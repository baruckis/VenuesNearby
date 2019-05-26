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

package com.baruckis.cache.mapper

import com.baruckis.cache.model.VenueCached
import com.baruckis.data.model.VenueEntity
import javax.inject.Inject

class VenueCachedMapper @Inject constructor() : CacheMapper<VenueCached, VenueEntity> {

    override fun mapFromCached(cached: VenueCached): VenueEntity {
        return VenueEntity(cached.id, cached.name, cached.lattitude, cached.longitude)
    }

    override fun mapToCached(entity: VenueEntity): VenueCached {
        return VenueCached(entity.id, entity.name, entity.lattitude, entity.longitude)
    }

}