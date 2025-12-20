package com.example.academy_tbc.presentation.screen.sign_up

import android.os.CountDownTimer
import android.widget.ArrayAdapter
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.FragmentSignUpBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.gone
import com.example.academy_tbc.presentation.extension.lifecycleCollect
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.show
import com.example.academy_tbc.presentation.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(
    FragmentSignUpBinding::inflate
) {
//    private var otpTimer: CountDownTimer? = null
    private val viewModel: SignUpViewModel by viewModels()

//    override fun onDestroyView() {
//        super.onDestroyView()
//        otpTimer?.cancel()
//        otpTimer = null
//    }

    override fun bind() {
        applyTosTextColors()
        setupDepartmentDropdown()
    }

    override fun listeners() {
        observeState()
        observeSideEffects()
        onSendOtpClick()
        setupResendCode()
    }

    private fun observeSideEffects() = with(binding) {
        lifecycleCollectLatest(viewModel.effect) { effect ->
            when (effect) {
                is SignUpSideEffect.ShowError ->
                    root.showSnackBar(effect.error.getString(requireContext()))

                is SignUpSideEffect.OtpExpired -> {}
            }
        }
    }

    private fun observeState() {
        lifecycleCollect(viewModel.state) { state ->
            if (state.elapsedTime > 0) {
                val seconds = state.elapsedTime / 1000
                binding.tvCodeExpirationTimer.text =
                    "Code expires in %02d:%02d".format(seconds / 60, seconds % 60)
//                binding.tvCodeExpirationTimer.isVisible = true
            }
//            else {
//                binding.tvCodeExpirationTimer.isVisible = false
//            }
        }
    }

    private fun applyTosTextColors() {
        binding.tvAgreeTos.text = HtmlCompat.fromHtml(
            getString(R.string.i_agree_to_the_terms_of_service_and_privacy_policy),
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )
    }

    //    MOCK_DATA_DROPDOWN
    private fun setupDepartmentDropdown() {
        val departments = listOf(
            "Marketing",
            "Finance",
            "IT",
            "HR",
            "Operations"
        )

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            departments
        )

        binding.spinnerDepartment.setAdapter(adapter)
    }

    private fun setupResendCode() = with(binding) {
        tvResendCode.setOnClickListener {
//            if (!tvCodeExpirationTimer.isVisible) {
//              Start timer and call api to check again
                viewModel.onEvent(SignUpEvent.ResendOtpClicked)
//                startOtpTimer()
//            }
        }
    }

    private fun onSendOtpClick() = with(binding) {
        btnSendOtp.setOnClickListener {
//            if (!groupOtp.isVisible) {
//              Start timer and call api to check validation
                groupOtp.show()
                viewModel.onEvent(SignUpEvent.SendOtpClicked)
//                startOtpTimer()
//            }
        }
    }
}