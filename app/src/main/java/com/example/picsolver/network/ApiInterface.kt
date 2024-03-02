package com.samrudhasolutions.bolo.network
import com.samrudhasolutions.bolo.response.ChatRequest
import com.samrudhasolutions.bolo.response.ChatResponse
import com.samrudhasolutions.bolo.utils.OPENAI_API_KEY
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiInterface {

    @POST("chat/completions")
    suspend fun createChatCompletion(
        @Body chatRequest: ChatRequest,
        @Header("Content-Type") contentType : String = "application/json",
        @Header("Authorization") authorization : String = "Bearer $OPENAI_API_KEY",
    ) : ChatResponse

}