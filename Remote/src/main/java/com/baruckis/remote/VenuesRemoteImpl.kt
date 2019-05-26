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
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class VenuesRemoteImpl @Inject constructor(
        private val apiService: FoursquareApiService,
        private val apiResponseModelMapper: VenueRecommendationsApiResponseModelMapper
) : VenuesRemote {

    override fun getVenuesNearby(placeName: String): Observable<List<VenueEntity>> {

        return apiService.getVenueRecommendations(
                placeName,
                API_FOURSQUARE_CLIENT_ID,
                API_FOURSQUARE_CLIENT_SECRET,
                getTodayDateFormatted(API_FOURSQUARE_DATE_FORMAT_PATTERN)
        )
                .map { remote ->
                    remote.response.groups.items.map { apiResponseModelMapper.mapFromApiResponseModel(it) }
                }
    }

    private fun getTodayDateFormatted(pattern: String): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        val date = Calendar.getInstance().time
        return formatter.format(date)
    }

}