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

package com.baruckis.remote

import com.baruckis.data.model.VenueEntity
import com.baruckis.remote.model.Item

object TestDataFactory {

    fun createVenueEntity(): VenueEntity {
        return VenueEntity("4d1a11a6cc216ea884ff81d3", "Trafalgar Sq", 51.50812811764834, -0.12808620929718018)
    }

    fun createItemVenue(): Item.Venue {
        return Item.Venue(
            "4d1a11a6cc216ea884ff8666",
            "Vingio parkas",
            Item.Venue.Location(54.68293703261666, 25.237655639648438)
        )
    }
}