package com.bammellab.gamepadtest.gamepad


object GamepadServices {
    val gamepadJoystickService = GamepadJoysticks()
    val gamepadButtonService = GamepadButton()
    val keycodes = KeycodeNumToString()
    val gamepadLoggerService = GamepadLogger()
}