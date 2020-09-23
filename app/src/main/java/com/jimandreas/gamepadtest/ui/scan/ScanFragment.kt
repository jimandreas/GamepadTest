@file:Suppress("UnnecessaryVariable")

package com.jimandreas.gamepadtest.ui.scan

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.view.InputDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jimandreas.gamepadtest.R
import com.jimandreas.gamepadtest.databinding.FragmentScanBinding
import com.jimandreas.gamepadtest.gamepad.GamepadServices

class ScanFragment : Fragment() {

    private lateinit var scanViewModel: ScanViewModel
    private lateinit var binding: FragmentScanBinding
    private lateinit var bcontext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScanBinding.inflate(inflater)
        binding.lifecycleOwner = this

        scanViewModel =
            ViewModelProvider(this).get(ScanViewModel::class.java)
        binding.viewModel = scanViewModel
        bcontext = binding.root.context

//        attachClickToCopyText(binding.someTextView,
//            binding.someTextView.text.toString(), binding.root.context)

        binding.cardview.setOnClickListener {
            Toast.makeText(
                activity,
                "CardView click",
                Toast.LENGTH_SHORT
            ).show()
        }

        /*
         * pull a copy of the existing bluetooth conns
         */
        val scanner = GamepadServices.bluetoothData
        val deviceArray = scanner.scanList(binding.root.context)
        if (deviceArray != null) {
            if (deviceArray.size > 0) {
                with(deviceArray[0]) {
                    val str = StringBuilder("Controller: $controllerNum\n")
                    str.append("name: $descString\n")
                    binding.deviceInfoText.text = str

                }
            } else {
                binding.deviceInfoText.text = getString(R.string.no_controllers)
            }
        }

        return binding.root
    }

}