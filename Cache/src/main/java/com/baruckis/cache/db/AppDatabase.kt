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

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.baruckis.cache.model.CacheInfo
import com.baruckis.cache.model.VenueCached
import com.baruckis.cache.utils.APP_DATABASE

@Database(entities = [VenueCached::class, CacheInfo::class], version = 1)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun venueCachedDao(): VenueCachedDao

    abstract fun cacheUpdateTimeDao(): CacheInfoDao


    // The AppDatabase a singleton to prevent having multiple instances of the database opened at the same time.
    companion object {

        // Marks the JVM backing field of the annotated property as volatile, meaning that writes to this field
        // are immediately made visible to other threads.
        @Volatile
        private var instance: AppDatabase? = null

        // For Singleton instantiation.
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Creates the database.
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, APP_DATABASE).build()
        }

    }

}