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

import com.baruckis.data.repository.VenuesDataStore
import javax.inject.Inject

open class VenuesDataStoreFactory @Inject constructor(
        private val venuesCacheDataStore: VenuesCacheDataStore,
        private val venuesRemoteDataStore: VenuesRemoteDataStore
) {

    open fun getDataStore(venuesCached: Boolean, cacheExpired: Boolean): VenuesDataStore {
        return if (venuesCached && !cacheExpired) {
            venuesCacheDataStore
        } else {
            venuesRemoteDataStore
        }
    }

    open fun getCacheDataStore(): VenuesDataStore {
        return venuesCacheDataStore
    }

}