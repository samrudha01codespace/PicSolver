package com.example.picsolver.chatgptfiles.response

import com.samrudhasolutions.bolo.response.Message

data class Choice(
    val index: Int,
    val message: Message
)