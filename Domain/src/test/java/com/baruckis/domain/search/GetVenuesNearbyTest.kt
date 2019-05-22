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

import com.baruckis.domain.executor.PostExecutionThread
import com.baruckis.domain.model.Venue
import com.baruckis.domain.repository.VenuesDomainRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
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
        stubGetVenuesNearby(Observable.just(makeVenuesList()))
        val testObserver =
            getVenuesNearby.buildUseCaseObservable(GetVenuesNearby.Params.forVenue("London")).test()
        testObserver.assertComplete()
    }

    @Test
    fun getVenuesReturnsData() {
        val venues = makeVenuesList()
        stubGetVenuesNearby(Observable.just(venues))
        val testObserver =
            getVenuesNearby.buildUseCaseObservable(GetVenuesNearby.Params.forVenue("Vilnius")).test()
        testObserver.assertValue(venues)
    }


    private fun stubGetVenuesNearby(observable: Observable<List<Venue>>) {
        whenever(venuesDomainRepository.getVenuesNearby(any()))
            .thenReturn(observable)
    }

    private fun makeVenuesList(): List<Venue> {
        val venues = mutableListOf<Venue>()

        val firstVenue = Venue("4d1a11a6cc216ea884ff81d3","Trafalgar Sq", 51.50812811764834, -0.12808620929718018)
        val secondVenue = Venue("4d1a11a6cc216ea884ff81d3","Gedimino pr.", 54.68736449150992, 25.279981398558263)

        venues.add(firstVenue)
        venues.add(secondVenue)

        return venues
    }

}