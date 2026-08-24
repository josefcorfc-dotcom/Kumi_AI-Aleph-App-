package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.Content
import com.example.data.GeminiClient
import com.example.data.Part
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val isLoading: Boolean = false
)

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val inputText: String = "",
    val isSending: Boolean = false
)

class ChatViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private val conversationHistory = mutableListOf<Content>()

    fun updateInput(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    fun sendMessage() {
        val currentText = _uiState.value.inputText.trim()
        if (currentText.isEmpty() || _uiState.value.isSending) return

        _uiState.update { state ->
            state.copy(
                inputText = "",
                isSending = true,
                messages = state.messages + ChatMessage(text = currentText, isUser = true) + ChatMessage(text = "...", isUser = false, isLoading = true)
            )
        }

        viewModelScope.launch {
            val response = GeminiClient.sendMessage(conversationHistory, currentText)
            
            _uiState.update { state ->
                val newMessages = state.messages.dropLast(1) // Remove loading message
                val updatedMessages = if (response != null) {
                    newMessages + ChatMessage(text = response, isUser = false)
                } else {
                    newMessages + ChatMessage(text = "Error: Failed to get response.", isUser = false)
                }
                state.copy(messages = updatedMessages)
            }

            // Update history
            conversationHistory.add(Content(role = "user", parts = listOf(Part(text = currentText))))
            if (response != null) {
                conversationHistory.add(Content(role = "model", parts = listOf(Part(text = response))))
            }
            
            _uiState.update { it.copy(isSending = false) }
        }
    }
}
