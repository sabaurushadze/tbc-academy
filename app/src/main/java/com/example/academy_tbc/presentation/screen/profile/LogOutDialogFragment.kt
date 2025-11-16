package com.example.academy_tbc.presentation.screen.profile

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.academy_tbc.R

class LogOutDialogFragment(
    private val onLogoutConfirmed: () -> Unit
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext()).setMessage(getString(R.string.log_out_really))
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                dismiss()
                onLogoutConfirmed()
            }.setNegativeButton(getString(R.string.cancel)) { _, _ -> }.create()

    companion object {
        const val TAG = "LogOutDialog"
    }
}