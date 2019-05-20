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
import com.baruckis.data.repository.VenuesCache
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Test

class VenuesCacheDataStoreTest {

    private val venuesCache = mock<VenuesCache>()
    private val venuesCacheDataStore = VenuesCacheDataStore(venuesCache)

    private val venueEntity = VenueEntity("Trafalgar Sq", 51.50812811764834, -0.12808620929718018)

    @Test
    fun getVenuesNearbyCompletes() {
        stubGetVenuesNearby(Observable.just(listOf(venueEntity)))
        val testObserver = venuesCacheDataStore.getVenuesNearby("Trafalgar Sq").test()
        testObserver.assertComplete()
    }

    @Test
    fun getVenuesNearbyReturnsData() {
        val data = listOf(venueEntity)
        stubGetVenuesNearby(Observable.just(data))
        val testObserver = venuesCacheDataStore.getVenuesNearby("Trafalgar Sq").test()
        testObserver.assertValue(data)
    }

    @Test
    fun saveVenuesNearbyCompletes() {
        stubSaveVenuesNearby(Completable.complete())
        val testObserver = venuesCacheDataStore.saveVenuesNearby(listOf(venueEntity)).test()
        testObserver.assertComplete()
    }

    @Test
    fun clearVenuesNearbyCompletes() {
        stubClearVenuesNearby(Completable.complete())
        val testObserver = venuesCacheDataStore.clearVenuesNearby().test()
        testObserver.assertComplete()
    }


    private fun stubGetVenuesNearby(observable: Observable<List<VenueEntity>>) {
        whenever(venuesCache.getVenuesNearby(any()))
            .thenReturn(observable)
    }

    private fun stubSaveVenuesNearby(completable: Completable) {
        whenever(venuesCache.setLastCacheTime(any()))
            .thenReturn(completable)

        whenever(venuesCache.saveVenuesNearby(any()))
            .thenReturn(completable)
    }

    private fun stubClearVenuesNearby(completable: Completable) {
        whenever(venuesCache.clearVenuesNearby())
            .thenReturn(completable)
    }

}