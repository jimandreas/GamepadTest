@file:Suppress("UnnecessaryVariable")

package com.jimandreas.gamepadtest.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.jimandreas.gamepadtest.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }


}
