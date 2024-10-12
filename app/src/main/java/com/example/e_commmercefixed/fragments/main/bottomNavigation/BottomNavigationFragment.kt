package com.example.e_commmercefixed.fragments.main.bottomNavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.e_commmercefixed.R
import com.example.e_commmercefixed.databinding.FragmentBottomNavigationBinding
import org.koin.android.ext.android.inject

class BottomNavigationFragment : Fragment() {

    private var _binding: FragmentBottomNavigationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BottomNavigationViewModel by inject()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Here to stop back button
        this.activity?.let {
            it.onBackPressedDispatcher.addCallback(viewLifecycleOwner){}
        }
        // Inflate the layout for this fragment
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_navigation, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigationHostFragment =
            this.childFragmentManager.findFragmentById(R.id.nav_host_fragment_second) as NavHostFragment
        navController = navigationHostFragment.navController
        binding.bottomNav.itemActiveIndicatorColor = ContextCompat.getColorStateList(this.requireContext(),R.color.md_theme_primary)
        binding.bottomNav.setupWithNavController(navController)
    }
}