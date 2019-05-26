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

package com.baruckis.data

import com.baruckis.data.mapper.VenueMapper
import com.baruckis.data.repository.VenuesCache
import com.baruckis.data.store.VenuesDataStoreFactory
import com.baruckis.domain.model.Venue
import com.baruckis.domain.repository.VenuesDomainRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class VenuesDataRepository @Inject constructor(
        private val mapper: VenueMapper,
        private val cache: VenuesCache,
        private val storeFactory: VenuesDataStoreFactory
) : VenuesDomainRepository {

    override fun getVenuesNearby(placeName: String): Single<List<Venue>> {

        return Single.zip(
                cache.areVenuesNearbyCached(),
                cache.isVenuesNearbyCacheExpired(),
                BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>> { areCached, isExpired ->
                    Pair(areCached, isExpired)
                }
        ).flatMap {
            storeFactory.getDataStore(it.first, it.second).getVenuesNearby(placeName)
        }
                .flatMap { venueEntities ->
                    storeFactory.getCacheDataStore()
                            .saveVenuesNearby(venueEntities)
                            .andThen(Single.just(venueEntities))
                }
                .map { venueEntity ->
                    venueEntity.map {
                        mapper.mapFromEntity(it)
                    }
                }
    }

}