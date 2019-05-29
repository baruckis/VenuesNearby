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
import com.baruckis.data.repository.VenuesRemote
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock

class VenuesRemoteDataStoreTest {

    private val venuesRemote = mock(VenuesRemote::class.java)
    private val venuesRemoteDataStore = VenuesRemoteDataStore(venuesRemote)

    private val venueEntity = TestDataFactory.createVenueEntity()

    @Test
    fun getVenuesNearbyCompletes() {
        stubGetVenuesNearby(Single.just(listOf(venueEntity)))
        val testObserver = venuesRemoteDataStore.getVenuesNearby(TestDataFactory.createVenueEntityPlaceName()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getVenuesNearbyReturnsData() {
        val data = listOf(venueEntity)
        stubGetVenuesNearby(Single.just(data))
        val testObserver = venuesRemoteDataStore.getVenuesNearby(TestDataFactory.createVenueEntityPlaceName()).test()
        testObserver.assertValue(data)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveVenuesNearby() {
        venuesRemoteDataStore.saveVenuesNearby(TestDataFactory.createVenueEntityPlaceName(), emptyList()).test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun clearVenuesNearby() {
        venuesRemoteDataStore.clearVenuesNearby().test()
    }


    private fun stubGetVenuesNearby(single: Single<List<VenueEntity>>) {
        Mockito.`when`(venuesRemote.getVenuesNearby(anyString()))
            .thenReturn(single)
    }

}