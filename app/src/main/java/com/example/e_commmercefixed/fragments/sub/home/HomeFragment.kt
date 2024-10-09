package com.example.e_commmercefixed.fragments.sub.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.e_commmercefixed.R
import com.example.e_commmercefixed.databinding.FragmentHomeBinding
import com.example.e_commmercefixed.ui.CategoriesList
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCategories()

        binding.profileBtn.setOnClickListener{
            this.context?.let {
                AuthUI.getInstance().signOut(it)
                    .addOnCompleteListener {}
            }
            viewModel.logout()
        }

        lifecycleScope.launch {
            viewModel.categories.collect{
                binding.composeView.setContent {
                    CategoriesList(categories = it)
                }
            }
        }
    }

}