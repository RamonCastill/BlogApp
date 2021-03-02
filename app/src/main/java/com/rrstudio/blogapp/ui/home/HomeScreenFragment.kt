package com.rrstudio.blogapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rrstudio.blogapp.R
import com.rrstudio.blogapp.core.Resource
import com.rrstudio.blogapp.data.remote.home.HomeScreenDataSource
import com.rrstudio.blogapp.databinding.FragmentHomeScreenBinding
import com.rrstudio.blogapp.domain.home.HomeScreenRepoImpl
import com.rrstudio.blogapp.presentation.HomeScreenViewModel
import com.rrstudio.blogapp.presentation.HomeScreenViewModelFactory
import com.rrstudio.blogapp.ui.home.adapter.HomeScreenAdapter

class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel by viewModels<HomeScreenViewModel>{HomeScreenViewModelFactory(
        HomeScreenRepoImpl(
        HomeScreenDataSource()
    )
    )}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)

        viewModel.fetchLatestPosts().observe(viewLifecycleOwner, Observer {result ->
            when(result){
                is Resource.Loading ->{
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success ->{
                    binding.progressBar.visibility = View.GONE
                    binding.rvHome.adapter = HomeScreenAdapter(result.data)
                }
                is Resource.Failure ->{
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(),"Ocurrio un error: ${result.exeption}", Toast.LENGTH_SHORT).show()
                }
            }
        })


    }

}