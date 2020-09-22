@file:Suppress("UnnecessaryVariable")

package com.jimandreas.gamepadtest.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jimandreas.gamepadtest.AboutActivity
import com.jimandreas.gamepadtest.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
                ViewModelProvider(this).get(SettingsViewModel::class.java)

//        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        val binding = FragmentSettingsBinding.inflate(inflater)

        binding.settingsAboutLink.setOnClickListener {
            val intent = Intent(binding.root.context, AboutActivity::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

        }
        return binding.root
    }
}



//        val textView: TextView = root.findViewById(R.id.text_settings)
//        settingsViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })