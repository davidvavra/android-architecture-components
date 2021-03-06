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

package com.example.android.observability.ui

import android.arch.lifecycle.ViewModel
import com.example.android.observability.UserDataSource
import com.example.android.observability.persistence.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.functions.Action
import io.reactivex.internal.operators.completable.CompletableFromAction

/**
 * View Model for the [UserActivity]
 */
class UserViewModel(private val mDataSource: UserDataSource) : ViewModel() {

    private var mUser: User? = null

    /**
     * Get the user name of the user.

     * @return a [Flowable] that will emit every time the user name has been updated.
     */
    // for every emission of the user, get the user name
    val userName: Flowable<String>
        get() = mDataSource.getUser()
                .map { (_, userName1) -> userName1 }

    /**
     * Update the user name.

     * @param userName the new user name
     * *
     * @return a [Completable] that completes when the user name is updated
     */
    fun updateUserName(userName: String): Completable {
        return CompletableFromAction(Action {
            // if there's no use, create a new user.
            // if we already have a user, then, since the user object is immutable,
            // create a new user, with the id of the previous user and the updated user name.
            if (mUser == null) {
                mUser = User()
            }
            mUser?.userName = userName
            mUser?.let { mDataSource.insertOrUpdateUser(it) }
        })
    }
}
