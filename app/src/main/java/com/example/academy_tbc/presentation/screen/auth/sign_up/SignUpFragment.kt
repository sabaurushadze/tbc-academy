package com.example.academy_tbc.presentation.screen.auth.sign_up

import android.util.Log.d
import android.widget.ArrayAdapter
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.FragmentSignUpBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.gone
import com.example.academy_tbc.presentation.extension.lifecycleCollect
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(
    FragmentSignUpBinding::inflate
) {
    private val viewModel: SignUpViewModel by viewModels()
    private var departmentAdapter: ArrayAdapter<String>? = null

    override fun bind() {
        applyTosTextColors()
        setupDepartmentDropdown()
    }

    override fun listeners() {
        observeState()
        observeSideEffects()
        onSendOtpClick()
        setupResendCode()
        onSignInClick()
        signUp()
        validateOtpCode()
    }

    private fun observeSideEffects() = with(binding) {
        lifecycleCollectLatest(viewModel.sideEffect) { effect ->
            when (effect) {
                is SignUpSideEffect.ShowError ->
                    root.showSnackBar(effect.error.getString(requireContext()))

                is SignUpSideEffect.OtpExpired -> {}
                is SignUpSideEffect.ShowConfirmPasswordError -> {
                    etConfirmPassword.error = effect.error.getString(requireContext())
                }
                is SignUpSideEffect.ShowEmailError -> {
                    etEmail.error = effect.error.getString(requireContext())
                }
                is SignUpSideEffect.ShowFirstNameError -> {
                    etFirstName.error = effect.error.getString(requireContext())
                }
                is SignUpSideEffect.ShowLastNameError -> {
                    etLastName.error = effect.error.getString(requireContext())
                }
                is SignUpSideEffect.ShowPasswordError -> {
                    etPassword.error = effect.error.getString(requireContext())
                }

                is SignUpSideEffect.ShowPhoneNumberError -> {
                    etPhoneNumber.error = effect.error.getString(requireContext())
                }

                SignUpSideEffect.OtpCodeValid -> {
                    groupOtp.gone()
                    etPhoneNumber.text?.clear()

                }
                is SignUpSideEffect.ShowOtpCodeError -> {
                    etOtp.error = effect.error.getString(requireContext())
                }

                SignUpSideEffect.SuccessAndNavigateToSignIn -> {
                    root.showSnackBar(getString(R.string.successfully_registered_please_sign_in))
                    findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
                }
            }
        }
    }

    private fun observeState() {
        lifecycleCollect(viewModel.state) { state ->
            binding.tvCodeExpirationTimer.isVisible = state.isOtpVisible
            binding.groupOtp.isVisible = state.isOtpVisible

            val seconds = state.elapsedTime / 1000
            binding.tvCodeExpirationTimer.text =
                getString(R.string.code_expires_in_02d_02d).format(seconds / 60, seconds % 60)

            if (departmentAdapter == null && state.departments != null) {
                departmentAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    state.departments.map { it.name }
                )
                binding.spinnerDepartment.setAdapter(departmentAdapter)
            }
        }
    }

    private fun applyTosTextColors() {
        binding.tvAgreeTos.text = HtmlCompat.fromHtml(
            getString(R.string.i_agree_to_the_terms_of_service_and_privacy_policy),
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )
    }

    private fun signUp() = with(binding) {
        btnCreateAccount.setOnClickListener {

            val firstName = etFirstName.text.toString().trim()
            val lastName = etLastName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()
            val phoneNumber = etPhoneNumber.text.toString().trim()
            if (!checkboxTos.isChecked) {
                root.showSnackBar(getString(R.string.you_need_to_agree_to_tos))
                return@setOnClickListener
            }
            if (spinnerDepartment.text.toString().isBlank()) {
                dropdownDepartment.error = getString(R.string.please_select_a_department)
            }
            val departmentId = viewModel.state.value.selectedDepartment
            d("asdd", "$departmentId")
            if (departmentId == null) {
                dropdownDepartment.error = getString(R.string.please_select_a_department)
                return@setOnClickListener
            }

            viewModel.onEvent(SignUpEvent.SignUp(
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                department = departmentId,
                phoneNumber = phoneNumber
            ))
        }


    }

    private fun setupDepartmentDropdown() {
        binding.spinnerDepartment.setOnItemClickListener { _, _, position, _ ->
            val department = viewModel.state.value.departments?.get(position)
            department?.let {
                viewModel.onEvent(SignUpEvent.SaveDepartment(it.id))
            }
        }
    }

    private fun validateOtpCode() = with(binding) {
        btnValidate.setOnClickListener {
            val otpCode = etOtp.text.toString().trim()

            viewModel.onEvent(SignUpEvent.ValidateCode(otpCode))
        }
    }

    private fun setupResendCode() = with(binding) {
        tvResendCode.setOnClickListener {
            val phoneNumber = etPhoneNumber.text.toString().trim()

            viewModel.onEvent(SignUpEvent.ResendOtp(phoneNumber))
        }
    }

    private fun onSendOtpClick() = with(binding) {
        btnSendOtp.setOnClickListener {
            val phoneNumber = etPhoneNumber.text.toString().trim()

//            groupOtp.show()
            viewModel.onEvent(SignUpEvent.SendOtp(phoneNumber))
        }
    }

    private fun onSignInClick() {
        binding.tvBtnSignIn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}