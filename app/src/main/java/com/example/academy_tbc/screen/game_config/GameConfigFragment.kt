package com.example.academy_tbc.screen.game_config

import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.R
import com.example.academy_tbc.common.BaseFragment
import com.example.academy_tbc.databinding.FragmentGameConfigBinding
import com.example.academy_tbc.extension.showSnackBar

class GameConfigFragment : BaseFragment<FragmentGameConfigBinding>(
    FragmentGameConfigBinding::inflate
) {
    override fun bind() {
        val dimensionsLabels = BoardSize.entries.map { it.label }
        val adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line, dimensionsLabels
        )
        binding.autoCompleteBoardSize.setAdapter(adapter)
    }

    override fun listeners() {
        binding.btnStart.setOnClickListener {
            val selectedDimension = binding.autoCompleteBoardSize.text.toString()
            val boardSize = BoardSize.fromLabel(selectedDimension)?.size

            if (boardSize != null) {
                binding.autoCompleteBoardSize.setText("", false)
                findNavController().navigate(
                    GameConfigFragmentDirections.actionGameConfigFragmentToGameFragment(boardSize)
                )
            } else {
                binding.root.showSnackBar(getString(R.string.please_select_board_size))
                return@setOnClickListener
            }
        }
    }
}

