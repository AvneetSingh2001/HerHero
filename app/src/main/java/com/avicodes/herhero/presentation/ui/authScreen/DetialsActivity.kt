package com.avicodes.herhero.presentation.ui.authScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.avicodes.herhero.R
import com.avicodes.herhero.databinding.ActivityDetialsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetialsActivity : AppCompatActivity() {

    private var _binding : ActivityDetialsBinding? = null
    private val binding get() = _binding!!


    @Inject
    lateinit var factory: DetailsViewModelFactory

    lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetialsBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)

        val navController  = findNavController(R.id.fragmentContainerView)
        navController.setGraph(R.navigation.details_nav_graph)


        viewModel = ViewModelProvider(this, factory)[DetailsViewModel::class.java]
    }
}