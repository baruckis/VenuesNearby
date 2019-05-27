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
import android.content.Context
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
import com.baruckis.ui.utils.logConsoleVerbose
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_explore_venues.*
import javax.inject.Inject

class ExploreVenuesActivity : AppCompatActivity() {

    private lateinit var exploreVenuesViewModel: ExploreVenuesViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var exploreVenuesRecyclerViewAdapter: ExploreVenuesRecyclerViewAdapter

    @Inject
    lateinit var uiMapper: VenueUiMapper

    private var searchMenuItem: MenuItem? = null
    private var searchView: SearchView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        setContentView(R.layout.activity_explore_venues)

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = exploreVenuesRecyclerViewAdapter

        // Obtain ViewModel from ViewModelProviders, using this activity as LifecycleOwner.
        exploreVenuesViewModel = ViewModelProviders.of(this, viewModelFactory).get(ExploreVenuesViewModel::class.java)

        exploreVenuesViewModel.getVenuesNearbyLiveData().observe(this, Observer { resource ->
            handlePresentationResourceStatus(resource)
        })

        button.setOnClickListener {
            exploreVenuesViewModel.fetchVenuesNearby("Vilnius")
        }

    }

    private fun handlePresentationResourceStatus(dataResource: Resource<List<VenuePresentation>>) {

        logConsoleVerbose("handlePresentationResourceStatus " + dataResource.status)

        when (dataResource.status) {
            Status.LOADING -> {
                progress.visibility = View.VISIBLE
                recyclerview.visibility = View.GONE
            }
            Status.SUCCESS -> {
                progress.visibility = View.GONE
                recyclerview.visibility = View.VISIBLE

                val venuesUi = dataResource.data?.map { uiMapper.mapToUi(it) } ?: emptyList()

                exploreVenuesRecyclerViewAdapter.setData(venuesUi)

            }
            Status.ERROR -> {
                progress.visibility = View.GONE
                Toast.makeText(this, "Error: ${dataResource.message}", Toast.LENGTH_LONG).show()
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
        searchView?.setOnQueryTextListener(searchListener)

        return true
    }

    // This listener reacts to text submit inside search area. We expect to get new search results
    // each time when we submit a query for the place.
    private val searchListener = object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
                exploreVenuesViewModel.fetchVenuesNearby(it)
            }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }

    }

}