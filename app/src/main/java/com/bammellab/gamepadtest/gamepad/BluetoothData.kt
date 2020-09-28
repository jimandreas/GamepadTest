@file:Suppress("UnnecessaryVariable", "LiftReturnOrAssignment", "MemberVisibilityCanBePrivate",
    "UNUSED_VARIABLE", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE"
)

package com.bammellab.gamepadtest.gamepad

import android.bluetooth.*
import android.content.Context
import android.hardware.input.InputManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.InputDevice
import android.view.KeyCharacterMap

data class DeviceInfo(
    var productName: String? = null,
    var sources: Int = 0,
    var keyMap: KeyCharacterMap? = null,
    var keyType: Int = -1,
    var controllerNum: Int = 0,
    var descriptor: String = "",  // is this useful for developers?
    var inputDeviceDataCopy: InputDevice? = null
)

class BluetoothData(contextIn: Context) : InputManager.InputDeviceListener {
    private val contextLocal = contextIn
    private var im: InputManager
    private var bluetoothAdapter: BluetoothAdapter? = null
    init {
        im = contextLocal.getSystemService(
            Context.INPUT_SERVICE
        ) as InputManager
        im.registerInputDeviceListener(this, Handler(Looper.getMainLooper()))
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()


        /*
         * this section is a place holder for trying to obtain
         * data on devices as the connect and disconnect.
         * Right now it is not obvious how to match
         * Bluetooth profiles with Input devices.
         * Just how to join the two sets of data is not obvious to me.
         */
        // removed - this section throws an exception in emulators.
        /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val manager = contextLocal.getSystemService(BluetoothManager::class.java)
            manager.adapter.getProfileProxy(
                contextLocal,
                object : BluetoothProfile.ServiceListener {
                    override fun onServiceConnected(profile: Int, proxy: BluetoothProfile) {
                        val devs = proxy.connectedDevices
                        val numDevs = devs.size

                    }

                    override fun onServiceDisconnected(profile: Int) {
                        val doSomethingHere = profile
                    }
                },
                4 // @Hide: int HID_HOST = 4;  Input devices go in this Profile
            )
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val manager = contextLocal.getSystemService(BluetoothManager::class.java)
            manager.adapter.getProfileProxy(contextLocal, object : BluetoothProfile.ServiceListener {
                override fun onServiceConnected(profile: Int, proxy: BluetoothProfile) {
                    val devs = proxy.connectedDevices
                    val numDevs = devs.size
                }

                override fun onServiceDisconnected(profile: Int) {
                    val doSomethingHere = profile
                }
            }, BluetoothProfile.HEADSET)
        }*/

        var bluetoothHeadset: BluetoothHeadset? = null

        // Get the default adapter
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        val profileListener = object : BluetoothProfile.ServiceListener {

            override fun onServiceConnected(profile: Int, proxy: BluetoothProfile) {
                if (profile == BluetoothProfile.HEADSET) {
                    bluetoothHeadset = proxy as BluetoothHeadset
                }
            }

            override fun onServiceDisconnected(profile: Int) {
                if (profile == BluetoothProfile.HEADSET) {
                    bluetoothHeadset = null
                }
            }
        }
        /*
         * end of placeholder dynamic listener handlers.  Not used currently.
         */


    }

    fun isBluetoothEnabled(): Boolean {
        if (bluetoothAdapter != null) {
            val state = bluetoothAdapter!!.isEnabled
            return state
        } else {
            return false
        }
    }

    fun scanList(): MutableList<DeviceInfo> {
        val inputManager = contextLocal.getSystemService(
            Context.INPUT_SERVICE
        ) as InputManager

        val devArray = mutableListOf<DeviceInfo>()

        val idArray = inputManager.inputDeviceIds
        for (i in idArray) {
            if (i > 0) {

                val dev = inputManager.getInputDevice(i)

                if (dev.controllerNumber > 0) {
                    val entry = DeviceInfo()
                    entry.productName = dev.name
                    entry.sources = dev.sources
                    entry.controllerNum = dev.controllerNumber
                    entry.descriptor = dev.descriptor
                    entry.keyMap = dev.keyCharacterMap
                    entry.keyType = dev.keyboardType
                    entry.inputDeviceDataCopy = dev
                    devArray.add(entry)

                }
            }
        }
        return devArray
    }

    fun assembleDescriptionStrings(): List<String> {
        val sourceToString = SourceDataToString()
        val descList = mutableListOf<String>()
        val deviceArray = scanList()
        var overviewString = "Input Controllers connected: ${deviceArray.size}"
        if (!isBluetoothEnabled()) {
            overviewString += "\nBluetooth is not turned on"
        }
        descList.add(overviewString)
        if (deviceArray.size > 0) {
            for (entry in deviceArray) {
                with(entry) {
                    val str = StringBuilder("Controller: $controllerNum\n")
                    str.append("name: $productName\n")
                    str.append("source bits (hex) decoded: ${sources.toString(16)}\n")
                    str.append(sourceToString.getSourceFeatures(sources))
                    descList.add(str.toString())

                }
            }
        }
        val retVal = descList.map { it }
        return retVal
    }

    override fun onInputDeviceAdded(deviceId: Int) {
        Log.i("BLUETOOTH:", "Device added $deviceId")
    }

    override fun onInputDeviceRemoved(deviceId: Int) {
        Log.i("BLUETOOTH:", "Device removed $deviceId")
    }

    override fun onInputDeviceChanged(deviceId: Int) {
        Log.i("BLUETOOTH:", "Device changed $deviceId")
    }
}