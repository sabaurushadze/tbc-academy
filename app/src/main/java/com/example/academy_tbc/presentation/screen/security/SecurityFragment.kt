package com.example.academy_tbc.presentation.screen.security

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.FragmentSecurityBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollect
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executor

@AndroidEntryPoint
class SecurityFragment : BaseFragment<FragmentSecurityBinding>(
    FragmentSecurityBinding::inflate
) {
    private val viewModel: SecurityViewModel by viewModels()

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var executor: Executor

    override fun bind() {
        setUpBiometric()
    }

    override fun listeners() = with(binding) {
        observeState()
        observeSideEffects()
        onPinCodeButtonClick()
        onTouchIdClick()
        onBackspaceClick()
        onForgotPasswordClick()
    }

    private fun setUpBiometric() {
        executor = ContextCompat.getMainExecutor(requireContext())

        biometricPrompt = BiometricPrompt(
            this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    binding.root.showSnackBar(getString(R.string.biometric_success))
                }
            })
    }

    private fun observeSideEffects() {
        lifecycleCollectLatest(viewModel.effect) { effect ->
            when (effect) {
                SecuritySideEffect.Success -> binding.root.showSnackBar(getString(R.string.success))
                SecuritySideEffect.WrongCode -> {
                    binding.root.showSnackBar(getString(R.string.incorrect_passcode))
                    updatePinUI("")
                }

                SecuritySideEffect.LaunchBiometric -> {
                    if (checkBiometricAvailability()) {
                        biometricPrompt.authenticate(promptInfo)
                    }
                }
            }
        }
    }

    private fun observeState() {
        lifecycleCollect(viewModel.state) { state ->
            updatePinUI(state.enteredCode)
        }
    }

    private fun onPinCodeButtonClick() = with(binding) {
        val buttons = listOf(btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9)

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                viewModel.onEvent(SecurityEvent.NumberPressed(index.toString()))
            }
        }
    }

    private fun onTouchIdClick() {
        binding.btnTouchId.setOnClickListener {
            viewModel.onEvent(SecurityEvent.BiometricClick)
        }
    }

    private fun onBackspaceClick() {
        binding.btnBackspace.setOnClickListener {
            viewModel.onEvent(SecurityEvent.Backspace)
        }
    }

    private fun onForgotPasswordClick() {
        binding.tvForgotPassword.setOnClickListener {
            binding.root.showSnackBar(getString(R.string.gaixseneb_araushavs))
        }
    }

    private fun updatePinUI(code: String) = with(binding) {
        val indicators = listOf(ivCode1, ivCode2, ivCode3, ivCode4)

        indicators.forEachIndexed { index, image ->
            if (index < code.length) {
                image.setImageResource(R.drawable.ic_filled_passcode)
            } else {
                image.setImageResource(R.drawable.ic_empty_passcode)
            }
        }
    }

    private fun checkBiometricAvailability(): Boolean {
        val biometricManager = BiometricManager.from(requireContext())

        val result = biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
        )

        when (result) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                buildPromptInfo()
                return true
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val intent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(
                            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            BiometricManager.Authenticators.BIOMETRIC_STRONG
                        )
                    }
                    startActivity(intent)
                } else {
                    binding.root.showSnackBar(getString(R.string.no_biometric_enrolled_please_set_up_fingerprint))
                }
                return false
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                binding.root.showSnackBar(getString(R.string.device_has_no_biometric_sensor))
                return false
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                binding.root.showSnackBar(getString(R.string.biometric_hardware_unavailable))
                return false
            }

            else -> {
                binding.root.showSnackBar(getString(R.string.biometric_authentication_not_available))
                return false
            }
        }
    }

    private fun buildPromptInfo() {
        promptInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            BiometricPrompt.PromptInfo.Builder().setTitle(getString(R.string.shedi_hakerulad))
                .setSubtitle(getString(R.string.use_fingerprint_or_device_pin))
                .setAllowedAuthenticators(
                    BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                ).build()
        } else {
            BiometricPrompt.PromptInfo.Builder().setTitle(getString(R.string.shedi_hakerulad))
                .setSubtitle(getString(R.string.use_fingerprint_or_device_pin))
                .setDeviceCredentialAllowed(true).build()
        }
    }
}