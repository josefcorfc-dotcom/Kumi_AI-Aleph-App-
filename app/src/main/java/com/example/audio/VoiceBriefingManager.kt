package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.speech.tts.TextToSpeech
import android.util.Base64
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

class VoiceBriefingManager(private val context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private var isTtsReady = false

    init {
        tts = TextToSpeech(context.applicationContext, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale("es", "MX"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                tts?.setLanguage(Locale.getDefault())
            }
            tts?.setSpeechRate(0.95f)
            tts?.setPitch(1.05f)
            isTtsReady = true
        } else {
            Log.e("VoiceBriefingManager", "TextToSpeech init failed")
        }
    }

    fun speakNative(text: String, onComplete: () -> Unit = {}) {
        if (isTtsReady && tts != null) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "BriefingUtterance")
            onComplete()
        } else {
            onComplete()
        }
    }

    suspend fun playBase64PcmAudio(base64Audio: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val bytes = Base64.decode(base64Audio, Base64.DEFAULT)
            val sampleRate = 24000
            val bufferSize = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )

            val audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(bufferSize.coerceAtLeast(bytes.size))
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            audioTrack.write(bytes, 0, bytes.size)
            audioTrack.play()

            // Wait for audio to finish playing
            val durationMs = (bytes.size.toDouble() / (sampleRate * 2) * 1000).toLong()
            kotlinx.coroutines.delay(durationMs + 200)

            audioTrack.stop()
            audioTrack.release()
            true
        } catch (e: Exception) {
            Log.e("VoiceBriefingManager", "Failed to play PCM audio", e)
            false
        }
    }

    fun shutdown() {
        try {
            tts?.stop()
            tts?.shutdown()
        } catch (e: Exception) {
            Log.e("VoiceBriefingManager", "TTS shutdown error", e)
        }
    }
}
