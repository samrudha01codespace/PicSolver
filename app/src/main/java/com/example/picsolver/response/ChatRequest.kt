package com.samrudhasolutions.bolo.response

data class ChatRequest(
    val messages: List<Message>,
    val model: String
)