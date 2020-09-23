package com.jimandreas.gamepadtest.gamepad

import android.content.Context
import android.hardware.input.InputManager
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

class BluetoothData {

    val devArray = mutableListOf<DeviceInfo>()

    fun scanList(contextIn: Context): MutableList<DeviceInfo>? {
        val inputManager = contextIn.getSystemService(
            Context.INPUT_SERVICE
        ) as InputManager

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
}