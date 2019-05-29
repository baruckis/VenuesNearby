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

import com.baruckis.data.TestDataFactory
import com.baruckis.data.model.VenueEntity
import com.baruckis.data.repository.VenuesCache
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

class VenuesCacheDataStoreTest {

    private val venuesCache = mock(VenuesCache::class.java)
    private val venuesCacheDataStore = VenuesCacheDataStore(venuesCache)

    private val venueEntity = TestDataFactory.createVenueEntity()

    @Test
    fun getVenuesNearbyCompletes() {
        stubGetVenuesNearby(Single.just(listOf(venueEntity)))
        val testObserver = venuesCacheDataStore.getVenuesNearby(TestDataFactory.createVenueEntityPlaceName()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getVenuesNearbyReturnsData() {
        val data = listOf(venueEntity)
        stubGetVenuesNearby(Single.just(data))
        val testObserver = venuesCacheDataStore.getVenuesNearby(TestDataFactory.createVenueEntityPlaceName()).test()
        testObserver.assertValue(data)
    }

    @Test
    fun saveVenuesNearbyCompletes() {
        stubSaveVenuesNearby(Completable.complete())
        val testObserver =
            venuesCacheDataStore.saveVenuesNearby(TestDataFactory.createVenueEntityPlaceName(), listOf(venueEntity))
                .test()
        testObserver.assertComplete()
    }

    @Test
    fun clearVenuesNearbyCompletes() {
        stubClearVenuesNearby(Completable.complete())
        val testObserver = venuesCacheDataStore.clearVenuesNearby().test()
        testObserver.assertComplete()
    }


    private fun stubGetVenuesNearby(single: Single<List<VenueEntity>>) {
        Mockito.`when`(venuesCache.getVenuesNearby(anyString()))
            .thenReturn(single)
    }

    private fun stubSaveVenuesNearby(completable: Completable) {
        Mockito.`when`(venuesCache.setLastCacheInfo(anyLong(), anyString()))
            .thenReturn(completable)

        Mockito.`when`(venuesCache.saveVenuesNearby(anyList()))
            .thenReturn(completable)
    }

    private fun stubClearVenuesNearby(completable: Completable) {
        Mockito.`when`(venuesCache.clearVenuesNearby())
            .thenReturn(completable)
    }

}