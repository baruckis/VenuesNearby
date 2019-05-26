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

package com.baruckis.ui.di.module

import android.app.Application
import com.baruckis.cache.VenuesCacheImpl
import com.baruckis.cache.db.AppDatabase
import com.baruckis.data.repository.VenuesCache
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
abstract class CacheModule {

    @Module
    companion object {
        @Provides // Annotation informs Dagger compiler that this method is the constructor for the Context return type.
        @JvmStatic
        fun provideDatabase(application: Application): AppDatabase {
            return AppDatabase.getInstance(application)
        }
    }


    @Binds
    abstract fun bindVenuesCache(venuesCache: VenuesCacheImpl): VenuesCache

}