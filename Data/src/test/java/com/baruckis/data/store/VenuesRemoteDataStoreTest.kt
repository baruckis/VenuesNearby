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

package com.baruckis.data.store

import com.baruckis.data.model.VenueEntity
import com.baruckis.data.repository.VenuesRemote
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Test

class VenuesRemoteDataStoreTest {

    private val venuesRemote = mock<VenuesRemote>()
    private val venuesRemoteDataStore = VenuesRemoteDataStore(venuesRemote)

    private val venueEntity = VenueEntity("Gedimino pr.", 54.68736449150992, 25.279981398558263)

    @Test
    fun getVenuesNearbyCompletes() {
        stubGetVenuesNearby(Observable.just(listOf(venueEntity)))
        val testObserver = venuesRemoteDataStore.getVenuesNearby("Trafalgar Sq").test()
        testObserver.assertComplete()
    }

    @Test
    fun getVenuesNearbyReturnsData() {
        val data = listOf(venueEntity)
        stubGetVenuesNearby(Observable.just(data))
        val testObserver = venuesRemoteDataStore.getVenuesNearby("Gedimino pr.").test()
        testObserver.assertValue(data)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveVenuesNearby() {
        venuesRemoteDataStore.saveVenuesNearby(emptyList()).test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun clearVenuesNearby() {
        venuesRemoteDataStore.clearVenuesNearby().test()
    }


    private fun stubGetVenuesNearby(observable: Observable<List<VenueEntity>>) {
        whenever(venuesRemote.getVenuesNearby(any()))
            .thenReturn(observable)
    }

}