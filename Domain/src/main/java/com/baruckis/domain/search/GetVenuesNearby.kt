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

package com.baruckis.domain.search

import com.baruckis.domain.executor.PostExecutionThread
import com.baruckis.domain.interactor.ObservableUseCase
import com.baruckis.domain.model.Venue
import com.baruckis.domain.repository.VenuesRepository
import io.reactivex.Observable
import javax.inject.Inject

open class GetVenuesNearby @Inject constructor(
    private val venuesRepository: VenuesRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Venue>, Nothing?>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Nothing?): Observable<List<Venue>> {
        return venuesRepository.getVenuesNearby()
    }

}