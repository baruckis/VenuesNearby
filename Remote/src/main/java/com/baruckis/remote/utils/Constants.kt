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

package com.baruckis.remote.utils

const val API_FOURSQUARE_SERVICE_BASE_URL = "https://api.foursquare.com/v2/"
const val API_FOURSQUARE_CLIENT_ID = ""     // TODO: Use your Client ID provided by Foursquare Developers website.
const val API_FOURSQUARE_CLIENT_SECRET = "" // TODO: Use your Client Secret provided by Foursquare Developers website.
const val API_FOURSQUARE_DATE_FORMAT_PATTERN = "yyyyMMdd"

// Representing groups of recommendations. Each group contains a type such as “recommended” a human-readable
// (eventually localized) name such as “Recommended Places,” and an array items of recommendation objects.
const val API_FOURSQUARE_GROUP_TYPE = "recommended"