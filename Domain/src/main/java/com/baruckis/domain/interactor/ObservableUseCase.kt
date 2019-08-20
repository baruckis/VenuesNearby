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

package com.baruckis.domain.interactor

import com.baruckis.domain.executor.PostExecutionThread
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

abstract class ObservableUseCase<T, in Params> constructor(private val postExecutionThread: PostExecutionThread) {

    private val disposables = CompositeDisposable()

    abstract fun buildUseCaseObservable(params: Params? = null): Single<T> // The work you need to do.

    open fun execute(observer: DisposableSingleObserver<T>, params: Params? = null) {
        val observable = this.buildUseCaseObservable(params)
            /* Schedulers are responsible for performing operations of Observable on different threads.
               IO generally used for stuff such as network requests, file system operations.
               IO scheduler is backed by thread-pool. */
                .subscribeOn(Schedulers.io()) // Thread you need the work to perform on.
                .observeOn(postExecutionThread.scheduler) // Thread you need to handle the result on.
        addDisposable(observable.subscribeWith(observer)) // Handle the result here.
    }

    fun dispose() {
        disposables.dispose()
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

}