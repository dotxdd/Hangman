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
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException


class UserScoreFragment : Fragment() {

    private lateinit var userImageView: ImageView
    private lateinit var usernameTextView: TextView
    private lateinit var userScoreTextView: TextView
    private lateinit var gameHistoryTable: TableLayout

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
        gameHistoryTable = view.findViewById(R.id.gameHistoryTable)
        populateGameHistoryTable()
        updateScoreText()
        loadUserProfile()
        return view
    }

    private fun getGameHistoryList(sharedPreferences: SharedPreferences): MutableList<String> {
        val historyJson = sharedPreferences.getString("gameHistory", null)
        val historyList = mutableListOf<String>()

        if (historyJson != null) {
            try {
                val jsonArray = JSONArray(historyJson)
                for (i in 0 until jsonArray.length()) {
                    val history = jsonArray.getString(i)
                    historyList.add(history)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        return historyList
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
    private fun populateGameHistoryTable() {
        val sharedPreferences = requireContext().getSharedPreferences("HangmanPrefs", Context.MODE_PRIVATE)
        val historyList = getGameHistoryList(sharedPreferences)

        for (i in historyList.indices) {
            val gameHistory = historyList[i]
            val historyRow = TableRow(requireContext())

            val historyDateView = TextView(requireContext())
            historyDateView.text = gameHistory.substringBefore(" ")
            historyDateView.setPadding(10, 0, 10, 0)

            val historyResultView = TextView(requireContext())
            historyResultView.text = gameHistory.substringAfter(" ").substringBefore(" ")
            historyResultView.setPadding(10, 0, 10, 0)

            val historyWordView = TextView(requireContext())
            historyWordView.text = gameHistory.substringAfterLast(" ")
            historyWordView.setPadding(10, 0, 10, 0)

            historyRow.addView(historyDateView)
            historyRow.addView(historyWordView)
            historyRow.addView(historyResultView)

            gameHistoryTable.addView(historyRow)
        }
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

