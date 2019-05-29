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

import android.app.Activity
import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.baruckis.ui.di.DaggerTestAppComponent
import com.baruckis.ui.di.TestAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class TestVenuesNearbyApp : Application(), HasActivityInjector {

    @Inject // It implements Dagger machinery of finding appropriate injector factory for a type.
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    private lateinit var testDaggerAppComponent: TestAppComponent

    companion object {
        fun testAppComponent(): TestAppComponent {
            return ApplicationProvider.getApplicationContext<TestVenuesNearbyApp>().testDaggerAppComponent
        }
    }


    override fun onCreate() {
        super.onCreate()

        // Here we initialize Dagger. DaggerAppComponent is auto-generated from TestAppComponent.
        testDaggerAppComponent = DaggerTestAppComponent.builder().application(this).build()
        testDaggerAppComponent.inject(this)
    }

    // This is required by HasActivityInjector interface to setup Dagger for Activity.
    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

}