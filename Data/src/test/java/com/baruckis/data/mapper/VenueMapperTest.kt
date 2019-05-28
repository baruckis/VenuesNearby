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

package com.baruckis.data.mapper

import com.baruckis.data.TestDataFactory
import com.baruckis.data.model.VenueEntity
import com.baruckis.domain.model.Venue
import org.junit.Test
import kotlin.test.assertEquals

class VenueMapperTest {

    private val mapper = VenueMapper()

    @Test
    fun mapFromEntity() {

        val dataModel = TestDataFactory.createVenueEntity()
        val domainModel = mapper.mapFromEntity(dataModel)

        assertMapsDataCorrectly(dataModel, domainModel)
    }

    @Test
    fun mapToEntity() {

        val domainModel = TestDataFactory.createVenue()
        val dataModel = mapper.mapToEntity(domainModel)

        assertMapsDataCorrectly(dataModel, domainModel)
    }

    private fun assertMapsDataCorrectly(dataModel: VenueEntity, domainModel: Venue) {
        assertEquals(dataModel.name, domainModel.name)
        assertEquals(dataModel.latitude, domainModel.latitude)
        assertEquals(dataModel.longitude, domainModel.longitude)
    }

}