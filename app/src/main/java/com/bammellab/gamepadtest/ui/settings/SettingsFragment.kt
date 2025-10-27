/*
 *
 * Copyright 2023-2025 Bammellab / James Andreas
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 *
 */

@file:Suppress("MoveVariableDeclarationIntoWhen", "UNUSED_VARIABLE")

package com.bammellab.gamepadtest.ui.settings

import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.bammellab.gamepadtest.BuildConfig
import com.bammellab.gamepadtest.R
import com.bammellab.gamepadtest.util.Defs.TEN_Q_GOOD_BUDDY

class SettingsFragment : PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener {


    private var themePreference: ListPreference? = null
    private var savedResources: Resources? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        savedResources = resources
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val context = preferenceScreen.context

        themePreferenceHandler()

        val versionPreference: Preference? = findPreference("app_version")
        val currentVersionString = BuildConfig.VERSION_NAME
        versionPreference!!.summary = currentVersionString
    }

    /**
     * Adjust the Theme list preference
     * With TenQAPI29 and above the Android platform has a system Dark Mode setting.
     * Earlier it was something like a strange Battery Saver.
     * This function hacks the pref list appropriately.
     */
    private fun themePreferenceHandler() {

        try {
            val entries = resources.getStringArray(R.array.theme_array_entries).toMutableList()
            val entryValues = resources.getStringArray(R.array.theme_array_entry_values).toMutableList()

            themePreference  = findPreference(resources.getString(R.string.prefs_theme_key))
            if (TEN_Q_GOOD_BUDDY) {
                entries.remove(entries[2])
                entryValues.remove(entryValues[2])
            } else {
                entries.remove(entries[3])
                entryValues.remove(entryValues[3])
            }
            themePreference?.entries = entries.toTypedArray()
            themePreference?.entryValues = entryValues.toTypedArray()
        } catch (e: Exception) {
            Log.e("Theme", "Something bad happened in themePreferenceHandler")
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences!!.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences!!.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sp: SharedPreferences?, key: String?) {

        val themeKey = savedResources!!.getString(R.string.prefs_theme_key)
        if (key == themeKey) {
            bashTheTheme(sp, savedResources)
        }
    }

    companion object {
        /**
         * Pull the string associated with the theme key and then
         * set up the appropriate theme.   This is done vie the MainActivity
         * and also in response to theme preference changes.
         */
        fun bashTheTheme(sp: SharedPreferences?, r: Resources?) {
            try {
                val themeKey = r!!.getString(R.string.prefs_theme_key)
                val entryValues = r.getStringArray(R.array.theme_array_entry_values)
                val setTo = sp!!.getString(themeKey, /* DEF value */ entryValues[0])
                when (setTo) {
                    entryValues[0] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    entryValues[1] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    else -> {
                        if (TEN_Q_GOOD_BUDDY) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("Theme",  "Something bad happened in bashTheTheme")
            }
        }
    }
}



