/*
 *
 *  * Copyright 2023 Bammellab / James Andreas
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License
 *
 */

@file:Suppress("MoveVariableDeclarationIntoWhen", "UNUSED_VARIABLE")

package com.bammellab.gamepadtest.ui.gamepad

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.hardware.input.InputManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bammellab.gamepadtest.R
import com.bammellab.gamepadtest.databinding.FragmentGamepadBinding
import com.bammellab.gamepadtest.gamepad.BluetoothData
import com.bammellab.gamepadtest.gamepad.GamepadServices
import com.bammellab.gamepadtest.gamepad.LocalBroadcastReceiver

class GamepadFragment : Fragment(),
        InputManager.InputDeviceListener,
        LocalBroadcastReceiver.Callback {

    private lateinit var gamepadViewModel: GamepadViewModel
    private lateinit var binding: FragmentGamepadBinding

    private var redButtonDrawable: Drawable? = null
    private var blueButtonDrawable: Drawable? = null

    private var redOvalButtonDrawable: Drawable? = null
    private var blueOvalButtonDrawable: Drawable? = null

    private var blueStartButtonDrawable: Drawable? = null
    private var redStartButtonDrawable: Drawable? = null

    private var blueBackButtonDrawable: Drawable? = null
    private var redBackButtonDrawable: Drawable? = null

    private lateinit var contextLocal: Context
    private lateinit var bluetoothData : BluetoothData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGamepadBinding.inflate(inflater)
        binding.lifecycleOwner = this
        contextLocal = binding.root.context
        bluetoothData = BluetoothData(contextLocal)
        GamepadServices.broadcastReceiver.setCallback(this)

        gamepadViewModel =
            ViewModelProvider(this).get(GamepadViewModel::class.java)
        binding.viewModel = gamepadViewModel

        val im = contextLocal.getSystemService(
            Context.INPUT_SERVICE
        ) as InputManager
        im.registerInputDeviceListener(this, Handler(Looper.getMainLooper()))

        gamepadViewModel.joyLeft.observe(viewLifecycleOwner, {
            val joy = it
            binding.joystickLeft.updatePosition(joy.first, joy.second)
        })

        gamepadViewModel.joyRight.observe(viewLifecycleOwner, {
            val joy = it
            binding.joystickRight.updatePosition(joy.first, joy.second)
        })

        gamepadViewModel.triggerLeft.observe(viewLifecycleOwner, {
            val trig = it
            updateTriggerColor(binding.lTrigger, trig)
        })

        gamepadViewModel.triggerRight.observe(viewLifecycleOwner, {
            val trig = it
            updateTriggerColor(binding.rTrigger, trig)
        })

        gamepadViewModel.buttonDown.observe(viewLifecycleOwner, {
            handleButtonChange(it)
        })

        // kudos to Dhaval Patel: https://stackoverflow.com/a/52619840/3853712
        redButtonDrawable =
            AppCompatResources.getDrawable(contextLocal, R.drawable.button_bg_round_red)
        blueButtonDrawable =
            AppCompatResources.getDrawable(contextLocal, R.drawable.button_bg_round_blue)

        redOvalButtonDrawable =
            AppCompatResources.getDrawable(contextLocal, R.drawable.button_bg_oval_red)
        blueOvalButtonDrawable =
            AppCompatResources.getDrawable(contextLocal, R.drawable.button_bg_round_blue)

        blueStartButtonDrawable =
            AppCompatResources.getDrawable(contextLocal, R.drawable.ic_table_rows_black_24dp)
        redStartButtonDrawable =
            AppCompatResources.getDrawable(contextLocal, R.drawable.ic_table_rows_red)

        blueBackButtonDrawable =
            AppCompatResources.getDrawable(contextLocal, R.drawable.ic_gamepad_squares)
        redBackButtonDrawable =
            AppCompatResources.getDrawable(contextLocal, R.drawable.ic_gamepad_squares_red)

        updateConnectionStatus()

        return binding.root
    }




    private fun handleButtonChange(keypair: Pair<Int, Boolean>) {
        val keycode = keypair.first
        val downIfTrue = keypair.second
        val colorToSet = if (downIfTrue) {
            Color.GREEN
        } else {
            Color.BLACK
        }

        // for "normal" buttons
        val drawableToSet = if (downIfTrue) {
            redButtonDrawable
        } else {
            blueButtonDrawable
        }
        // for Triggers and Bumpers
        val drawableBTToSet = if (downIfTrue) {
            redOvalButtonDrawable
        } else {
            blueOvalButtonDrawable
        }

        // for Back button
        val drawableBackButton = if (downIfTrue) {
            redBackButtonDrawable
        } else {
            blueBackButtonDrawable
        }

        // for Start button
        val drawableStartButton = if (downIfTrue) {
            redStartButtonDrawable
        } else {
            blueStartButtonDrawable
        }


        var v: View = binding.root
        when (keycode) {
            KeyEvent.KEYCODE_DPAD_LEFT -> v = binding.dpadLeft
            KeyEvent.KEYCODE_DPAD_RIGHT -> v = binding.dpadRight
            KeyEvent.KEYCODE_DPAD_UP -> v = binding.dpadUp
            KeyEvent.KEYCODE_DPAD_DOWN -> v = binding.dpadDown
            KeyEvent.KEYCODE_DPAD_CENTER -> v = binding.dpadCenter

            KeyEvent.KEYCODE_BUTTON_L1 -> binding.lBumper.background = drawableBTToSet
            KeyEvent.KEYCODE_BUTTON_R1 -> binding.rBumper.background = drawableBTToSet

            KeyEvent.KEYCODE_BUTTON_START -> binding.startButton.setImageDrawable(drawableStartButton)
            KeyEvent.KEYCODE_BACK -> binding.backButton.setImageDrawable(drawableBackButton)
            // moved to "SELECT" with Android 11??
            KeyEvent.KEYCODE_BUTTON_SELECT -> binding.backButton.setImageDrawable(drawableBackButton)

            KeyEvent.KEYCODE_BUTTON_A -> binding.aButton.background = drawableToSet
            KeyEvent.KEYCODE_BUTTON_B -> binding.bButton.background = drawableToSet
            KeyEvent.KEYCODE_BUTTON_C -> Log.i("FRAG", "C")


            KeyEvent.KEYCODE_BUTTON_X -> binding.xButton.background = drawableToSet
            KeyEvent.KEYCODE_BUTTON_Y -> binding.yButton.background = drawableToSet
            KeyEvent.KEYCODE_BUTTON_Z -> Log.i("FRAG", "Z")

            KeyEvent.KEYCODE_BUTTON_THUMBL -> binding.joystickLeft.updateIndicator(downIfTrue)
            KeyEvent.KEYCODE_BUTTON_THUMBR -> binding.joystickRight.updateIndicator(downIfTrue)


//            else -> Log.i("FRAG", "Other $keycode")
        }
        if (v != binding.root) {
            v.setBackgroundColor(colorToSet)
        }
        //binding.invalidateAll()

//        val viewSquare : View = root.findViewById(R.id.viewTemp)
//        viewSquare.setBackgroundColor(Color.GRAY)
    }

    private fun updateTriggerColor(trig: View, pressIntensity: Float) {
        if (pressIntensity != 0f) {
            trig.background = redOvalButtonDrawable
        } else {
            trig.background = blueOvalButtonDrawable
        }
    }

    override fun onInputDeviceAdded(deviceId: Int) {
        updateConnectionStatus()
    }

    override fun onInputDeviceRemoved(deviceId: Int) {
        updateConnectionStatus()
    }

    override fun onInputDeviceChanged(deviceId: Int) {
        updateConnectionStatus()
    }

    private fun updateConnectionStatus() {
        /**
         * Now look and see if any input devices are present
         */

        val devList = bluetoothData.scanList()
        val enabled = bluetoothData.isBluetoothEnabled()
        val statusString = when (devList.size) {
            0 -> "No controllers found"
            1 -> "1 controller found"
            else -> "${devList.size} controllers found"
        }
        binding.bluetoothStatus.text = if (enabled) {
            "Bluetooth is on"
        } else {
            "Bluetooth is off"
        }
        binding.inputDeviceStatus.text = statusString
    }

    override fun updateBluetoothStatus(state: Int) {
        when (state) {
            BluetoothAdapter.STATE_OFF -> binding.bluetoothStatus.text =  "Bluetooth is off"
            //BluetoothAdapter.STATE_TURNING_OFF -> binding.bluetoothStatus.text =  "Bluetooth is turning on"
            BluetoothAdapter.STATE_ON -> binding.bluetoothStatus.text =  "Bluetooth is on"
            //BluetoothAdapter.STATE_TURNING_ON -> binding.bluetoothStatus.text =  "Bluetooth is turning on"
        }
    }
}