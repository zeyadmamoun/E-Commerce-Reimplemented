package com.example.e_commmercefixed.fragments.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.e_commmercefixed.R
import com.example.e_commmercefixed.databinding.FragmentSplashBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment() {

    private val viewModel: SplashViewModel by viewModel()
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_splash,container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().installSplashScreen().apply {
            setKeepOnScreenCondition{
                viewModel.isLoading.value
            }
        }

        lifecycleScope.launch {
            viewModel.navigationEvent.collect{ event ->
                when(event){
                    NavigationEvent.NavigateToHome -> {
                        val action = SplashFragmentDirections.actionSplashFragmentToBottomNavigationFragment()
                        findNavController().navigate(action)
                    }
                    else -> {
                        val action = SplashFragmentDirections.actionSplashFragmentToWelcomeFragment()
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }
}