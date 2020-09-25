@file:Suppress("UnnecessaryVariable")

package com.jimandreas.gamepadtest.gamepad

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

class BluetoothData(contextIn: Context)
    : InputManager.InputDeviceListener {
    private val contextLocal = contextIn
    private lateinit var im: InputManager
    init {
        im = contextLocal.getSystemService(
            Context.INPUT_SERVICE
        ) as InputManager
        im.registerInputDeviceListener(this, Handler(Looper.getMainLooper()))
    }

    private fun scanList(): MutableList<DeviceInfo>? {
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
        if (deviceArray != null) {
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