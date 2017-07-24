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

package com.android.example.github.vo

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import com.google.gson.annotations.SerializedName

/**
 * Using name/owner_login as primary key instead of id since name/owner_login is always available
 * vs id is not.
 */
@Entity(indices = arrayOf(Index("id"), Index("owner_login")), primaryKeys = arrayOf("name", "owner_login"))
data class Repo(var id: Int,
                @SerializedName("name")
                var name: String,
                @SerializedName("full_name")
                var fullName: String,
                @SerializedName("description")
                var description: String,
                @SerializedName("owner")
                @Embedded(prefix = "owner_")
                var owner: Owner,
                @SerializedName("stargazers_count")
                var stars: Int) {
    constructor() : this(UNKNOWN_ID, "", "", "", Owner(), 0)

    companion object {
        val UNKNOWN_ID = -1
    }
}

data class Owner(@SerializedName("login")
                 var login: String, @SerializedName("url")
                 var url: String?) {
    constructor() : this("", null)
}
