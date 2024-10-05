package com.example.e_commmercefixed.fragments.main.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.e_commmercefixed.R
import com.example.e_commmercefixed.databinding.FragmentWelcomeBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

@Suppress("KDocUnresolvedReference")
class WelcomeFragment : Fragment(), OnClickListener {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WelcomeViewModel by inject()

    /** ActivityResultLauncher is a component in Android that provides
     *  a way to handle activities that return results (like starting a camera activity or
     *  opening Firebase Authentication UI)
     *  @param contract is the application activity we want to get data from.
     *  @param callback what we will use it in .
     * **/
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        viewModel.onSignInResult(res)
    }

    /**To kick off the FirebaseUI sign in flow,
     * create a sign in intent with your preferred sign-in methods
     **/
    private val providers = arrayListOf(
        AuthUI.IdpConfig.GoogleBuilder().build()
    )

    /** to get data from another application activity we have to define intent like we did in
     * normal intents and pass it to the ActivityResultLauncher
     */
    private val signInIntent = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .build()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btn3.setOnClickListener(this)
        binding.loginBtn.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.signInState.collect { state ->
                if (state) {
                    val action =
                        WelcomeFragmentDirections.actionWelcomeFragmentToBottomNavigationFragment()
                    findNavController().navigate(action)
                } else {
                    Snackbar.make(
                        binding.root,
                        "An error occurred. Please try again.", Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btn1 -> {
                signInLauncher.launch(signInIntent)
            }

            binding.btn3 -> {
                val action = WelcomeFragmentDirections.actionWelcomeFragmentToSignupFragment()
                this.findNavController().navigate(action)
            }

            binding.loginBtn -> {
                val action = WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment()
                this.findNavController().navigate(action)
            }
        }
    }
}