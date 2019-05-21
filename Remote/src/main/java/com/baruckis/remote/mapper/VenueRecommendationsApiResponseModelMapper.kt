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

package com.baruckis.remote.mapper

import com.baruckis.data.model.VenueEntity
import com.baruckis.remote.model.Item
import com.baruckis.remote.model.VenueRecommendationsApiResponseModel

class VenueRecommendationsApiResponseModelMapper(): ApiResponseModelMapper<Item, VenueEntity> {

    override fun mapFromApiResponseModel(remote: Item): VenueEntity {
        return VenueEntity(remote.venue.name, remote.venue.location.lat, remote.venue.location.lng)
    }

}