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

package com.baruckis.ui.mapper

import com.baruckis.presentation.model.VenuePresentation
import com.baruckis.ui.TestDataFactory
import com.baruckis.ui.model.VenueUi
import org.junit.Test
import kotlin.test.assertEquals

class VenueUiMapperTest {

    private val mapper = VenueUiMapper()

    @Test
    fun mapToUi() {

        val presentationModel = TestDataFactory.createVenuePresentation()
        val uiModel = mapper.mapToUi(presentationModel)

        assertMapsDataCorrectly(presentationModel, uiModel)
    }

    private fun assertMapsDataCorrectly(presentationModel: VenuePresentation, uiModel: VenueUi) {
        assertEquals(presentationModel.id, uiModel.id)
        assertEquals(presentationModel.name, uiModel.name)
        assertEquals(presentationModel.latitude, uiModel.latitude)
        assertEquals(presentationModel.longitude, uiModel.longitude)
    }

}