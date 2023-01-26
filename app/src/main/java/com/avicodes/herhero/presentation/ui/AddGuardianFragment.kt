package com.avicodes.herhero.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.avicodes.herhero.R
import com.avicodes.herhero.data.models.Guardians
import com.avicodes.herhero.data.utils.Response
import com.avicodes.herhero.databinding.FragmentAddGuardianBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class AddGuardianFragment : Fragment() {

    private var _binding : FragmentAddGuardianBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: AddGuardianViewModelFactory
    lateinit var viewModel: AddGuardianViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddGuardianBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), factory)[AddGuardianViewModel::class.java]

        binding.apply {
            buttonAddGuardian.setOnClickListener {
                val gid = tvGid.editText?.text.toString()
                viewModel.checkUser(gid)
                viewModel.checkUserData.observe(viewLifecycleOwner, Observer {
                    when(it) {
                        is Response.Success -> {
                            when (it.message) {
                                "Exists" -> {

                                    lifecycleScope.launch(Dispatchers.IO) {
                                        Log.e("MYTAG","Exists")
                                        viewModel.updateLocalListGuardian(gid)
                                    }

                                    viewModel.getSavedGuardians().observe(viewLifecycleOwner, Observer {list ->
                                        viewModel.updateUserGuardian(list)
                                    })

                                    navigateBack()

                                }
                                "Not Exists" -> {
                                    Log.e("MYTAG", "Not Exists")
                                    tvGid.error = "User Doesn't Exist"
                                }
                                else -> {
                                    Log.e("MYTAG", "Not Exists")
                                    tvGid.error = "Required"
                                }
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

    }

    fun navigateBack() {
        requireView().findNavController().popBackStack()
    }
}