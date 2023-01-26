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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.avicodes.herhero.data.models.Guardians
import com.avicodes.herhero.data.utils.Response
import com.avicodes.herhero.databinding.FragmentDetailsGuardianBinding
import com.avicodes.herhero.presentation.ui.adapters.GuardiansAdapter
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class DetailsGuardianFragment : Fragment() {

    private var _binding : FragmentDetailsGuardianBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailsViewModel

    private var guardianList: List<Guardians>? = null

    @Inject
    lateinit var guardiansAdapter: GuardiansAdapter

    @Inject
    lateinit var auth: FirebaseAuth

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

        viewModel.getSavedGuardians().observe(requireActivity(), Observer {
            guardianList = it
            guardiansAdapter.differ.submitList(guardianList)

        })

        initRecyclerView()

        binding.apply {

            buttonAddGuardian.setOnClickListener {
                val gid = tvGid.editText?.text.toString()
                if(gid == "") {
                    tvGid.error = "Required"
                } else {
                    viewModel.checkUser(gid)
                    viewModel.checkUserData.observe(requireActivity(), Observer {
                            when (it) {
                                is Response.Success -> {
                                    if(it.message == "Exists") {
                                        lifecycleScope.launch(Dispatchers.IO) {
                                            Log.e("MYTAG","Exists")
                                            viewModel.updateLocalListGuardian(gid)
                                        }

                                    } else {
                                            Log.e("MYTAG","Not Exists")
                                            tvGid.error = "User Doesn't Exist"
                                    }

                                }

                                else -> {
                                    Log.e("MYTAG","else")
                                    tvGid.isErrorEnabled = false
                                }
                            }
                        })
                }
            }

            btnContinue.setOnClickListener {
                if(guardianList != null)
                    viewModel.updateUserGuardian(guardianList!!)

                val action = DetailsGuardianFragmentDirections.actionDetailsGuardianFragmentToHomeActivity()
                requireView().findNavController().navigate(action)

            }

        }


        guardiansAdapter.setOnItemClickListener {
            viewModel.deleteGuardian(it)
        }

    }
    private fun initRecyclerView() {
        binding.rvGuardians.apply {
            adapter = guardiansAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }





}