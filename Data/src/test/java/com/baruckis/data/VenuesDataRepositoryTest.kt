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

package com.baruckis.data

import com.baruckis.data.mapper.VenueMapper
import com.baruckis.data.model.VenueEntity
import com.baruckis.data.repository.VenuesCache
import com.baruckis.data.repository.VenuesDataStore
import com.baruckis.data.store.VenuesDataStoreFactory
import com.baruckis.domain.model.Venue
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class VenuesDataRepositoryTest {

    private val venueMapper = mock<VenueMapper>()

    private val venuesCache = mock<VenuesCache>()

    private val venuesDataStoreFactory = mock<VenuesDataStoreFactory>()

    private val venuesDataStore = mock<VenuesDataStore>()

    private val venuesDataRepository = VenuesDataRepository(venueMapper, venuesCache, venuesDataStoreFactory)

    private val venueModel = Venue("4d1a11a6cc216ea884ff81d3", "Gedimino pr.", 54.68736449150992, 25.279981398558263)
    private val venueEntity = VenueEntity("4d1a11a6cc216ea884ff81d3", "Trafalgar Sq", 51.50812811764834, -0.12808620929718018)


    @Before
    fun setup() {

        stubAreVenuesNearbyCached(Single.just(false))

        stubIsCacheExpired(Single.just(false))

        stubGetDataStore()

        stubGetVenuesNearby(any(), Single.just(listOf(venueEntity)))

        stubGetCacheDataStore()

        stubSaveVenuesNearby(Completable.complete())

        stubMapFromEntity(venueModel, venueEntity)
    }


    @Test
    fun getVenuesNearbyCompletes() {

        val testObserver = venuesDataRepository.getVenuesNearby("Gedimino pr.").test()
        testObserver.assertComplete()
    }


    @Test
    fun getVenuesNearbyReturnsData() {

        val testObserver = venuesDataRepository.getVenuesNearby("Trafalgar Sq").test()
        testObserver.assertValue(listOf(venueModel))
    }


    private fun stubAreVenuesNearbyCached(single: Single<Boolean>) {
        whenever(venuesCache.areVenuesNearbyCached())
                .thenReturn(single)
    }

    private fun stubIsCacheExpired(single: Single<Boolean>) {
        whenever(venuesCache.isVenuesNearbyCacheExpired())
                .thenReturn(single)
    }

    private fun stubGetDataStore() {
        whenever(venuesDataStoreFactory.getDataStore(any(), any()))
                .thenReturn(venuesDataStore)
    }

    private fun stubGetVenuesNearby(placeName: String, single: Single<List<VenueEntity>>) {
        whenever(venuesDataStore.getVenuesNearby(placeName))
                .thenReturn(single)
    }

    private fun stubGetCacheDataStore() {
        whenever(venuesDataStoreFactory.getCacheDataStore())
                .thenReturn(venuesDataStore)
    }

    private fun stubSaveVenuesNearby(completable: Completable) {
        whenever(venuesDataStore.saveVenuesNearby(any()))
                .thenReturn(completable)
    }

    private fun stubMapFromEntity(model: Venue, entity: VenueEntity) {
        whenever(venueMapper.mapFromEntity(entity))
                .thenReturn(model)
    }

}