package fr.isen.vincenti.isensmartcompanion.gemini

import android.content.Context
import com.google.ai.client.generativeai.GenerativeModel
import fr.isen.vincenti.isensmartcompanion.R

object GeminiService {
    val generativeModel = GenerativeModel("gemini-1.5-flash", "AIzaSyBIGzu564ZviiAEBKUPqW2x8exHVRvyW5I")

    suspend fun generateResponse(context: Context, input: String): String {
        return try {
            val response = generativeModel.generateContent(input)
            response.text ?: context.getString(R.string.no_response)
        } catch (e: Exception) {
            context.getString(R.string.error_generating_response, e.message)
        }
    }
}