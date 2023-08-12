/*
 *  Copyright 2020 James Andreas
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

@file:Suppress("UnnecessaryVariable")

package com.bammellab.gamepadtest.util

import android.annotation.SuppressLint
import android.content.Context
import androidx.preference.PreferenceManager
import java.util.*

@SuppressLint("StaticFieldLeak")
object PrefsUtil {
    var prefsContext : Context? = null

    fun getBool(key: String, defaultValue: Boolean): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(prefsContext!!)
        val boolVal = prefs.getBoolean(key, defaultValue)
        return boolVal
    }
    fun getStringSet(key: String, defaultValue: Set<String>): Set<String>? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(prefsContext!!)
        val set = prefs.getStringSet(key, defaultValue)
        return if (set == null) null else Collections.unmodifiableSet(set)
    }

    fun setStringSet(key: String?, value: Set<String?>?) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(prefsContext!!)
        prefs.edit().putStringSet(key, value).apply()
    }

    const val PREVIOUS_SEARCHES_KEY = "psk"
}