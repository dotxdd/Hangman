package com.example.hangmangame

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserScoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserScoreFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onResume() {
        super.onResume()
        refreshUserScoreFragment()
    }

    @SuppressLint("SdCardPath")
    private fun refreshUserScoreFragment() {
        // Tutaj umieść kod, który odświeża zawartość fragmentu
        val userImageView: ImageView = requireView().findViewById(R.id.userImage)
        val imagePath: String = "/data/user/0/com.example.hangmangame/cache/selected_image.png"
        val bitmap: Bitmap = BitmapFactory.decodeFile(imagePath)
        userImageView.setImageBitmap(bitmap)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_score, container, false)
    }

    @SuppressLint("SdCardPath")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userImageView: ImageView = view.findViewById(R.id.userImage)


        val imagePath: String = "/data/user/0/com.example.hangmangame/cache/selected_image.png"
        val bitmap: Bitmap = BitmapFactory.decodeFile(imagePath)
        userImageView.setImageBitmap(bitmap)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val sharedPreferences = requireActivity().getSharedPreferences("HangmanPrefs", Activity.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", null)
        Log.d("Hangman", "Nazwa użytkownika z SharedPreferences: $username")

        val textViewUserName: TextView = requireView().findViewById(R.id.textViewUserName)
        textViewUserName.text = username
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserScoreFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserScoreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}