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

package com.baruckis.cache

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.baruckis.cache.db.AppDatabase
import com.baruckis.cache.mapper.VenueCachedMapper
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

// Robolectric setup
@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class VenuesCacheImplTest {

    @Rule
    @JvmField var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        AppDatabase::class.java)
        .allowMainThreadQueries()
        .build()

    private val mapper = VenueCachedMapper()
    private val cache = VenuesCacheImpl(database, mapper)

    @After
    fun closeDb() {
        database.close()
    }


    @Test
    fun clearVenuesNearby() {
        val testObserver = cache.clearVenuesNearby().test()
        testObserver.assertComplete()
    }

    @Test
    fun saveVenuesNearby() {
        val venueEntities = listOf(TestDataFactory.createVenueEntity())

        val testObserver = cache.saveVenuesNearby(venueEntities).test()
        testObserver.assertComplete()
    }

    @Test
    fun getVenuesNearby() {
        val venueEntities = listOf(TestDataFactory.createVenueEntity())
        cache.saveVenuesNearby(venueEntities).test()

        val testObserver = cache.getVenuesNearby(TestDataFactory.createPlaceName()).test()
        testObserver.assertValue(venueEntities)
    }

    @Test
    fun areVenuesNearbyCached() {
        val venueEntities = listOf(TestDataFactory.createVenueEntity())
        cache.saveVenuesNearby(venueEntities).test()

        val testObserver = cache.areVenuesNearbyCached().test()
        testObserver.assertValue(true)
    }

    @Test
    fun setLastCacheTime() {
        val testObserver = cache.setLastCacheTime(100).test()
        testObserver.assertComplete()
    }

    @Test
    fun isVenuesNearbyCacheExpired() {
        val testObserver = cache.isVenuesNearbyCacheExpired().test()
        testObserver.assertValue(true)
    }

    @Test
    fun isVenuesNearbyCacheNotExpired() {
        cache.setLastCacheTime(System.currentTimeMillis()).test()
        val testObserver = cache.isVenuesNearbyCacheExpired().test()
        testObserver.assertValue(false)
    }

}