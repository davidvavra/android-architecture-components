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

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import com.example.android.observability.provideViewModelFactory
import com.example.android.persistence.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user.*


/**
 * Main screen of the app. Displays a user name and gives the option to update the user name.
 */
class UserActivity : LifecycleActivity() {

    private lateinit var mViewModelFactory: ViewModelFactory

    private lateinit var mViewModel: UserViewModel

    private val mDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        mViewModelFactory = provideViewModelFactory()
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel::class.java)
        vUpdate.setOnClickListener { updateUserName() }
    }

    override fun onStart() {
        super.onStart()
        // Subscribe to the emissions of the user name from the view model.
        // Update the user name text view, at every onNext emission.
        // In case of error, log the exception.
        mDisposable.add(mViewModel.userName
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userName -> vUserName.text = userName }) { throwable -> Log.e(TAG, "Unable to update username", throwable) })
    }

    override fun onStop() {
        super.onStop()

        // clear all the subscription
        mDisposable.clear()
    }

    private fun updateUserName() {
        val userName = vInput.text.toString()
        // Disable the update button until the user name update has been done
        vUpdate.isEnabled = false
        // Subscribe to updating the user name.
        // Enable back the button once the user name has been updated
        mDisposable.add(mViewModel.updateUserName(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ vUpdate.isEnabled = true }) { throwable -> Log.e(TAG, "Unable to update username", throwable) })
    }

    companion object {

        private val TAG = UserActivity::class.java.simpleName
    }
}
