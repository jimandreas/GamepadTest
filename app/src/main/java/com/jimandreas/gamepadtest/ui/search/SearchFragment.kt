@file:Suppress("UnnecessaryVariable")

package com.jimandreas.gamepadtest.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jimandreas.gamepadtest.R

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    //val joystickHandler = GamepadJoysticks()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)
//        val textView: TextView = root.findViewById(R.id.text_search)
//        searchViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

//        root.setOnGenericMotionListener(View.OnGenericMotionListener { v, event ->
//
//            // Check that the event came from a game controller
//            if (event.source and InputDevice.SOURCE_JOYSTICK == InputDevice.SOURCE_JOYSTICK
//                && event.action == MotionEvent.ACTION_MOVE) {
//
//                // Process the movements starting from the
//                // earliest historical position in the batch
//                (0 until event.historySize).forEach { i ->
//                    // Process the event at historical position i
//                    joystickHandler.processJoystickInput(event, i)
//                }
//
//                // Process the current movement sample in the batch (position -1)
//                joystickHandler.processJoystickInput(event, -1)
//                true
//            } else {
//                false
//            }
//        })
        //root.requestFocus()

        return root
    }

}