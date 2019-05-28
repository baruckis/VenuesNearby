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

import com.baruckis.data.model.VenueEntity
import com.baruckis.domain.model.Venue
import org.junit.Test
import kotlin.test.assertEquals

class VenueMapperTest {

    private val mapper = VenueMapper()

    @Test
    fun mapFromEntity() {

        val entity = VenueEntity("4d1a11a6cc216ea884ff81d3", "Trafalgar Sq", 51.50812811764834, -0.12808620929718018)
        val model = mapper.mapFromEntity(entity)

        assertMapsDataCorrectly(entity, model)
    }

    @Test
    fun mapToEntity() {

        val model = Venue("4d1a11a6cc216ea884ff81d3", "Gedimino pr.", 54.68736449150992, 25.279981398558263)
        val entity = mapper.mapToEntity(model)

        assertMapsDataCorrectly(entity, model)
    }

    private fun assertMapsDataCorrectly(entity: VenueEntity, model: Venue) {
        assertEquals(entity.name, model.name)
        assertEquals(entity.latitude, model.latitude)
        assertEquals(entity.longitude, model.longitude)
    }

}