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

package com.baruckis.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baruckis.domain.model.Venue
import com.baruckis.domain.search.GetVenuesNearby
import com.baruckis.presentation.mapper.VenuePresentationMapper
import com.baruckis.presentation.model.VenuePresentation
import com.baruckis.presentation.state.Resource
import com.baruckis.presentation.state.Status
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class ExploreVenuesViewModel @Inject constructor(
    private val getVenuesNearby: GetVenuesNearby,
    private val mapper: VenuePresentationMapper
) : ViewModel() {

    private val venuesNearbyMutableLiveData: MutableLiveData<Resource<List<VenuePresentation>>> = MutableLiveData()


    fun getVenuesNearbyLiveData(): LiveData<Resource<List<VenuePresentation>>> {
        return venuesNearbyMutableLiveData
    }

    fun fetchVenuesNearby() {
        venuesNearbyMutableLiveData.postValue(Resource(Status.LOADING, null, null))
        getVenuesNearby.execute(VenuesSubscriber())
    }

    override fun onCleared() {
        getVenuesNearby.dispose()
        super.onCleared()
    }


    inner class VenuesSubscriber : DisposableObserver<List<Venue>>() {

        override fun onNext(item: List<Venue>) {
            venuesNearbyMutableLiveData.postValue(
                Resource(
                    Status.SUCCESS,
                    item.map { domain -> mapper.mapToPresentation(domain) }, null
                )
            )
        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {
            venuesNearbyMutableLiveData.postValue(Resource(Status.ERROR, null, e.localizedMessage))
        }
    }

}