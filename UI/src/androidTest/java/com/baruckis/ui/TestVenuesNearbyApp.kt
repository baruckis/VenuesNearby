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

package com.baruckis.ui

import androidx.test.core.app.ApplicationProvider
import com.baruckis.ui.di.DaggerTestAppComponent
import com.baruckis.ui.di.TestAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class TestVenuesNearbyApp : DaggerApplication() {

    private lateinit var testDaggerAppComponent: TestAppComponent

    companion object {
        fun testAppComponent(): TestAppComponent {
            return ApplicationProvider.getApplicationContext<TestVenuesNearbyApp>().testDaggerAppComponent
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {

        // Here we initialize Dagger. DaggerAppComponent is auto-generated from TestAppComponent.
        testDaggerAppComponent = DaggerTestAppComponent.builder().application(this).build()

        return testDaggerAppComponent
    }

}