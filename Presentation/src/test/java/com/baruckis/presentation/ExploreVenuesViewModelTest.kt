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

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.baruckis.domain.model.Venue
import com.baruckis.domain.search.GetVenuesNearby
import com.baruckis.presentation.mapper.VenuePresentationMapper
import com.nhaarman.mockitokotlin2.*
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Rule
import org.junit.Test
import org.mockito.Captor
import kotlin.test.assertEquals

class ExploreVenuesViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    var getVenuesNearby = mock<GetVenuesNearby>()
    var mapper = mock<VenuePresentationMapper>()

    var viewModel = ExploreVenuesViewModel(getVenuesNearby, mapper)

    @Captor
    val captor = argumentCaptor<DisposableSingleObserver<List<Venue>>>()


    @Test
    fun fetchVenuesNearbyExecute() {

        viewModel.fetchVenuesNearby("Vilnius")

        verify(getVenuesNearby, times(1)).execute(any(), eq(GetVenuesNearby.Params.search("Vilnius")))
    }

    @Test
    fun fetchVenuesReturnsData() {

        val venues = TestDataFactory.createVenueList()
        val venuePresentations = TestDataFactory.createVenuePresentationList()

        whenever(mapper.mapToPresentation(venues[0])).thenReturn(venuePresentations[0])

        viewModel.fetchVenuesNearby("Vilnius")

        verify(getVenuesNearby).execute(captor.capture(), eq(GetVenuesNearby.Params.search("Vilnius")))
        captor.firstValue.onSuccess(venues)

        assertEquals(venuePresentations, viewModel.getVenuesNearbyLiveData().value?.data)
    }

    @Test
    fun fetchVenuesReturnsErrorMessage() {

        val errorMsg = TestDataFactory.createErrorMessage()

        viewModel.fetchVenuesNearby("Vilnius")

        verify(getVenuesNearby).execute(captor.capture(), eq(GetVenuesNearby.Params.search("Vilnius")))
        captor.firstValue.onError(RuntimeException(errorMsg))

        assertEquals(errorMsg, viewModel.getVenuesNearbyLiveData().value?.message)
    }

}