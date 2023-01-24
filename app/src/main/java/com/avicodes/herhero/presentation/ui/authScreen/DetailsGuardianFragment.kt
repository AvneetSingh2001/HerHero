package com.avicodes.herhero.presentation.ui.authScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.avicodes.herhero.data.models.Guardians
import com.avicodes.herhero.data.utils.Response
import com.avicodes.herhero.databinding.FragmentDetailsGuardianBinding
import kotlinx.coroutines.launch


class DetailsGuardianFragment : Fragment() {

    private var _binding : FragmentDetailsGuardianBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailsViewModel

    private var guardianList: List<Guardians>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsGuardianBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as DetialsActivity).viewModel
        initRecyelerView()

        viewModel.getSavedGuardians().observe(viewLifecycleOwner) {
            guardianList = it
        }

        binding.apply {

            tvGid.setOnClickListener {
                tvGid.isErrorEnabled = false
            }

            buttonAddGuardian.setOnClickListener {
                val gid = tvGid.editText?.text.toString()
                if(gid == "") {
                    tvGid.error = "Required"
                } else {
                    viewModel.checkUser(gid)
                    viewModel.checkUserData.observe(requireActivity(), Observer {
                            when (it) {
                                Response.Success("Not Exists") -> {
                                    tvGid.error = "User Doesn't Exist"
                                }

                                Response.Success("Exists") -> {
                                    lifecycleScope.launch {
                                        viewModel.updateLocalListGuardian(gid)
                                    }
                                }

                                else -> {}
                            }
                        })
                }
            }

            btnContinue.setOnClickListener {
                if(guardianList != null)
                    viewModel.updateUserGuardian(guardianList!!)
            }





        }
    }
    private fun initRecyelerView() {

    }




}