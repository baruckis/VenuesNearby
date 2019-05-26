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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baruckis.presentation.ExploreVenuesViewModel
import com.baruckis.ui.di.ViewModelFactory
import com.baruckis.ui.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class PresentationModule {

    // We'd like to take this implementation of the ViewModel class and make it available in an injectable map with
    // ExploreVenuesViewModel::class as a key to that map.
    @Binds
    @IntoMap
    // We use a restriction on multibound map defined with @ViewModelKey annotation, and if don't need any,
    // we should use @ClassKey annotation provided by Dagger.
    @ViewModelKey(ExploreVenuesViewModel::class)
    abstract fun bindExploreVenuesViewModel(viewModel: ExploreVenuesViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}