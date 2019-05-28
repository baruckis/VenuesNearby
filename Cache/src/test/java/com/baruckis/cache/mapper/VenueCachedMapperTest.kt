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

import com.baruckis.cache.TestDataFactory
import com.baruckis.cache.model.VenueCached
import com.baruckis.data.model.VenueEntity
import org.junit.Test
import kotlin.test.assertEquals

class VenueCachedMapperTest {

    private val mapper = VenueCachedMapper()

    @Test
    fun mapFromCachedModel() {

        val cachedModel = TestDataFactory.createVenueCached()
        val dataModel = mapper.mapFromCached(cachedModel)

        assertMapsDataCorrectly(cachedModel, dataModel)
    }

    private fun assertMapsDataCorrectly(cachedModel: VenueCached, dataModel: VenueEntity) {
        assertEquals(cachedModel.id, dataModel.id)
        assertEquals(cachedModel.name, dataModel.name)
        assertEquals(cachedModel.latitude, dataModel.latitude)
        assertEquals(cachedModel.longitude, dataModel.longitude)
    }

}