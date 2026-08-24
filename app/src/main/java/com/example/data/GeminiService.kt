package com.example.data

import android.util.Log
import com.example.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface GeminiApi {
    @POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GenerateContentRequest
    ): GenerateContentResponse

    @POST("v1beta/models/gemini-2.5-flash-preview-tts:generateContent")
    suspend fun generateTTS(
        @Query("key") apiKey: String,
        @Body request: GenerateContentRequest
    ): GenerateContentResponse
}

object GeminiClient {
    private const val TAG = "GeminiClient"
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val api: GeminiApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GeminiApi::class.java)
    }

    private fun getApiKey(): String {
        return try {
            val key = BuildConfig.GEMINI_API_KEY
            if (key.isNullOrBlank() || key == "MY_GEMINI_API_KEY") "" else key
        } catch (e: Exception) {
            ""
        }
    }

    suspend fun analyzeIntegrity(resonance: Int, step: Int, isEncrypted: Boolean): String = withContext(Dispatchers.IO) {
        val apiKey = getApiKey()
        if (apiKey.isEmpty()) {
            return@withContext if (isEncrypted) {
                "[DIAGNÓSTICO SOBERANO]: Blindaje Aleph-Σ al 100% de capacidad. Integridad cuántica verificada en Nodo SQ-3000_G6."
            } else {
                "[DIAGNÓSTICO SOBERANO]: Frecuencia de resonancia a ${resonance}Hz en Fase ${step + 1}/4. Nivel de blindaje nominal, aguardando modulación final."
            }
        }

        val systemPrompt = "Eres el sistema operativo KUMI Aleph-Σ (NEUROBIN v26.2), una IA de seguridad avanzada. Hablas en español técnico, futurista y autoritario pero elegante. El operador es José Francisco Cantoriano Leyva (CALF8712186T5)."
        val userPrompt = "Analiza este estado de sistema: Resonancia actual ${resonance}Hz, Fase ${step + 1}/4, Estado Encriptación: ${if (isEncrypted) "Activa" else "En proceso"}. Proporciona un diagnóstico breve (2 frases) sobre la integridad del blindaje."

        try {
            val request = GenerateContentRequest(
                contents = listOf(Content(parts = listOf(Part(text = userPrompt)))),
                systemInstruction = Content(parts = listOf(Part(text = systemPrompt)))
            )
            val response = api.generateContent(apiKey, request)
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: "[NEUROBIN COGNITIVO]: Integridad de resonancia verificada sin anomalías de densidad."
        } catch (e: Exception) {
            Log.e(TAG, "Integrity analysis failed", e)
            "[DIAGNÓSTICO ALTERNO]: Enlace secundario activo. Frecuencia ${resonance}Hz estable con tolerancia XOR de 0.001%."
        }
    }

    suspend fun generateNeuroMantra(input: String): Pair<String, String> = withContext(Dispatchers.IO) {
        val apiKey = getApiKey()
        if (apiKey.isEmpty()) {
            val defaultMantra = "Blindaje Cantoriano impenetrable en la matriz transfinita."
            val defaultPersona = "SOMBRA ALFA // NEUROBIN"
            return@withContext Pair(defaultMantra, defaultPersona)
        }

        val systemPrompt = "Eres el oráculo de seguridad de KUMI Aleph-Σ. Transforma la entrada del usuario en un mantra de soberanía digital críptico y poderoso en español. Máximo 10 palabras."
        try {
            val request = GenerateContentRequest(
                contents = listOf(Content(parts = listOf(Part(text = input)))),
                systemInstruction = Content(parts = listOf(Part(text = systemPrompt)))
            )
            val response = api.generateContent(apiKey, request)
            val mantra = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: "Soberanía digital confirmada en el canal de resonancia $input"

            val personaPrompt = "Genera un nombre de agente de seguridad cibernética de 2 palabras (ej. Sombra Alfa) basado en este mantra: $mantra"
            val personaRequest = GenerateContentRequest(
                contents = listOf(Content(parts = listOf(Part(text = personaPrompt))))
            )
            val personaResponse = api.generateContent(apiKey, personaRequest)
            val persona = personaResponse.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: "CALF8712186T5 // NEUROBIN"

            Pair(mantra.trim('"').trim(), persona.replace("\n", "").trim())
        } catch (e: Exception) {
            Log.e(TAG, "Neuro mantra generation failed", e)
            Pair("Resonancia $input integrada al núcleo de seguridad Aleph-Σ.", "SOMBRAMX // CALF87")
        }
    }

    suspend fun generateSemanticShadow(memo: String): String = withContext(Dispatchers.IO) {
        val apiKey = getApiKey()
        if (apiKey.isEmpty()) {
            return@withContext "SIGMA_CODE::0x${memo.hashCode().toString(16).uppercase()} [MEMO_PROTECTED_ALEPH]"
        }

        val systemPrompt = "Eres un algoritmo de encriptación semántica. Recibes un texto y lo conviertes en un 'shadow code' que parece lenguaje de programación esotérico o fórmulas matemáticas crípticas en español, pero que retiene el significado emocional original. Formato corto."
        try {
            val request = GenerateContentRequest(
                contents = listOf(Content(parts = listOf(Part(text = memo)))),
                systemInstruction = Content(parts = listOf(Part(text = systemPrompt)))
            )
            val response = api.generateContent(apiKey, request)
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: "SHADOW_ALEPH(0x${memo.hashCode().toString(16)}) -> { ℵ₁ :: PROTECTED }"
        } catch (e: Exception) {
            Log.e(TAG, "Semantic shadow generation failed", e)
            "SHADOW_CODE::${memo.uppercase().replace(" ", "_")}_ENCRYPTED"
        }
    }

    suspend fun getTTSAudioBase64(text: String): String? = withContext(Dispatchers.IO) {
        val apiKey = getApiKey()
        if (apiKey.isEmpty()) return@withContext null

        try {
            val request = GenerateContentRequest(
                contents = listOf(Content(parts = listOf(Part(text = "Say in a calm, highly professional technical female voice: $text")))),
                generationConfig = GenerationConfig(
                    responseModalities = listOf("AUDIO"),
                    speechConfig = SpeechConfig(
                        voiceConfig = VoiceConfig(
                            prebuiltVoiceConfig = PrebuiltVoiceConfig(voiceName = "Kore")
                        )
                    )
                )
            )
            val response = api.generateTTS(apiKey, request)
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.inlineData?.data
        } catch (e: Exception) {
            Log.e(TAG, "TTS Audio failed", e)
            null
        }
    }

    suspend fun sendMessage(history: List<Content>, newMessage: String): String? = withContext(Dispatchers.IO) {
        val apiKey = getApiKey()
        if (apiKey.isEmpty()) {
            return@withContext "Error: No API key provided."
        }

        try {
            val userContent = Content(
                role = "user",
                parts = listOf(Part(text = newMessage))
            )
            
            val requestContents = history + userContent

            val request = GenerateContentRequest(
                contents = requestContents
            )
            val response = api.generateContent(apiKey, request)
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
        } catch (e: Exception) {
            Log.e(TAG, "Chat generation failed", e)
            null
        }
    }
}
