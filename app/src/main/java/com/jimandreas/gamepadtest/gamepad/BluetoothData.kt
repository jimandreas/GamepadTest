package com.jimandreas.gamepadtest.gamepad

import android.content.Context
import android.hardware.input.InputManager
import android.util.Log
import android.view.InputDevice
import android.view.KeyCharacterMap

data class DeviceInfo(
    var descString: String = "",
    var keyMap: KeyCharacterMap? = null,
    var keyType: Int = -1,
    var controllerNum: Int = 0,
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
                    entry.controllerNum = dev.controllerNumber
                    entry.descString = dev.descriptor
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