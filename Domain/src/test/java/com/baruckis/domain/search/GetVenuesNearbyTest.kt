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

package com.baruckis.domain.search

import com.baruckis.domain.TestDataFactory
import com.baruckis.domain.executor.PostExecutionThread
import com.baruckis.domain.model.Venue
import com.baruckis.domain.repository.VenuesDomainRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetVenuesNearbyTest {

    private lateinit var getVenuesNearby: GetVenuesNearby

    @Mock
    lateinit var venuesDomainRepository: VenuesDomainRepository

    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getVenuesNearby = GetVenuesNearby(venuesDomainRepository, postExecutionThread)
    }

    @Test
    fun getVenuesCompletes() {
        stubGetVenuesNearby(Single.just(makeVenuesList()))
        val testObserver =
            getVenuesNearby.buildUseCaseObservable(GetVenuesNearby.Params.search(TestDataFactory.createVenueFirstPlaceName()))
                .test()
        testObserver.assertComplete()
    }

    @Test
    fun getVenuesReturnsData() {
        val venues = makeVenuesList()
        stubGetVenuesNearby(Single.just(venues))
        val testObserver =
            getVenuesNearby.buildUseCaseObservable(GetVenuesNearby.Params.search(TestDataFactory.createVenueSecondPlaceName()))
                .test()
        testObserver.assertValue(venues)
    }


    private fun stubGetVenuesNearby(single: Single<List<Venue>>) {
        whenever(venuesDomainRepository.getVenuesNearby(any()))
            .thenReturn(single)
    }

    private fun makeVenuesList(): List<Venue> {
        val venues = mutableListOf<Venue>()

        val firstVenue = TestDataFactory.createVenueFirst()
        val secondVenue = TestDataFactory.createVenueSecond()

        venues.add(firstVenue)
        venues.add(secondVenue)

        return venues
    }

}