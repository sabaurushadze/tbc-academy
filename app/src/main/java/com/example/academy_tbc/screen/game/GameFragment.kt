package com.example.academy_tbc.screen.game

import android.graphics.Color
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageButton
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.academy_tbc.R
import com.example.academy_tbc.common.BaseFragment
import com.example.academy_tbc.databinding.FragmentGameBinding

class GameFragment : BaseFragment<FragmentGameBinding>(
    FragmentGameBinding::inflate
) {
    private lateinit var board: Array<IntArray>
    private var currentPlayer: Player = Player.X
    private var gameOver = false
    private val args: GameFragmentArgs by navArgs<GameFragmentArgs>()

    override fun listeners() {
        setUpLayout(args.boardSize)
    }

    private fun setUpLayout(boardSize: Int) = with(binding) {
        board = Array(boardSize) { IntArray(boardSize) }

        for (row in 0 until boardSize) {
            for (column in 0 until boardSize) {
                val button = AppCompatImageButton(requireContext()).apply {
                    setBackgroundColor(Color.WHITE)
                    scaleType = ImageView.ScaleType.CENTER_INSIDE
                }

                val params = GridLayout.LayoutParams().apply {
                    width = 0
                    height = 0
                    rowSpec = GridLayout.spec(row, 1f)
                    columnSpec = GridLayout.spec(column, 1f)
                    setMargins(8, 8, 8, 8)
                }

                button.setOnClickListener {
                    if (!gameOver) {
                        onTileClick(
                            btn = button,
                            image = currentPlayer.drawableRes,
                            row = row,
                            column = column,
                            player = currentPlayer
                        )
                        currentPlayer = currentPlayer.other()

                    }
                }

                gridBoard.addView(button, params)
            }
        }
    }

    private fun onTileClick(
        btn: ImageButton, image: Int, row: Int, column: Int, player: Player
    ) {
        val playerToNumber = if (player == Player.X) 1 else 2
        board[row][column] = playerToNumber

        btn.setImageResource(image)
        btn.isEnabled = false

        if (checkWinner(playerToNumber)) {
            gameOver = true
            showGameOverDialog(getString(R.string.cross_or_nought_won, player.symbol))

        } else if (isTie()) {
            gameOver = true
            showGameOverDialog(getString(R.string.it_is_a_tie))
        }
    }

    private fun showGameOverDialog(message: String) {
        val dialog = AlertDialog.Builder(requireContext()).setTitle(getString(R.string.game_over))
            .setMessage(message).setCancelable(false)
            .setNeutralButton(getString(R.string.change_board_size)) { _, _ ->
                findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameConfigFragment())
            }.setPositiveButton(getString(R.string.play_again)) { _, _ ->
                resetGame()
            }.create()

        dialog.show()
    }

    private fun resetGame() {
        currentPlayer = Player.X
        gameOver = false

        for (i in 0 until binding.gridBoard.childCount) {
            val button = binding.gridBoard.getChildAt(i) as ImageButton
            button.setImageDrawable(null)
            button.isEnabled = true
        }

        for (row in board.indices) {
            for (col in board[row].indices) {
                board[row][col] = 0
            }
        }
    }

    private fun checkWinner(player: Int): Boolean {
        return hasWinningRow(player) || hasWinningColumn(player) || hasWinningDiagonal(player) || hasWinningAntiDiagonal(
            player
        )
    }

    private fun isTie(): Boolean {
        return board.all { row -> row.all { it != 0 } }
    }

    private fun hasWinningRow(player: Int): Boolean {
        return board.any { row -> row.all { it == player } }
    }

    private fun hasWinningColumn(player: Int): Boolean {
        return board.indices.any { col -> board.all { row -> row[col] == player } }
    }

    private fun hasWinningDiagonal(player: Int): Boolean {
        return board.indices.all { i -> board[i][i] == player }
    }

    private fun hasWinningAntiDiagonal(player: Int): Boolean {
        return board.indices.all { i -> board[i][board.size - 1 - i] == player }
    }
}