package com.example.hangmangame

import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView

import org.json.JSONArray
import org.json.JSONObject

<<<<<<< HEAD

import androidx.appcompat.app.AppCompatActivity

=======
import androidx.appcompat.app.AppCompatActivity


>>>>>>> f98d2009516ef4fa5eac88cec550d3be46a06890

class UserScoreFragment : Fragment() {

    private lateinit var userImageView: ImageView
    private lateinit var usernameTextView: TextView
    private lateinit var userScoreTextView: TextView

    private var userScore = 0

    private val defaultImageResId = R.drawable.user_image
    private val defaultUsername = "Default Username"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "Score"
        val view = inflater.inflate(R.layout.fragment_user_score, container, false)
        userImageView = view.findViewById(R.id.userImage)
        usernameTextView = view.findViewById(R.id.textViewUserName)
        userScoreTextView = view.findViewById(R.id.userScoreTextView)
        updateScoreText()
        loadUserProfile()
        return view
    }




    private fun updateScoreText() {
        val score = getUserScoreFromPreferences() // Pobierz aktualną wartość userScore z preferencji
        val scoreText = resources.getString(R.string.user_score, score)
        userScoreTextView.text = scoreText
    }

    private fun getUserScoreFromPreferences(): Int {
        val sharedPreferences = requireContext().getSharedPreferences("HangmanPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("userScore", 0)
    }


    private fun loadUserProfile() {
        val sharedPreferences = requireActivity().getSharedPreferences("HangmanPrefs", 0)
        val imagePath = sharedPreferences.getString("imagePath", null)
        val username = sharedPreferences.getString("username", defaultUsername)

        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            userImageView.setImageBitmap(bitmap)
        } else {
            userImageView.setImageResource(defaultImageResId)
        }

        usernameTextView.text = username
    }


    companion object {
        private const val ARG_IMAGE_PATH = "image_path"

        fun newInstance(imagePath: String): UserScoreFragment {
            val fragment = UserScoreFragment()
            val bundle = Bundle()
            bundle.putString(ARG_IMAGE_PATH, imagePath)
            fragment.arguments = bundle
            return fragment
        }
    }
}

