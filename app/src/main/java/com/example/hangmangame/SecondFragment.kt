package com.example.hangmangame

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.navigation.Navigation.findNavController
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.hangmangame.databinding.FragmentSecondBinding
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */


class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private var randomWord: String? = null
    private lateinit var alphabetButtonClickListener: AlphabetButtonClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "Hanging Time"
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        binding.buttonNewGame.setOnClickListener {
            resetGame()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i("SecondFragment", "t1")

        // Pobieranie losowego słowa z API

        Log.i("SecondFragment", "t1")

        // Pobieranie losowego słowa z API


        // Fetch random word from API
        fetchRandomWord()
        Thread.sleep(1000)


        binding.buttonDefinition.setOnClickListener {
            randomWord?.let { word ->
                val apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/$word"
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url(apiUrl)
                    .build()
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val responseData = response.body?.string()
                        val definition = extractDefinition(responseData)
                        activity?.runOnUiThread {
                            Toast.makeText(context, definition, Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }
        }

        alphabetButtonClickListener = AlphabetButtonClickListener(
            randomWord ?: "",
            binding.generatedWord,
            binding.imgView
        )

        val alphabetButtons = listOf(
            binding.a, binding.b, binding.c, binding.d, binding.e, binding.f, binding.g, binding.h,
            binding.i, binding.j, binding.k, binding.l, binding.m, binding.n, binding.o, binding.p,
            binding.q, binding.r, binding.s, binding.t, binding.u, binding.v, binding.w, binding.x,
            binding.y, binding.z
        )
        alphabetButtons.forEach { button ->
            button.setOnClickListener(alphabetButtonClickListener)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchRandomWord() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://random-word-api.herokuapp.com/word?number=1")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                randomWord = extractRandomWord(responseData)
                randomWord?.let {
                    val wordLength = it.length
                    val underscoreText = "_ ".repeat(wordLength)
                    activity?.runOnUiThread {
                        binding.generatedWord.text = underscoreText
                        binding.generatedWord.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun extractRandomWord(responseData: String?): String? {
        val regex = """\["(\w+)"\]""".toRegex()
        val matchResult = regex.find(responseData ?: "")
        return matchResult?.groupValues?.get(1)
    }

    private fun extractDefinition(responseData: String?): String {
        try {
            val jsonObject = JSONObject(responseData)
            val errorMessage = jsonObject.getString("message")
            return "Error: $errorMessage"
        } catch (e: JSONException) {
            val jsonArray = JSONArray(responseData)
            if (jsonArray.length() > 0) {
                val jsonObject = jsonArray.getJSONObject(0)
                val meaningsArray = jsonObject.getJSONArray("meanings")
                if (meaningsArray.length() > 0) {
                    val firstMeaning = meaningsArray.getJSONObject(0)
                    val definitionsArray = firstMeaning.getJSONArray("definitions")
                    if (definitionsArray.length() > 0) {
                        val firstDefinition = definitionsArray.getJSONObject(0)
                        return firstDefinition.getString("definition")
                    }
                }
            }
            return "Definition not found"
        }
    }
    private fun resetGame() {

        // New Instance of Second fragment
        view?.findNavController()?.navigate(R.id.action_SecondFragment_self)
        Thread.sleep(1000)

    }



}



