package com.samrudhasolutions.virtualtutoringplatform.respository

import com.samrudhasolutions.bolo.network.ApiClient
import com.samrudhasolutions.bolo.response.ChatRequest
import com.samrudhasolutions.bolo.response.Message
import com.samrudhasolutions.bolo.utils.ANSBEFOREEDIT
import com.samrudhasolutions.bolo.utils.ANSWERAFTEREDIT
import com.samrudhasolutions.bolo.utils.CHATGPT_MODEL


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
