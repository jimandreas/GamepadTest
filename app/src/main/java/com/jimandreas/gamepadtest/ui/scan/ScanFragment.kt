@file:Suppress("UnnecessaryVariable")

package com.jimandreas.gamepadtest.ui.scan

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jimandreas.gamepadtest.R
import com.jimandreas.gamepadtest.databinding.FragmentScanBinding
import com.jimandreas.gamepadtest.gamepad.GamepadServices
import com.jimandreas.gamepadtest.gamepad.SourceDataToString

class ScanFragment : Fragment() {

    private lateinit var scanViewModel: ScanViewModel
    private lateinit var binding: FragmentScanBinding
    private lateinit var bcontext: Context
    private val sourceToString = SourceDataToString()

    private lateinit var theRecyclerView : RecyclerView
    private lateinit var deviceAdapter : DeviceAdapter

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

// works but not the right context
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
                    str.append("name: $productName\n")
                    str.append("source bits (hex) decoded: ${sources.toString(16)}\n")
                    str.append(sourceToString.getSourceFeatures(sources))
                    binding.deviceInfoText.text = str.toString()

                }
            } else {
                binding.deviceInfoText.text = getString(R.string.no_controllers)
            }
        }

        theRecyclerView = binding.recyclerListThing
        val layoutManager = LinearLayoutManager(binding.root.context)
        theRecyclerView.layoutManager = layoutManager
        theRecyclerView.setHasFixedSize(false)

        deviceAdapter = DeviceAdapter(binding.root.context, object : DeviceAdapter.DeviceAdapterOnClickHandler {
            override fun onClick() {
                Toast.makeText(
                    activity,
                    "Recycler view - CardView click",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        theRecyclerView.adapter = deviceAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }

}