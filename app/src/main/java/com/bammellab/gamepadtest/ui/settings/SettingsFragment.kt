package com.bammellab.gamepadtest.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.bammellab.gamepadtest.R


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    companion object {
        const val PREFERENCE_KEY = "settings"
        const val PREFERENCE_MOTION_EVENTS = "motion"
    }
}


