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
import com.baruckis.data.repository.VenuesRemote
import com.baruckis.remote.mapper.VenueRecommendationsApiResponseModelMapper
import com.baruckis.remote.service.FoursquareApiService
import com.baruckis.remote.utils.API_FOURSQUARE_CLIENT_ID
import com.baruckis.remote.utils.API_FOURSQUARE_CLIENT_SECRET
import com.baruckis.remote.utils.API_FOURSQUARE_DATE_FORMAT_PATTERN
import com.baruckis.remote.utils.API_FOURSQUARE_GROUP_TYPE
import io.reactivex.Single
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class VenuesRemoteImpl @Inject constructor(
        private val apiService: FoursquareApiService,
        private val apiResponseModelMapper: VenueRecommendationsApiResponseModelMapper
) : VenuesRemote {

    override fun getVenuesNearby(placeName: String): Single<List<VenueEntity>> {

        val response = apiService.getVenueRecommendations(
                placeName,
                API_FOURSQUARE_CLIENT_ID,
                API_FOURSQUARE_CLIENT_SECRET,
                getTodayDateFormatted(API_FOURSQUARE_DATE_FORMAT_PATTERN)
        )

        val recommendedGroup = response.map { remote ->
            remote.response.groups.first { group -> group.name == API_FOURSQUARE_GROUP_TYPE }
        }

        val venueEntities = recommendedGroup.map { group ->
            group.items.map { item -> apiResponseModelMapper.mapFromApiResponseModel(item) }
        }

        return venueEntities
    }

    private fun getTodayDateFormatted(pattern: String): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        val date = Calendar.getInstance().time
        return formatter.format(date)
    }

}