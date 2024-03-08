package com.example.picsolver.chatgptfiles.response

import com.samrudhasolutions.bolo.response.Message

data class ChatRequest(
    val messages: List<Message>,
    val model: String
)