/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.observability

import android.content.Context

import com.example.android.observability.persistence.LocalUserDataSource
import com.example.android.observability.persistence.UsersDatabase
import com.example.android.observability.ui.ViewModelFactory

/**
 * Enables injection of data sources.
 */

fun Context.provideUserDataSource(): UserDataSource {
    val database = UsersDatabase.getInstance(this)
    return LocalUserDataSource(database.userDao())
}

fun Context.provideViewModelFactory(): ViewModelFactory {
    val dataSource = this.provideUserDataSource()
    return ViewModelFactory(dataSource)
}
