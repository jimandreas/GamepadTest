package com.bammellab.gamepadtest.gamepad

import android.view.InputDevice.*

class SourceDataToString {
    private val sourceToStringMap = HashMap<Int, String>()

    init {

        // SOURCE_CLASS_MASK = 0x000000ff
        // sourceToStringMap[0x00000000] =  "NONE"
        sourceToStringMap[0x00000001] =  "BUTTON"
        sourceToStringMap[0x00000002] =  "POINTER"
        sourceToStringMap[0x00000004] =  "TRACKBALL"
        sourceToStringMap[0x00000008] =  "POSITION"
        // sourceToStringMap[0x00000010] =  "JOYSTICK"
        // sourceToStringMap[0x00000000] =  "SOURCE_UNKNOWN"

        sourceToStringMap[0x00000100 or SOURCE_CLASS_BUTTON] = "KEYBOARD"
        sourceToStringMap[0x00000200 or SOURCE_CLASS_BUTTON] = "DPAD"
        sourceToStringMap[0x00000400 or SOURCE_CLASS_BUTTON] = "GAMEPAD"
        sourceToStringMap[0x00001000 or SOURCE_CLASS_POINTER] = "TOUCHSCREEN"
        sourceToStringMap[0x00002000 or SOURCE_CLASS_POINTER] = "MOUSE"
        sourceToStringMap[0x00004000 or SOURCE_CLASS_POINTER] = "STYLUS"
        sourceToStringMap[0x00010000 or SOURCE_CLASS_TRACKBALL] = "TRACKBALL"
        sourceToStringMap[0x00020000 or SOURCE_CLASS_TRACKBALL] = "MOUSE_RELATIVE"
        sourceToStringMap[0x00100000 or SOURCE_CLASS_POSITION] = "TOUCHPAD"
        sourceToStringMap[0x00200000 or SOURCE_CLASS_NONE] = "TOUCH_NAVIGATION"
        sourceToStringMap[0x00400000 or SOURCE_CLASS_NONE] = "ROTARY_ENCODER"
        sourceToStringMap[0x01000000 or SOURCE_CLASS_JOYSTICK] = "JOYSTICK"
        sourceToStringMap[0x02000001 or SOURCE_CLASS_BUTTON] = "HDMI"
    }

    fun getSourceFeatures(packedData: Int): String {
        var retString = ""
        for (entry in sourceToStringMap) {
            if ((packedData and entry.key) == entry.key) {
                retString += "${entry.value}\n"
            }
        }
        return retString
    }

}
