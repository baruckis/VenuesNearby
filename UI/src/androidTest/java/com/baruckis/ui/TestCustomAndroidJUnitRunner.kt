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

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.test.runner.AndroidJUnitRunner
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers

class TestCustomAndroidJUnitRunner : AndroidJUnitRunner() {

    override fun onCreate(arguments: Bundle?) {
        super.onCreate(arguments)
        // Whenever anything is subscribed to, the result is returned immediately.
        RxJavaPlugins.setInitIoSchedulerHandler { Schedulers.trampoline() }
    }

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, TestVenuesNearbyApp::class.java.name, context)
    }

}