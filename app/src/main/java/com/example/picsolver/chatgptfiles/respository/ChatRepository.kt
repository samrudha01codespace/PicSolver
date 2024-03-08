package com.example.picsolver.chatgptfiles.respository

import com.samrudhasolutions.bolo.network.ApiClient
import com.example.picsolver.chatgptfiles.response.ChatRequest
import com.samrudhasolutions.bolo.response.Message
import com.example.picsolver.chatgptfiles.utils.ANSBEFOREEDIT
import com.example.picsolver.chatgptfiles.utils.ANSWERAFTEREDIT
import com.example.picsolver.chatgptfiles.utils.CHATGPT_MODEL
import java.sql.DriverManager.println


class ChatRepository {

    private val apiClient = ApiClient.getInstance()

    suspend fun sendToChatGPT(messagetochatgpt: String, yourstatement: String) {
        try {
            // Construct a ChatRequest object with the user's message
            val chatRequest = ChatRequest(
                arrayListOf(
                    Message(yourstatement, "system"),
                    Message(messagetochatgpt, "user")
                ), CHATGPT_MODEL
            )

            // Make a network call to the chatbot API
            val response = apiClient.createChatCompletion(chatRequest)
             response.choices[0].message.let {
                 ANSBEFOREEDIT = it.toString()

                 if (ANSBEFOREEDIT.length >= 20) {
                     ANSWERAFTEREDIT = ANSBEFOREEDIT.substring(
                         16,
                         ANSBEFOREEDIT.length - 18
                     )
                 } else {
                     println("Input string should have at least 20 characters.")
                 }
             }
        } catch (e: Exception) {
            // Handle exceptions by printing the stack trace and return an empty string
            e.printStackTrace()

        }

    }
}
