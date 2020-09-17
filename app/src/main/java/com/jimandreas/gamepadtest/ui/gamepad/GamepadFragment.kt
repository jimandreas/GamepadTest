@file:Suppress("MoveVariableDeclarationIntoWhen", "UNUSED_VARIABLE")

package com.jimandreas.gamepadtest.ui.gamepad

import android.content.Context
import android.graphics.Color
import android.hardware.input.InputManager
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jimandreas.gamepadtest.R
import com.jimandreas.gamepadtest.databinding.FragmentGamepadBinding
import com.jimandreas.gamepadtest.ui.customviews.JoystickView


class GamepadFragment : Fragment(), Observer<Int> {

    private lateinit var gamepadViewModel: GamepadViewModel
    private lateinit var binding: FragmentGamepadBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGamepadBinding.inflate(inflater)
        binding.lifecycleOwner = this

        /**
         *  EXPERIMENTAL
         */
        val inputManager = binding.root.context.getSystemService(
            Context.INPUT_SERVICE
        ) as InputManager
        /**
         * END
         */

        gamepadViewModel =
            ViewModelProvider(this).get(GamepadViewModel::class.java)
        // root = inflater.inflate(R.layout.fragment_gamepad, container, false)
        binding.viewModel = gamepadViewModel

        // val textView: TextView = root.findViewById(R.id.text_gamepad)
        gamepadViewModel.text.observe(viewLifecycleOwner, {
            binding.textGamepad.text = it
        })

        // val joystickViewLeft : JoystickView = root.findViewById(R.id.joystickLeft)
        gamepadViewModel.joyLeft.observe(viewLifecycleOwner, {
            val joy = it
            Log.i("JOYSTICK FRAGMENT", joy.first.toString() + " " + joy.second.toString())
            binding.joystickLeft.updatePosition(joy.first, joy.second)
        })

        // val joystickViewRight : JoystickView = root.findViewById(R.id.joystickRight)
        gamepadViewModel.joyRight.observe(viewLifecycleOwner, {
            val joy = it
            Log.i("JOYSTICK FRAGMENT", joy.first.toString() + " " + joy.second.toString())
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


//        val viewThing: View = root.findViewById(R.id.dpadDown)
//        viewThing.setBackgroundColor(Color.GRAY)

        return binding.root
    }



    private fun handleButtonChange(keypair: Pair<Int, Boolean>) {
        val keycode = keypair.first
        val colorToSet = if (keypair.second) {
            Color.GREEN
        } else {
            Color.RED
        }

        var v : View = binding.root
        when (keycode) {
            KeyEvent.KEYCODE_DPAD_LEFT -> v = binding.dpadLeft
            KeyEvent.KEYCODE_DPAD_RIGHT -> v = binding.dpadRight
            KeyEvent.KEYCODE_DPAD_UP -> v = binding.dpadUp
            KeyEvent.KEYCODE_DPAD_DOWN -> v = binding.dpadDown
            KeyEvent.KEYCODE_DPAD_CENTER -> v = binding.dpadCenter

            KeyEvent.KEYCODE_BUTTON_L1 -> v = binding.lBumper
            KeyEvent.KEYCODE_BUTTON_R1 -> v = binding.rBumper

            KeyEvent.KEYCODE_BUTTON_A -> Log.i("FRAG", "A")
            KeyEvent.KEYCODE_BUTTON_B -> Log.i("FRAG", "B")
            KeyEvent.KEYCODE_BUTTON_C -> Log.i("FRAG", "C")


            KeyEvent.KEYCODE_BUTTON_X -> Log.i("FRAG", "X")
            KeyEvent.KEYCODE_BUTTON_Y -> Log.i("FRAG", "Y")
            KeyEvent.KEYCODE_BUTTON_Z -> Log.i("FRAG", "Z")

            KeyEvent.KEYCODE_BUTTON_THUMBL -> binding.lBumper.setBackgroundColor(View.INVISIBLE)
            KeyEvent.KEYCODE_BUTTON_THUMBR -> binding.rBumper.setBackgroundColor(Color.GREEN)


            else -> Log.i("FRAG", "Other $keycode")
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
            trig.setBackgroundColor(Color.GRAY)
        } else {
            trig.setBackgroundColor(Color.RED)
        }
    }

    /**
     * OnChanged is needed for the Observer interface
     */
    override fun onChanged(t: Int?) {
        TODO("Not yet implemented")
    }
}