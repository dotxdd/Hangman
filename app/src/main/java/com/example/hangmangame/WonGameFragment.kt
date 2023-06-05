package com.example.hangmangame

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LostGameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class WonGameFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var userScore = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_won_game, container, false)


        val imageView = view.findViewById<ImageView>(R.id.iv_image)
        imageView.pivotX = 0.5f
        imageView.pivotY = 0.4f

        // Post the animation action to run after the view is created
        imageView.post {
            imageView.animate().apply {
                duration = 1000
                rotationYBy(360f)
                withEndAction {
                    imageView.animate().apply {
                        duration = 700
                        rotationXBy(360f)

                    }.start()
                }
            }.start()
        }
        val hangAgainButton = view.findViewById<Button>(R.id.hangAgainButton)
        hangAgainButton.setOnClickListener {
                view: View->view.findNavController().navigate(R.id.action_wonGameFragment_to_SecondFragment)
        }

        userScore = getUserScoreFromPreferences() // Pobierz aktualną wartość userScore z preferencji
        userScore++ // Zwiększ wartość userScore o 1
        saveUserScoreToPreferences(userScore)

        return view
    }

    private fun getUserScoreFromPreferences(): Int {
        val sharedPreferences = requireContext().getSharedPreferences("HangmanPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("userScore", 0)
    }

    private fun saveUserScoreToPreferences(score: Int) {
        val sharedPreferences = requireContext().getSharedPreferences("HangmanPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("userScore", score)
        editor.apply()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LostGameFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
            LostGameFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}