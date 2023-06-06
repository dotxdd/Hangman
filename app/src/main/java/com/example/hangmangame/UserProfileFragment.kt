package com.example.hangmangame

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var userImageView: ImageView
    private lateinit var usernameEditText: EditText
    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageBitmap: Bitmap? = null


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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "User"
        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)
        usernameEditText = view.findViewById(R.id.userSetField) // Inicjalizacja usernameEditText
        return view
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Inicjalizacja widoków
        val userSetField: EditText = view.findViewById(R.id.userSetField)
        val addPictureButton: Button = view.findViewById(R.id.addPictureButton)
        val submitButton: Button = view.findViewById(R.id.submitButton)

        // Pobieranie zapisanych danych
        val sharedPreferences = requireActivity().getSharedPreferences("HangmanPrefs", Activity.MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("username", "")
        val savedImagePath = sharedPreferences.getString("imagePath", "")

        // Wypełnienie domyślnymi wartościami
        userSetField.setText(savedUsername)

        // Obsługa przycisku "save"
        submitButton.setOnClickListener {
            val username = userSetField.text.toString().trim()

            if (username.isNotEmpty()) {
                saveUsernameLocally(username)
                Toast.makeText(requireContext(), "Username saved successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Username cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        // Obsługa przycisku "add picture"
        addPictureButton.setOnClickListener {
            chooseImageFromGallery()
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        submitButton.setOnClickListener {
            val username = userSetField.text.toString().trim()

            if (username.isNotEmpty()) {
                saveUsernameLocally(username)
                saveChanges()
                Toast.makeText(requireContext(), "Changes saved successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Username cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun chooseImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE)
    }

    private fun saveChanges() {
        val username = usernameEditText.text.toString()
        saveUsernameLocally(username)
        saveImageLocally(selectedImageBitmap)
    }

    private fun saveUsernameLocally(username: String) {
        val sharedPreferences = requireActivity().getSharedPreferences("HangmanPrefs", Activity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.apply()
    }

    private fun saveImageLocally(bitmap: Bitmap?): String? {
        if (bitmap != null) {
            val directory = requireContext().cacheDir
            val file = File(directory, "selected_image.png")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            return file.absolutePath
        }
        return null
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            imageUri?.let {
                val bitmap: Bitmap? = getBitmapFromUri(it)
                bitmap?.let {
                    val imagePath = saveImageLocally(it)
                    if (imagePath != null) {
                        saveImagePathLocally(imagePath)
                    }
                    Toast.makeText(requireContext(), "Image saved successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            val inputStream = requireActivity().contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun saveImagePathLocally(imagePath: String) {
        val sharedPreferences = requireActivity().getSharedPreferences("HangmanPrefs", Activity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("imagePath", imagePath)
        editor.apply()
    }



    private fun processUserData(username: String, imagePath: String) {
        Log.d("Hangman", "Nazwa użytkownika: $username")
        Log.d("Hangman", "Ścieżka do obrazka: $imagePath")
    }

    companion object {
        private const val REQUEST_IMAGE = 1
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