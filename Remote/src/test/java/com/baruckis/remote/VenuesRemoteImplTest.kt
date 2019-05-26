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

package com.baruckis.remote

import com.baruckis.data.model.VenueEntity
import com.baruckis.remote.mapper.VenueRecommendationsApiResponseModelMapper
import com.baruckis.remote.model.Group
import com.baruckis.remote.model.Item
import com.baruckis.remote.model.Response
import com.baruckis.remote.model.VenueRecommendationsApiResponseModel
import com.baruckis.remote.service.FoursquareApiService
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test

class VenuesRemoteImplTest {

    private val mapper = mock<VenueRecommendationsApiResponseModelMapper>()
    private val service = mock<FoursquareApiService>()
    private val remote = VenuesRemoteImpl(service, mapper)

    private val item = Item(
            Item.Venue("4d1a11a6cc216ea884ff81d3", "Vingio parkas", Item.Venue.Location(54.68293703261666, 25.237655639648438))
    )
    private val model = VenueRecommendationsApiResponseModel(Response(listOf(Group("recommended", listOf(item)))))
    private val entity = VenueEntity("4d1a11a6cc216ea884ff81d3", "Vingio parkas", 54.68293703261666, 25.237655639648438)

    @Test
    fun getVenuesNearbyCompletes() {
        stubGetVenuesNearby(Single.just(model))

        val testObserver = remote.getVenuesNearby("Vilnius").test()
        testObserver.assertComplete()
    }

    @Test
    fun getVenuesNearbyReturnsData() {
        stubGetVenuesNearby(Single.just(model))
        val entities = mutableListOf<VenueEntity>()

        model.response.groups.first().items.forEach { item ->
            entities.add(entity)
            whenever(mapper.mapFromApiResponseModel(item)).thenReturn(entity)
        }
        val testObserver = remote.getVenuesNearby("Vilnius").test()
        testObserver.assertValue(entities)
    }

    private fun stubGetVenuesNearby(single: Single<VenueRecommendationsApiResponseModel>) {
        whenever(service.getVenueRecommendations(any(), any(), any(), any()))
                .thenReturn(single)
    }

}

