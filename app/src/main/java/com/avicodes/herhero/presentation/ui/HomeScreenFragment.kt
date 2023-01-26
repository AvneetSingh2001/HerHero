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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.avicodes.herhero.databinding.FragmentHomeScreenBinding
import com.avicodes.herhero.presentation.ui.adapters.GuardiansAdapter
import com.avicodes.herhero.presentation.ui.authScreen.DetailsViewModel
import com.avicodes.herhero.presentation.ui.authScreen.DetailsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomeScreenFragment : Fragment() {

    private var _binding : FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!


    @Inject
    lateinit var guardiansAdapter: GuardiansAdapter

    @Inject
    lateinit var factory: HomeScreenViewModelFactory

    lateinit var viewModel: HomeScreenViewModel

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

        viewModel = ViewModelProvider(requireActivity(), factory)[HomeScreenViewModel::class.java]

        viewModel.getSavedGuardians().observe(requireActivity(), Observer {
            guardiansAdapter.differ.submitList(it)
        })

        initRecyclerView()

        binding.apply {
            copyButton.setOnClickListener {
                val clipboardManager =
                    activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("text", tvCode.text.toString())
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(requireContext(), "Text copied to clipboard", Toast.LENGTH_LONG)
                    .show()
            }

            lifecycleScope.launch {
                tv3.text = viewModel.getCurrentUserDetails()?.name.toString()
            }

            tvCode.text = viewModel.getFirebaseUser()!!.uid

            btnAddGuardian.setOnClickListener {
                val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToAddGuardianFragment()
                requireView().findNavController().navigate(action)
            }
        }
        guardiansAdapter.setOnItemClickListener {
            viewModel.deleteGuardian(it)
        }
    }


    fun initRecyclerView() {
        binding.rvGuardians.apply {
            adapter = guardiansAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}