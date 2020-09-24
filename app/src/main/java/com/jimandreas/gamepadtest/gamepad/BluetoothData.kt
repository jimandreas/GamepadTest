@file:Suppress("UnnecessaryVariable")

package com.jimandreas.gamepadtest.gamepad

import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.content.Context.BLUETOOTH_SERVICE
import android.hardware.input.InputManager
import android.util.Log
import android.view.InputDevice
import android.view.KeyCharacterMap
import androidx.core.content.ContextCompat
import com.jimandreas.gamepadtest.R

data class DeviceInfo(
    var productName: String? = null,
    var sources: Int = 0,
    var keyMap: KeyCharacterMap? = null,
    var keyType: Int = -1,
    var controllerNum: Int = 0,
    var descriptor: String = "",  // is this useful for developers?
    var inputDeviceDataCopy: InputDevice? = null
)

class BluetoothData {



    fun scanList(contextIn: Context): MutableList<DeviceInfo>? {
        val inputManager = contextIn.getSystemService(
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

    fun assembleDescriptionStrings(contextIn: Context): List<String> {

        val manager = contextIn.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager?
        // profile GATT or GATT_SERVER supported GATT is Bluetooth Low Energy BLE
        val connected = manager!!.getConnectedDevices(BluetoothProfile.GATT_SERVER)
        Log.i("Connected Devices: ", connected.size.toString() + "")


        val sourceToString = SourceDataToString()
        val descList = mutableListOf<String>()
        val deviceArray = scanList(contextIn)
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
}