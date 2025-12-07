package com.example.academy_tbc.presentation.screen.sign_in

import androidx.fragment.app.viewModels
import com.example.academy_tbc.databinding.FragmentSignInBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollect
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(
    FragmentSignInBinding::inflate
) {
    private val viewModel: GoogleAuthViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        val currentUser = FirebaseAuth.getInstance().currentUser
        binding.root.showSnackBar("${currentUser?.uid}")
    }

    override fun listeners() {
        onSignInClick()
        observe()
        onSignOut()
    }

    fun observe() {
        lifecycleCollect(viewModel.state) { state ->
            if (state.isSignInSuccessful) {
                viewModel.resetState()
            }
        }
    }

    private fun onSignInClick() {
        binding.btnSignIn.setOnClickListener {
            viewModel.signIn()
        }
    }

    private fun onSignOut() {
        binding.btnSignOut.setOnClickListener {
            viewModel.signOut()
        }
    }


}