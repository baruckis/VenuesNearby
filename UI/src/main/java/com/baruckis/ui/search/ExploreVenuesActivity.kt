/*
 * Copyright 2019 Andrius Baruckis www.baruckis.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.baruckis.ui.search

import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.baruckis.presentation.ExploreVenuesViewModel
import com.baruckis.presentation.model.VenuePresentation
import com.baruckis.presentation.state.Resource
import com.baruckis.presentation.state.Status
import com.baruckis.ui.R
import com.baruckis.ui.mapper.VenueUiMapper
import com.baruckis.ui.model.VenueUi
import com.baruckis.ui.utils.logConsoleVerbose
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_explore_venues.*
import javax.inject.Inject

class ExploreVenuesActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var exploreVenuesViewModel: ExploreVenuesViewModel

    @Inject // It implements Dagger machinery of finding appropriate injector factory for a type.
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var exploreVenuesRecyclerViewAdapter: ExploreVenuesRecyclerViewAdapter

    @Inject
    lateinit var uiMapper: VenueUiMapper

    private var searchMenuItem: MenuItem? = null
    private var searchView: SearchView? = null

    private var queryPlaceName: String? = null


    companion object {
        const val QUERY_PLACE_NAME = "query_place_name"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        setContentView(R.layout.activity_explore_venues)

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = exploreVenuesRecyclerViewAdapter
        exploreVenuesRecyclerViewAdapter.setItemClickListener(venueClickListener)

        // Obtain ViewModel from ViewModelProviders, using this activity as LifecycleOwner.
        exploreVenuesViewModel = ViewModelProviders.of(this, viewModelFactory).get(ExploreVenuesViewModel::class.java)

        queryPlaceName = savedInstanceState?.getString(QUERY_PLACE_NAME)
        queryPlaceName?.let {
            exploreVenuesViewModel.fetchVenuesNearby(it)
            supportActionBar?.subtitle = StringBuilder(getString(R.string.app_subtitle, it))
        } ?: run {
            handlePresentationResourceStatus(Resource(Status.INIT, null, null))
        }

        exploreVenuesViewModel.getVenuesNearbyLiveData().observe(this, Observer { resource ->
            handlePresentationResourceStatus(resource)
        })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(QUERY_PLACE_NAME, queryPlaceName)
        super.onSaveInstanceState(outState)
    }


    private fun handlePresentationResourceStatus(dataResource: Resource<List<VenuePresentation>>) {

        logConsoleVerbose("handlePresentationResourceStatus " + dataResource.status)

        when (dataResource.status) {
            Status.LOADING -> {
                progress.visibility = View.VISIBLE
                recyclerview.visibility = View.GONE
                message.visibility = View.GONE

                supportActionBar?.subtitle = ""
            }
            Status.SUCCESS -> {
                progress.visibility = View.GONE
                recyclerview.visibility = View.VISIBLE
                message.visibility = View.GONE

                if (!queryPlaceName.isNullOrBlank()) {
                    supportActionBar?.subtitle = StringBuilder(getString(R.string.app_subtitle, queryPlaceName))
                }

                val venuesUi = dataResource.data?.map { uiMapper.mapToUi(it) } ?: emptyList()

                exploreVenuesRecyclerViewAdapter.setData(venuesUi)

                if (venuesUi.isEmpty()) {
                    message.visibility = View.VISIBLE
                    message.text = getString(R.string.empty_message, queryPlaceName)
                }
            }
            Status.ERROR -> {
                progress.visibility = View.GONE
                recyclerview.visibility = View.GONE
                message.visibility = View.VISIBLE

                message.text = getString(R.string.error_message, dataResource.message)
            }
            Status.INIT -> {
                progress.visibility = View.GONE
                recyclerview.visibility = View.GONE
                message.visibility = View.VISIBLE

                message.text = getString(R.string.init_message)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_search, menu)

        // Associate searchable configuration with the SearchView.
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchMenuItem = menu?.findItem(R.id.search)

        searchView = searchMenuItem?.actionView as SearchView
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView?.maxWidth = Integer.MAX_VALUE // Expand to full width, to have close button set to the right side.
        searchView?.setOnQueryTextListener(this)

        return true
    }


    // This listener reacts to text submit inside search area. We expect to get new search results
    // each time when we submit a query for the place.
    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            exploreVenuesViewModel.fetchVenuesNearby(it)
            queryPlaceName = it
            supportActionBar?.subtitle = StringBuilder(getString(R.string.app_subtitle, queryPlaceName))

            searchMenuItem?.collapseActionView()
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }


    private val venueClickListener = object : VenueClickListener {

        override fun onVenueClicked(venueUi: VenueUi) {
            try {
                val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.uri_maps_app, venueUi.latitude, venueUi.longitude, venueUi.name))
                )
                this@ExploreVenuesActivity.startActivity(intent)
            } catch (exc: ActivityNotFoundException) {
                logConsoleVerbose("onVenueClicked " + exc.localizedMessage)
                Toast.makeText(this@ExploreVenuesActivity, getString(R.string.install_maps_app), Toast.LENGTH_LONG)
                        .show()
            } catch (exc: Exception) {
                logConsoleVerbose("onVenueClicked " + exc.localizedMessage)
                Toast.makeText(this@ExploreVenuesActivity, exc.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }

    }

}