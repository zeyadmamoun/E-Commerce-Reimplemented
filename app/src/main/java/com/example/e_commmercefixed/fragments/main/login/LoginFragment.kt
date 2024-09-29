package com.example.e_commmercefixed.fragments.main.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.e_commmercefixed.R
import com.example.e_commmercefixed.databinding.FragmentLoginBinding
import com.example.e_commmercefixed.utils.auth.PasswordValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editText = binding.passwordTextBox.editText
        editText?.addTextChangedListener(PasswordValidator(binding.passwordTextBox))

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect {
                binding.emailTextBox.error = it.emailError
                binding.passwordTextBox.error = it.passwordError
                binding.loginBtn.isEnabled = !it.isLoading
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.navigationEvent.collect {
                val action = LoginFragmentDirections.actionLoginFragmentToBottomNavigationFragment()
                findNavController().navigate(action)
            }
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.emailOutlinedTextField.text ?: ""
            val password = binding.passwordOutlinedTextField.text ?: ""
            viewModel.login(email.toString(), password.toString())
        }
    }
}
