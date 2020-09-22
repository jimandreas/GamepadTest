@file:Suppress("MoveVariableDeclarationIntoWhen", "UNUSED_VARIABLE")

package com.jimandreas.gamepadtest.ui.gamepad

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.hardware.input.InputManager
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jimandreas.gamepadtest.R
import com.jimandreas.gamepadtest.databinding.FragmentGamepadBinding


class GamepadFragment : Fragment(), Observer<Int> {

    private lateinit var gamepadViewModel: GamepadViewModel
    private lateinit var binding: FragmentGamepadBinding

    private var redButtonDrawable: Drawable? = null
    private var blueButtonDrawable: Drawable? = null

    private var redOvalButtonDrawable: Drawable? = null
    private var blueOvalButtonDrawable: Drawable? = null

    private lateinit var bcontext: Context

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
        binding.viewModel = gamepadViewModel
        bcontext = binding.root.context

        gamepadViewModel.joyLeft.observe(viewLifecycleOwner, {
            val joy = it
            Log.i("JOYSTICK FRAGMENT", joy.first.toString() + " " + joy.second.toString())
            binding.joystickLeft.updatePosition(joy.first, joy.second)
        })

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

        // kudos to Dhaval Patel: https://stackoverflow.com/a/52619840/3853712
        redButtonDrawable =
            AppCompatResources.getDrawable(bcontext, R.drawable.button_bg_round_red)
        blueButtonDrawable =
            AppCompatResources.getDrawable(bcontext, R.drawable.button_bg_round_blue)

        redOvalButtonDrawable =
            AppCompatResources.getDrawable(bcontext, R.drawable.button_bg_oval_red)
        blueOvalButtonDrawable =
            AppCompatResources.getDrawable(bcontext, R.drawable.button_bg_round_blue)

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


        var v: View = binding.root
        when (keycode) {
            KeyEvent.KEYCODE_DPAD_LEFT -> v = binding.dpadLeft
            KeyEvent.KEYCODE_DPAD_RIGHT -> v = binding.dpadRight
            KeyEvent.KEYCODE_DPAD_UP -> v = binding.dpadUp
            KeyEvent.KEYCODE_DPAD_DOWN -> v = binding.dpadDown
            KeyEvent.KEYCODE_DPAD_CENTER -> v = binding.dpadCenter

            KeyEvent.KEYCODE_BUTTON_L1 -> binding.lBumper.background = drawableBTToSet
            KeyEvent.KEYCODE_BUTTON_R1 -> binding.rBumper.background = drawableBTToSet

            KeyEvent.KEYCODE_BUTTON_A -> binding.aButton.background = drawableToSet
            KeyEvent.KEYCODE_BUTTON_B -> binding.bButton.background = drawableToSet
            KeyEvent.KEYCODE_BUTTON_C -> Log.i("FRAG", "C")


            KeyEvent.KEYCODE_BUTTON_X -> binding.xButton.background = drawableToSet
            KeyEvent.KEYCODE_BUTTON_Y -> binding.yButton.background = drawableToSet
            KeyEvent.KEYCODE_BUTTON_Z -> Log.i("FRAG", "Z")

            KeyEvent.KEYCODE_BUTTON_THUMBL -> binding.lBumper.setBackgroundColor(View.INVISIBLE)
            KeyEvent.KEYCODE_BUTTON_THUMBR -> binding.rBumper.setBackgroundColor(Color.GREEN)


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

    /**
     * OnChanged is needed for the Observer interface
     */
    override fun onChanged(t: Int?) {
        TODO("Not yet implemented")
    }
}