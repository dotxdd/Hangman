package com.example.hangmangame

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hangmangame.databinding.FragmentSecondBinding
import okhttp3.*
import okhttp3.internal.wait
import java.io.IOException

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!


    private var randomWord: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        Log.i("SecondFragment", "t0")

        binding.buttonNewGame.setOnClickListener{
            resetGame()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("SecondFragment", "t1")
        // Pobieranie losowego słowa z API
        fetchRandomWord()
        Thread.sleep(950)
        Log.i("SecondFragment", "t2")
        val generatedWordTextView = binding.generatedWord
        val alphabetButtonClickListener = AlphabetButtonClickListener(
            randomWord ?: "",
            binding.generatedWord,
            binding.imgView
        )

        binding.a.setOnClickListener(alphabetButtonClickListener)
        binding.b.setOnClickListener(alphabetButtonClickListener)
        binding.c.setOnClickListener(alphabetButtonClickListener)
        binding.d.setOnClickListener(alphabetButtonClickListener)
        binding.e.setOnClickListener(alphabetButtonClickListener)
        binding.f.setOnClickListener(alphabetButtonClickListener)
        binding.g.setOnClickListener(alphabetButtonClickListener)
        binding.h.setOnClickListener(alphabetButtonClickListener)
        binding.i.setOnClickListener(alphabetButtonClickListener)
        binding.j.setOnClickListener(alphabetButtonClickListener)
        binding.k.setOnClickListener(alphabetButtonClickListener)
        binding.l.setOnClickListener(alphabetButtonClickListener)
        binding.m.setOnClickListener(alphabetButtonClickListener)
        binding.n.setOnClickListener(alphabetButtonClickListener)
        binding.o.setOnClickListener(alphabetButtonClickListener)
        binding.p.setOnClickListener(alphabetButtonClickListener)
        binding.r.setOnClickListener(alphabetButtonClickListener)
        binding.s.setOnClickListener(alphabetButtonClickListener)
        binding.t.setOnClickListener(alphabetButtonClickListener)
        binding.u.setOnClickListener(alphabetButtonClickListener)
        binding.v.setOnClickListener(alphabetButtonClickListener)
        binding.w.setOnClickListener(alphabetButtonClickListener)
        binding.x.setOnClickListener(alphabetButtonClickListener)
        binding.y.setOnClickListener(alphabetButtonClickListener)
        binding.z.setOnClickListener(alphabetButtonClickListener)
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
                // Obsługa błędu pobierania danych z API
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                randomWord = extractRandomWord(responseData)

                // Logowanie pobranego słowa
                randomWord?.let {
                    val wordLength = it.length
                    Log.i("SecondFragment", "Pobrane słowo: $it")
                    Log.i("SecondFragment", "Pobrane słowo długość: $wordLength")
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
        // Wyodrębnij losowe słowo z odpowiedzi API
        val regex = """\["(\w+)"\]""".toRegex()
        val matchResult = regex.find(responseData ?: "")
        return matchResult?.groupValues?.get(1)
    }
    private fun resetGame() {
        val fragment = SecondFragment()
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }
}