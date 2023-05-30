package com.example.hangmangame

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("UNREACHABLE_CODE")
class UserProfileFragment : Fragment() {
    private lateinit var usernameEditText: EditText
    private lateinit var selectImageButton: Button
    private lateinit var startButton: Button

    private lateinit var selectedImageBitmap: Bitmap
    private lateinit var selectedImageFile: File


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)

        usernameEditText = view.findViewById(R.id.userSetField)
        selectImageButton = view.findViewById(R.id.addPictureButton)
        startButton = view.findViewById(R.id.submitButton)

        selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }


        startButton.setOnClickListener {
            val username = usernameEditText.text.toString()

            // Zapisywanie nazwy użytkownika lokalnie
            saveUsernameLocally(username)

            // Zapisywanie wybranego obrazka lokalnie
            saveImageLocally(selectedImageBitmap)

            processUserData(username, selectedImageFile.path)

            // Wyczyszczenie pola do wpisywania użytkownika
                usernameEditText.text.clear()

        }

        return view
    }

    private fun saveUsernameLocally(username: String) {
        // Możesz użyć SharedPreferences do zapisu nazwy użytkownika
        val sharedPreferences = requireActivity().getSharedPreferences("HangmanPrefs", Activity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.apply()
    }

    fun getSavedUserName(): String? {
        val sharedPreferences = requireActivity().getSharedPreferences("HangmanPrefs", Activity.MODE_PRIVATE)
        return sharedPreferences.getString("username", null)
    }

    private fun saveImageLocally(bitmap: Bitmap): String {
        val directory = requireContext().filesDir // Katalog wewnętrzny aplikacji
        val file = File(directory, "selected_image.png")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        return file.absolutePath
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data
            selectedImageBitmap = BitmapFactory.decodeStream(requireActivity().contentResolver.openInputStream(selectedImageUri!!))

            // Tworzenie pliku, w którym zostanie zapisane wybrane zdjęcie
            selectedImageFile = File(requireContext().cacheDir, "selected_image.png")
        }

    }



    private fun processUserData(username: String, imagePath: String) {
        Log.d("Hangman", "Nazwa użytkownika: $username")
        Log.d("Hangman", "Ścieżka do obrazka: $imagePath")
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}