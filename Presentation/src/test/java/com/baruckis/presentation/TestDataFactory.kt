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

package com.baruckis.presentation

import com.baruckis.domain.model.Venue
import com.baruckis.presentation.model.VenuePresentation

object TestDataFactory {

    fun createVenue(): Venue {
        return Venue("4d1a11a6cc216ea884ff81d3", "Trafalgar Sq", 51.50812811764834, -0.12808620929718018)
    }

    fun createVenuePresentation(): VenuePresentation {
        return VenuePresentation("4d1a11a6cc216ea884ff81d3", "Trafalgar Sq", 51.50812811764834, -0.12808620929718018)
    }

    fun createVenueList(): List<Venue> {
        val list = listOf<Venue>(createVenue())
        return list
    }

    fun createVenuePresentationList(): List<VenuePresentation> {
        val list = listOf<VenuePresentation>(createVenuePresentation())
        return list
    }

    fun createErrorMessage(): String {
        return "Custom error message!"
    }

}