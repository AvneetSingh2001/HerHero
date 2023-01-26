package com.avicodes.herhero.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avicodes.herhero.R
import com.avicodes.herhero.databinding.FragmentEmergencyBinding


class EmergencyFragment : Fragment() {

    private var _binding : FragmentEmergencyBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEmergencyBinding.inflate(inflater, container, false)
        return binding.root
    }

}