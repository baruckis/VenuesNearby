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

package com.baruckis.data.repository

import com.baruckis.data.model.VenueEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface VenuesCashe {

    fun clearVenuesNearby(): Completable
    fun saveVenuesNearby(venues: List<VenueEntity>): Completable
    fun getVenuesNearby(placeName: String): Observable<List<VenueEntity>>
    fun areProjectsCached(): Single<Boolean>
    fun setLastCacheTime(lastCache: Long): Completable
    fun isVenuesNearbyCacheExpired(): Single<Boolean>

}