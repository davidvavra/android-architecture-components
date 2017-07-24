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

package com.example.android.observability.persistence

import com.example.android.observability.UserDataSource

import io.reactivex.Flowable

/**
 * Using the Room database as a data source.
 */
class LocalUserDataSource(private val mUserDao: UserDao) : UserDataSource {

    override fun getUser(): Flowable<User> {
        return mUserDao.user
    }

    override fun insertOrUpdateUser(user: User) {
        mUserDao.insertUser(user)
    }

    override fun deleteAllUsers() {
        mUserDao.deleteAllUsers()
    }
}
