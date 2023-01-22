package com.avicodes.herhero.presentation.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.avicodes.herhero.databinding.FragmentHomeScreenBinding


class HomeScreenFragment : Fragment() {

    private var _binding : FragmentHomeScreenBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            copyButton.setOnClickListener {
                val clipboardManager = activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("text", tvCode.text.toString())
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(requireContext(), "Text copied to clipboard", Toast.LENGTH_LONG).show()
            }
        }


    }
}