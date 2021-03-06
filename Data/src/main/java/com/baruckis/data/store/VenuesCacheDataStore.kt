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

import com.baruckis.data.model.VenueEntity
import com.baruckis.data.repository.VenuesCache
import com.baruckis.data.repository.VenuesDataStore
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

open class VenuesCacheDataStore @Inject constructor(
        private val venuesCache: VenuesCache
) : VenuesDataStore {

    override fun getVenuesNearby(placeName: String): Single<List<VenueEntity>> {
        return venuesCache.getVenuesNearby(placeName)
    }

    override fun saveVenuesNearby(placeName: String, venues: List<VenueEntity>): Completable {
        return venuesCache.saveVenuesNearby(venues).andThen(venuesCache.setLastCacheInfo(System.currentTimeMillis(), placeName))
    }

    override fun clearVenuesNearby(): Completable {
        return venuesCache.clearVenuesNearby()
    }

}