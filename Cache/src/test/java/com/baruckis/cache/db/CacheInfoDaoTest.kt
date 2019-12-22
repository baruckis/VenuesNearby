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

package com.baruckis.cache.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.baruckis.cache.TestDataFactory
import com.baruckis.cache.model.CacheInfo
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

// Robolectric setup
@RunWith(RobolectricTestRunner::class)
@Config(
    manifest = Config.NONE,
    // Robolectric is telling me that Java 9 is required.
    sdk = [android.os.Build.VERSION_CODES.O_MR1]
)
class CacheInfoDaoTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        AppDatabase::class.java
    )
        .allowMainThreadQueries()
        .build()

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun replaceAndGetCacheInfo() {

        val cacheUpdateTime =
            CacheInfo(-1, System.currentTimeMillis(), TestDataFactory.createPlaceName())

        database.cacheUpdateTimeDao().replaceCacheInfo(cacheUpdateTime)

        val testObserver = database.cacheUpdateTimeDao().getCacheInfo().test()
        testObserver.assertValue(cacheUpdateTime)
    }

}