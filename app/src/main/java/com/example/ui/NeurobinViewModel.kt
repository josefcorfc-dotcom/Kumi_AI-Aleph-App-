package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.VoiceBriefingManager
import com.example.data.GeminiClient
import com.example.data.db.AppDatabase
import com.example.data.db.NeuroMantraEntity
import com.example.data.db.SystemLogEntity
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PhaseInfo(
    val title: String,
    val desc: String,
    val formula: String,
    val defaultResult: String
)

data class NeurobinUiState(
    val step: Int = 0,
    val isEncrypted: Boolean = false,
    val resonance: Int = 81,
    val matrix: List<List<Int>> = listOf(
        listOf(1, 0, 1, 1),
        listOf(0, 1, 0, 0),
        listOf(1, 1, 1, 0),
        listOf(0, 0, 0, 1)
    ),
    val syncProgress: Int = 0,
    val isSyncing: Boolean = false,
    val logs: List<String> = listOf("> --- Sistema Inicializado: NODO SQ-3000_G6 ---"),
    val isAnalyzing: Boolean = false,
    val aiAnalysis: String = "",
    val neuroInput: String = "",
    val isGeneratingMantra: Boolean = false,
    val sovereignMantra: String = "",
    val digitalPersona: String = "CALF8712186T5 // NEUROBIN",
    val memoInput: String = "",
    val isOfuscating: Boolean = false,
    val semanticShadow: String = "",
    val isSpeaking: Boolean = false
)

class NeurobinViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val logDao = db.systemLogDao()
    private val mantraDao = db.neuroMantraDao()
    private val voiceManager = VoiceBriefingManager(application)

    private val _uiState = MutableStateFlow(NeurobinUiState())
    val uiState: StateFlow<NeurobinUiState> = _uiState.asStateFlow()

    private var resonanceJob: Job? = null
    private var syncJob: Job? = null

    val steps = listOf(
        PhaseInfo("Resonancia Armónica", "Sincronización de frecuencia base.", "R = 81", "K₀ = 81Hz"),
        PhaseInfo("Matriz de Cantor", "Muestreo de registros r₁-r₄.", "Density = 43.75%", "Densidad: 43.75%"),
        PhaseInfo("Salto de Stride", "Diagonalización de la matriz.", "Diag = [1,1,1,1]", "Σ_pura = 1010"),
        PhaseInfo("Blindaje Final", "Modulación XOR soberana.", "Token = ALEPH-KUMI", "Token: ALEPH-KUMI-2026")
    )

    init {
        startResonanceTimer()
        loadPersistedData()
    }

    private fun loadPersistedData() {
        viewModelScope.launch {
            val latestMantra = mantraDao.getLatestMantra().firstOrNull()
            if (latestMantra != null) {
                _uiState.update {
                    it.copy(
                        sovereignMantra = latestMantra.mantra,
                        digitalPersona = latestMantra.persona,
                        semanticShadow = latestMantra.memoShadow
                    )
                }
            }
        }
    }

    private fun startResonanceTimer() {
        resonanceJob?.cancel()
        resonanceJob = viewModelScope.launch {
            while (true) {
                delay(3000)
                _uiState.update { state ->
                    val nextRes = if (state.resonance % 2 == 0) state.resonance + 1 else state.resonance - 1
                    val boundedRes = nextRes.coerceIn(78, 99)
                    state.copy(resonance = boundedRes)
                }
            }
        }
    }

    fun addLog(msg: String) {
        val formattedLog = "> $msg"
        _uiState.update { state ->
            val updatedLogs = (state.logs + formattedLog).takeLast(6)
            state.copy(logs = updatedLogs)
        }
        viewModelScope.launch {
            try {
                logDao.insertLog(SystemLogEntity(message = formattedLog))
            } catch (e: Exception) {
                // Ignore DB write errors
            }
        }
    }

    fun nextStep() {
        val currentStep = _uiState.value.step
        if (currentStep < steps.size - 1) {
            val newStep = currentStep + 1
            _uiState.update { it.copy(step = newStep) }
            addLog("Fase $newStep completada.")
        } else {
            _uiState.update { it.copy(isEncrypted = true) }
            addLog("Encriptación Aleph-Σ: EXITOSA")
            startCloudSync()
        }
    }

    fun reset() {
        _uiState.update {
            it.copy(
                step = 0,
                isEncrypted = false,
                aiAnalysis = "",
                sovereignMantra = "",
                semanticShadow = "",
                digitalPersona = "CALF8712186T5 // NEUROBIN"
            )
        }
        addLog("Sistema actualizado y Reiniciado.")
    }

    fun startCloudSync() {
        if (_uiState.value.isSyncing) return
        _uiState.update { it.copy(isSyncing = true, syncProgress = 0) }
        addLog("Iniciando Túnel AEA (gs://datos-gold-ℵ1-976)...")

        syncJob?.cancel()
        syncJob = viewModelScope.launch {
            var progress = 0
            while (progress < 100) {
                delay(100)
                progress += 5
                _uiState.update { it.copy(syncProgress = progress) }
            }
            _uiState.update { it.copy(isSyncing = false) }
            addLog("Sincronización Total: NODO_MX_SQ (Apache License 2.0)")
            delay(2000)
            _uiState.update { it.copy(syncProgress = 0) }
        }
    }

    fun analyzeIntegrity() {
        if (_uiState.value.isAnalyzing) return
        _uiState.update { it.copy(isAnalyzing = true) }
        addLog("Consultando núcleo cognitivo Gemini...")

        viewModelScope.launch {
            val state = _uiState.value
            val report = GeminiClient.analyzeIntegrity(state.resonance, state.step, state.isEncrypted)
            _uiState.update { it.copy(aiAnalysis = report, isAnalyzing = false) }
            addLog("Análisis de integridad completado.")
        }
    }

    fun updateNeuroInput(text: String) {
        _uiState.update { it.copy(neuroInput = text) }
    }

    fun generateNeuroSync() {
        val input = _uiState.value.neuroInput.trim()
        if (input.isEmpty() || _uiState.value.isGeneratingMantra) return

        _uiState.update { it.copy(isGeneratingMantra = true) }
        addLog("Procesando entrada neuronal...")

        viewModelScope.launch {
            val (mantra, persona) = GeminiClient.generateNeuroMantra(input)
            _uiState.update {
                it.copy(
                    sovereignMantra = mantra,
                    digitalPersona = persona,
                    neuroInput = "",
                    isGeneratingMantra = false
                )
            }
            addLog("Mantra de soberanía generado.")

            try {
                mantraDao.insertMantra(
                    NeuroMantraEntity(
                        mantra = mantra,
                        persona = persona,
                        memoShadow = _uiState.value.semanticShadow
                    )
                )
            } catch (e: Exception) {
                // Ignore DB error
            }
        }
    }

    fun updateMemoInput(text: String) {
        _uiState.update { it.copy(memoInput = text) }
    }

    fun ofuscateMemo() {
        val memo = _uiState.value.memoInput.trim()
        if (memo.isEmpty() || _uiState.value.isOfuscating) return

        _uiState.update { it.copy(isOfuscating = true) }
        addLog("Iniciando Protocolo de Ofuscación...")

        viewModelScope.launch {
            val shadow = GeminiClient.generateSemanticShadow(memo)
            _uiState.update {
                it.copy(
                    semanticShadow = shadow,
                    memoInput = "",
                    isOfuscating = false
                )
            }
            addLog("Shadow Code generado con éxito.")
        }
    }

    fun handleVoiceBriefing() {
        if (_uiState.value.isSpeaking) return
        _uiState.update { it.copy(isSpeaking = true) }
        addLog("Generando síntesis de voz...")

        val text = if (_uiState.value.isEncrypted) {
            "Soberanía digital confirmada. El blindaje Aleph Sigma está operando al cien por ciento de su capacidad para el operador Cantoriano Leyva."
        } else {
            "Iniciando protocolos. Frecuencia de resonancia establecida en ${_uiState.value.resonance} hercios. Esperando autorización de Operador Cantoriano Leyva."
        }

        viewModelScope.launch {
            val base64Audio = GeminiClient.getTTSAudioBase64(text)
            if (base64Audio != null) {
                val success = voiceManager.playBase64PcmAudio(base64Audio)
                if (!success) {
                    voiceManager.speakNative(text)
                }
            } else {
                voiceManager.speakNative(text)
            }
            _uiState.update { it.copy(isSpeaking = false) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        resonanceJob?.cancel()
        syncJob?.cancel()
        voiceManager.shutdown()
    }
}
