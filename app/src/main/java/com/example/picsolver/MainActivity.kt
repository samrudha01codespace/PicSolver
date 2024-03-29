package com.example.picsolver

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.example.picsolver.chatgptfiles.utils.ANSWERAFTEREDIT
import com.example.picsolver.chatgptfiles.respository.ChatRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity() {

    private lateinit var buttonSend: Button
    private lateinit var editTextUserInput: EditText
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private val chatRepository = ChatRepository()
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        buttonSend = findViewById(R.id.buttonSendImageAndText)
        imageView = findViewById(R.id.imageView)
        editTextUserInput = findViewById(R.id.editTextUserInput)
        textView = findViewById(R.id.textView)

        // Set OnClickListener for the send button
        buttonSend.setOnClickListener {
            val userStatement = editTextUserInput.text.toString()
            if (userStatement.isNotEmpty()) {
                // Call function to send both image and statement to ChatGPT
                sendToChatGPT(userStatement)
            } else {
                Toast.makeText(this, "Please enter your statement", Toast.LENGTH_SHORT).show()
            }
        }

        // Set OnClickListener for the image upload button
        imageView.setOnClickListener {
            openFileChooser()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun sendToChatGPT(userStatement: String) {
        textView.text = "Please wait..."
        buttonSend.text = "Solving...."

        // Call function to process image and statement
        lifecycleScope.launch {
            try {
                val imageUri = imageView.tag as? Uri
                if (imageUri != null) {
                    val imageText = processImageFromUri(imageUri)
                    // Call repository function to send image and statement to ChatGPT
                    chatRepository.sendToChatGPT(userStatement, imageText)
                    textView.text = ANSWERAFTEREDIT
                    buttonSend.text = "Solved.."
                } else {
                    Toast.makeText(this@MainActivity, "Please select an image", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                textView.text = "Error: ${e.message}"
            }
        }
    }

    private fun openFileChooser() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri = data.data!!
            imageView.setImageURI(imageUri)
            imageView.tag = imageUri // Attach image URI to ImageView
        }
    }

    private suspend fun processImageFromUri(imageUri: Uri): String {
        val image: InputImage = InputImage.fromFilePath(this, imageUri)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        return try {
            val result = recognizer.process(image).await() // This line is important

            // Extract recognized text from the result
            val recognizedText = result.text

            // Return the recognized text
            recognizedText
        } catch (e: Exception) {
            e.printStackTrace()
            "" // Return empty string in case of any errors
        }
    }
}
