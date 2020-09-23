@file:Suppress("UnnecessaryVariable")

package com.jimandreas.gamepadtest.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jimandreas.gamepadtest.databinding.FragmentSearchBinding
import com.jimandreas.gamepadtest.util.Util.attachClickToCopyText

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding
    private lateinit var bcontext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        binding.lifecycleOwner = this

        searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)
        binding.viewModel = searchViewModel
        bcontext = binding.root.context

//        attachClickToCopyText(binding.someTextView,
//            binding.someTextView.text.toString(), binding.root.context)

        binding.cardview.setOnClickListener { Toast.makeText(activity, "CardView click", Toast.LENGTH_SHORT).show() }


        return binding.root
    }

}