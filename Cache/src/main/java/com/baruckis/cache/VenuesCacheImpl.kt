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

package com.baruckis.cache

import com.baruckis.cache.db.AppDatabase
import com.baruckis.cache.mapper.VenueCachedMapper
import com.baruckis.cache.model.CacheUpdateTime
import com.baruckis.data.model.VenueEntity
import com.baruckis.data.repository.VenuesCache
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class VenuesCacheImpl @Inject constructor(
        private val appDatabase: AppDatabase,
        private val mapper: VenueCachedMapper
) : VenuesCache {

    override fun clearVenuesNearby(): Completable {

        return Completable.defer {
            appDatabase.venueCachedDao().deleteVenueRecommendations()
            Completable.complete()
        }
    }

    override fun saveVenuesNearby(venues: List<VenueEntity>): Completable {

        return Completable.defer {
            appDatabase.venueCachedDao()
                    .replaceVenueRecommendations(venues.map { entity -> mapper.mapToCached(entity) })
            Completable.complete()
        }
    }

    override fun getVenuesNearby(placeName: String): Single<List<VenueEntity>> {

        return appDatabase.venueCachedDao().getVenueRecommendations().firstOrError()
                .map { it.map { cached -> mapper.mapFromCached(cached) } }
    }

    override fun areVenuesNearbyCached(): Single<Boolean> {

        return appDatabase.venueCachedDao().getVenueRecommendations().isEmpty.map { !it }
    }

    override fun setLastCacheTime(lastCache: Long): Completable {

        return Completable.defer {
            appDatabase.cacheUpdateTimeDao().replaceCacheUpdateTime(CacheUpdateTime(lastUpdateTime = lastCache))
            Completable.complete()
        }
    }

    override fun isVenuesNearbyCacheExpired(): Single<Boolean> {
        val currentTime = System.currentTimeMillis()
        val expirationTime = (86400000).toLong()
        return appDatabase.cacheUpdateTimeDao().getCacheUpdateTime().toSingle(CacheUpdateTime(lastUpdateTime = 0))
                .map {
                    currentTime - it.lastUpdateTime > expirationTime
                }
    }

}