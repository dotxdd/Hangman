package com.example.hangmangame

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import org.json.JSONArray
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LostGameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LostGameFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "You Lost"
        val view = inflater.inflate(R.layout.fragment_lost_game, container, false)
        val word = requireArguments().getString("word")
        val randomWordTextView = view.findViewById<TextView>(R.id.randomWordTextView)
        randomWordTextView.text = word
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
            view: View->view.findNavController().navigate(R.id.action_lostGameFragment_to_SecondFragment)
        }

        val currentDate = getCurrentDate()
        val gameHistory = "$currentDate Lost"
        if (word != null) {
            saveGameHistory(gameHistory, word)
        }



        return view
    }


    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }

    private fun saveGameHistory(gameHistory: String, word: String) {
        val sharedPreferences = requireContext().getSharedPreferences("HangmanPrefs", Context.MODE_PRIVATE)
        val historyList = getGameHistoryList(sharedPreferences)

        if (historyList.size >= 3) {
            historyList.removeAt(0) // Usuń najstarszą historię, jeśli już jest 3
        }

        historyList.add("$gameHistory $word") // Dodaj nową historię do listy

        val editor = sharedPreferences.edit()
        editor.putString("gameHistory", JSONArray(historyList).toString())
        editor.apply()
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
        @JvmStatic fun newInstance(word: String) = LostGameFragment().apply {
            arguments = Bundle().apply {
                putString("word", word)
            }
        }
    }
}