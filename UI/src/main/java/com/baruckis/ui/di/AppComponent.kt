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

package com.baruckis.ui.di

import android.app.Application
import com.baruckis.ui.VenuesNearbyApp
import com.baruckis.ui.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Singleton component interface for the app. It ties all the modules together.
 * The component is used to connect objects to their dependencies.
 * Dagger will auto-generate DaggerAppComponent which is used for initialization at Application.
 */
@Singleton
@Component(
        modules = [
            // AndroidInjectionModule is a class of Dagger and we don't need to create it.
            // If you want to use injection in fragment then you should use AndroidSupportInjectionModule.class else
            // use AndroidInjectionModule.
            AndroidInjectionModule::class,
            AppModule::class,
            DataModule::class,
            CacheModule::class,
            RemoteModule::class,
            PresentationModule::class,
            UiModule::class
        ]
)
interface AppComponent : AndroidInjector<VenuesNearbyApp> {

    @Component.Builder // Used for instantiation of a component.
    interface Builder {

        @BindsInstance // Bind our application instance to our Dagger graph.
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    // The application which is allowed to request the dependencies declared by the modules.
    override fun inject(app: VenuesNearbyApp)

}