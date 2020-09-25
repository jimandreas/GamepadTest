package com.jimandreas.gamepadtest.ui.settings

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle

import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.jimandreas.gamepadtest.R


class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.pref_sandbox)
        // Preferences.sync(getPreferenceManager());

        // gist from:  https://gist.github.com/yujikosuga/1234287
        var pref = findPreference("fmi_key")
        pref.onPreferenceClickListener = Preference.OnPreferenceClickListener { preference ->
            val key = preference.key

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(
                    "https://github.com/jimandreas/BottomNavigationView-plus-LeakCanary/blob/master/README.md")
            startActivity(intent)
            true
        }

        pref = findPreference("nightmode")
        pref.onPreferenceClickListener = Preference.OnPreferenceClickListener { preference ->
            val key = preference.key
            val nightModeSetting = PreferenceManager
                    .getDefaultSharedPreferences(activity)
                    .getBoolean("nightmode", false)

            AppCompatDelegate
                    .setDefaultNightMode(if (nightModeSetting) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)

            true
        }
    }

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {}

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        val key = preference?.key
        return super.onPreferenceTreeClick(preference)
    }
}