package com.example.e_commmercefixed.fragments.main.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.e_commmercefixed.R
import com.example.e_commmercefixed.databinding.FragmentSignupBinding
import com.example.e_commmercefixed.utils.auth.ConfirmPasswordValidator
import com.example.e_commmercefixed.utils.auth.PasswordValidator
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignupViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val passwordLayout = binding.passwordTextBox.editText
        passwordLayout?.addTextChangedListener(PasswordValidator(binding.passwordTextBox))
        val passwordConfirmation = binding.confirmPasswordTextBox.editText
        passwordConfirmation?.addTextChangedListener(
            ConfirmPasswordValidator(binding.passwordTextBox, binding.confirmPasswordTextBox)
        )

        lifecycleScope.launch {
            viewModel.uiState.collect{
                binding.emailTextBox.error = it.signupError
                binding.loginBtn.isEnabled = !it.isLoading
            }
        }

        lifecycleScope.launch {
            viewModel.navigationEvent.collect{
                val action = SignupFragmentDirections.actionSignupFragmentToBottomNavigationFragment()
                findNavController().navigate(action)
            }
        }

        binding.loginBtn.setOnClickListener {
            val firstname = binding.firstNameEditText.text.toString()
            val lastname = binding.lastNameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (firstname.isEmpty() || lastname.isEmpty()) {
                binding.firstNameTextBox.error = "first name or last name is empty"
            } else if (email.isEmpty()) {
                binding.emailTextBox.error = "need to provide email"
            } else if (password.isEmpty()) {
                binding.passwordTextBox.error = "need to provide write your password"
            } else{
                viewModel.signup(firstname,lastname,email,password)
            }
        }
    }
}