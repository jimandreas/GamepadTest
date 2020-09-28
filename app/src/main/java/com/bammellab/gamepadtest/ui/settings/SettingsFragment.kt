package com.bammellab.gamepadtest.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.bammellab.gamepadtest.BuildConfig
import com.bammellab.gamepadtest.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val versionPreference = findPreference("app_version")
        val currentVersionString = BuildConfig.VERSION_NAME
        versionPreference.summary = currentVersionString
    }

    companion object {
        const val PREFERENCE_KEY = "settings"
        const val PREFERENCE_MOTION_EVENTS = "motion"
    }
}


