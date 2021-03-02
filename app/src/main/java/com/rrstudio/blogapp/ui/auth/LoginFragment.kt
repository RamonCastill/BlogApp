package com.rrstudio.blogapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rrstudio.blogapp.R
import com.rrstudio.blogapp.core.Resource
import com.rrstudio.blogapp.data.remote.auth.LoginDataSource
import com.rrstudio.blogapp.databinding.FragmentLoginBinding
import com.rrstudio.blogapp.domain.auth.LoginRepoImpl
import com.rrstudio.blogapp.presentation.auth.LoginScreenViewModel
import com.rrstudio.blogapp.presentation.auth.LoginScreenViewModelFactory

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val viewModel by viewModels<LoginScreenViewModel> { LoginScreenViewModelFactory(LoginRepoImpl(
        LoginDataSource()
    )) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        isUserLoggedIn()
        doLogIn()
    }

    private fun isUserLoggedIn(){
        firebaseAuth.currentUser?.let {             //si esta logeatdo entonces entra al let
            findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
        }
    }

    private fun doLogIn(){
        binding.btnSignin.setOnClickListener{
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            validateCredentials(email, password)
            signIn(email, password)
        }
    }

    private fun validateCredentials(email: String, password: String){
        if(email.isEmpty()){
            binding.editTextEmail.error = "E-mail is empty"
            return //la accion para aca si la condicion de cumple
        }
        if(password.isEmpty()){
            binding.editTextPassword.error = "Password is empty"
            return //la accion para aca si la condicion de cumple
        }
    }

    private fun signIn(email: String, password: String){
        viewModel.signIn(email, password).observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSignin.isEnabled = false
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
                    Toast.makeText(requireContext(), "Welcome ${result.data?.email}",Toast.LENGTH_SHORT).show()
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignin.isEnabled = true
                    Toast.makeText(requireContext(), "Error: ${result.exeption}",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}