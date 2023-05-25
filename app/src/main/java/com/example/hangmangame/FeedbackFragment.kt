package com.example.hangmangame

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import okhttp3.*
import java.io.IOException
import com.example.hangmangame.ValidationClass

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FeedbackFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FeedbackFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var email: String? = null
    private var feedback: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feedback, container, false)

        val emailField = view.findViewById<EditText>(R.id.editTextTextEmailAddress3)
        val feedbackField = view.findViewById<EditText>(R.id.editTextTextMultiLine)
        val sendButton = view.findViewById<Button>(R.id.sendButton)

        sendButton.setOnClickListener {
            val emailValue = emailField.text.toString()
            val feedbackValue = feedbackField.text.toString()
            if (ValidationClass.isEmailValid(emailValue)) {
                Log.i("FeedbackFragment", "Email: $emailValue")
                Log.i("FeedbackFragment", "Feedback: $feedbackValue")
                val authorizationSecret = readAuthorizationSecretFromFile()

                // Make API request
                val url = "https://api.clickup.com/api/v2/list/900501265994/task"
                val requestBody = FormBody.Builder()
                    .add("name", emailValue)
                    .add("description", feedbackValue)
                    .build()

                val request = Request.Builder()
                    .url(url)
                    .addHeader("Authorization", authorizationSecret)
                    .post(requestBody)
                    .build()

                val client = OkHttpClient()
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.e("FeedbackFragment", "API request failed", e)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val responseCode: Int = response.code
                        val responseBody: String? = response.body?.string()

                        if (response.isSuccessful) {
                            Log.i("FeedbackFragment", "Task created successfully")
                        } else {
                            Log.e("FeedbackFragment", "API request unsuccessful: $responseCode")
                            Log.e("FeedbackFragment", "Response body: $responseBody")
                        }

                        response.close()
                    }
                })


            }
            else {

                emailField.error = "Invalid email address"
            }
            }

        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FeedbackFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FeedbackFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun readAuthorizationSecretFromFile(): String {
        val inputStream = resources.openRawResource(R.raw.api_key)
        val api_key = inputStream.bufferedReader().use { it.readText() }
        return api_key.replace("\n", "").replace("\r", "")
    }
}