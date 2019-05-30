# VenuesNearby
## Clean architecture prototype app that allows a user to enter a place name, submit, and then view the recommended venues nearby provided by Foursquare API.

![venuesnearby_screen_recording_resized](https://user-images.githubusercontent.com/2387056/58613505-98c5ae80-82be-11e9-94d0-478171723b6f.gif)

## Description
This is a demonstration app that allows a user to enter a place name, submit, and then
view the recommended venues nearby. The venues are provided by the Foursquare API which you can integrate with. You will need to sign up as a Foursquare developer and create an app in order to use their
services. Feel free to use the Foursquare public token available from trying out the API endpoints
on the foursquare developer website. 

Api used: https://developer.foursquare.com/docs/api/venues/explore

## Programming language
- Kotlin

## Build system
- Gradle

## Architecture
- Clean architecture
- MVVM
- Reactive

![app_architecture](https://user-images.githubusercontent.com/2387056/58619904-82275380-82ce-11e9-91eb-fac91c276762.png)

## Libraries
- **Android Lifecycles Compiler** - Android Lifecycles annotation processor.
- **Android Lifecycle Extensions** - ViewModel and LiveData.
- **AndroidX Test Library**
  - test:core - Android Core testing
  - test:rules
  - test:runner
- **AppCompat** - This library adds support for the Action Bar user interface design pattern. It includes support for material design user interface implementations.
- **ConstraintLayout** - A ConstraintLayout is a ViewGroup which allows you to position and size widgets in a flexible way.
- **Dagger** - It is a fully static, compile-time dependency injection framework for both Java and Android.
- **Espresso** - Use Espresso to write concise, beautiful, and reliable Android UI tests.
  - espresso-core - Contains core and basic View matchers, actions, and assertions.
  - espresso-contrib - External contributions that contain DatePicker, RecyclerView and Drawer actions, accessibility checks, and CountingIdlingResource.
- **Javax Inject** - This package specifies a means for obtaining objects in such a way as to maximize reusability, testability and maintainability compared to traditional approaches such as constructors, factories, and service locators (e.g., JNDI). This process, known as dependency injection, is beneficial to most nontrivial applications. 
- **Kotlin Standard Library JDK 8** - Kotlin Standard Library for Java JDK 8 extension.
- **kotlin-test-junit** - kotlin.test library module, which provides an implementation of Asserter on top of JUnit and maps the test annotations from kotlin-test-annotations-common to JUnit test annotations.
- **Mockito** - Most popular Mocking framework for unit tests written in Java.
  - mockito-core - Mockito mock objects library core API and implementation.
  - mockito-android - Mockito for Android.
- **OkHttp** - An HTTP & HTTP/2 client for Android and Java applications.
  - OkHttp Logging Interceptor - An OkHttp interceptor which logs HTTP request and response data.
- **RecyclerView** - A flexible view for providing a limited window into a large data set.
- **Retrofit** - A type-safe HTTP client for Android and Java.
  - Retrofit RxJava2 Adapter - An Adapter for adapting RxJava 2.x types.
  - Retrofit Gson Converter - A Converter which uses Gson for serialization to and from JSON.
- **Robolectric** - A framework that brings fast and reliable unit tests to Android. Tests run inside the JVM on your workstation in seconds.
- **Room** - The persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
  - room-compiler - Android Room annotation processor.
  - room-runtime - Android Room runtime.
  - room-testing - Android Room testing.
  - room-rxjava2 - RxJava2 support for Android Room.
- **RxAndroid** - Reactive extensions for Android.
- **RxKotlin** - Kotlin Extensions for RxJava. RxKotlin is a lightweight library that adds convenient extension functions to RxJava. You can use RxJava with Kotlin out-of-the-box, but Kotlin has language features (such as extension functions) that can streamline usage of RxJava even more. 
- **Stetho** - Facebook's Stetho project enables you to use Chrome debugging tools to troubleshoot network traffic, database files, and view layouts.


## License

    Copyright 2019 Andrius Baruckis www.baruckis.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
