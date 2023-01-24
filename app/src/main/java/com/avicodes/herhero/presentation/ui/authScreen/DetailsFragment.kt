package com.avicodes.herhero.presentation.ui.authScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.avicodes.herhero.R
import com.avicodes.herhero.data.models.Users
import com.avicodes.herhero.data.utils.Response
import com.avicodes.herhero.data.utils.ValidateResponse
import com.avicodes.herhero.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as DetialsActivity).viewModel

        binding.apply {

            btnContinue.setOnClickListener {

                    viewModel.addUser(
                        name = tvName.editText?.text.toString(),
                        phone = tvPhone.editText?.text.toString(),
                        location = tvLocation.editText?.text.toString()
                    )

                    viewModel.flowState.observe(requireActivity(), Observer {
                        if(it.locEmpty) {
                            tvLocation.error = "Required"
                        } else {
                            tvLocation.isErrorEnabled = false
                        }

                        if(it.nameEmpty) {
                            tvName.error = "Required"
                        } else {
                            tvName.isErrorEnabled = false
                        }

                        if(it.phoneNotValid) {
                            tvPhone.error = "Invalid"
                        } else {
                            tvPhone.isErrorEnabled = false
                        }

                        if(it.phoneEmpty) {
                            tvPhone.error = "Required"
                        }

                        if(it.success) {

                            val action = DetailsFragmentDirections.actionDetailsFragmentToDetailsGuardianFragment()
                            requireView().findNavController().navigate(action)
                        }
                    })


            }





        }
    }

}