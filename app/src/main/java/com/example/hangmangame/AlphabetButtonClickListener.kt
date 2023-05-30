package com.example.hangmangame

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController

class AlphabetButtonClickListener(
    private val randomWord: String,
    private val generatedWordTextView: TextView,
    private val hangmanImageView: ImageView
) : View.OnClickListener {

    private val guessedLetters = mutableListOf<Char>()
    private var wrongGuesses = 0

    override fun onClick(view: View) {
        view.isEnabled = false
        view.setBackgroundColor(Color.GRAY)
        val letter = view.tag.toString().toLowerCase()
        val isCorrectGuess = randomWord.contains(letter, ignoreCase = true)
        if (isCorrectGuess) {
            guessedLetters.add(letter[0])
            updateGeneratedWordTextView()
            Log.i("SecondFragment", "You are right!")
        } else {
            wrongGuesses++
            updateHangmanImage()
            if (wrongGuesses === 9) {
                view.findNavController().navigate(SecondFragmentDirections.actionSecondFragmentToLostGameFragment(randomWord))
            }
            Log.i("SecondFragment", "WRONG")
        }
    }

    private fun updateGeneratedWordTextView() {
        val updatedWord = StringBuilder()
        for (char in randomWord.toLowerCase()) {
            if (guessedLetters.contains(char)) {
                updatedWord.append(char)
            } else {
                updatedWord.append("_ ")
            }
        }
        generatedWordTextView.text = updatedWord.toString().trim()
    }

    private fun updateHangmanImage() {
        val drawableResId = when (wrongGuesses) {
            1 -> R.drawable.game2
            2 -> R.drawable.game3
            3 -> R.drawable.game4
            4 -> R.drawable.game5
            5 -> R.drawable.game6
            6 -> R.drawable.game7
            7 -> R.drawable.game8
            8 -> R.drawable.game9
            else -> R.drawable.game1
        }
        hangmanImageView.setImageResource(drawableResId)
    }
}
