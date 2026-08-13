package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ui.components.CantorMatrixView
import com.example.ui.components.CloudSyncTelemetryCard
import com.example.ui.components.CognitiveCoreCard
import com.example.ui.components.FooterActions
import com.example.ui.components.HeaderSection
import com.example.ui.components.NeuroSyncCard
import com.example.ui.components.PhaseProgressCard
import com.example.ui.components.SemanticObfuscationCard
import com.example.ui.components.SystemTerminalLogs
import com.example.ui.theme.CyberBackground
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.CyberSurfaceBorder

@Composable
fun NeurobinDashboardScreen(
    viewModel: NeurobinViewModel
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        CyberBackground,
                        CyberSurface,
                        CyberBackground
                    )
                )
            )
            .padding(top = topPadding, bottom = bottomPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(CyberSurface.copy(alpha = 0.85f))
                    .border(1.dp, CyberSurfaceBorder, RoundedCornerShape(28.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    HeaderSection(
                        digitalPersona = state.digitalPersona,
                        isSpeaking = state.isSpeaking,
                        onVoiceClick = { viewModel.handleVoiceBriefing() }
                    )

                    CantorMatrixView(
                        matrix = state.matrix,
                        step = state.step
                    )

                    PhaseProgressCard(
                        currentPhase = viewModel.steps[state.step],
                        step = state.step,
                        isEncrypted = state.isEncrypted
                    )

                    NeuroSyncCard(
                        neuroInput = state.neuroInput,
                        onInputChange = { viewModel.updateNeuroInput(it) },
                        isGeneratingMantra = state.isGeneratingMantra,
                        sovereignMantra = state.sovereignMantra,
                        onSyncClick = { viewModel.generateNeuroSync() }
                    )

                    SemanticObfuscationCard(
                        memoInput = state.memoInput,
                        onMemoChange = { viewModel.updateMemoInput(it) },
                        isOfuscating = state.isOfuscating,
                        semanticShadow = state.semanticShadow,
                        onOfuscateClick = { viewModel.ofuscateMemo() }
                    )

                    CognitiveCoreCard(
                        isAnalyzing = state.isAnalyzing,
                        aiAnalysis = state.aiAnalysis,
                        onAnalyzeClick = { viewModel.analyzeIntegrity() }
                    )

                    SystemTerminalLogs(
                        logs = state.logs
                    )

                    CloudSyncTelemetryCard(
                        syncProgress = state.syncProgress,
                        isSyncing = state.isSyncing,
                        isEncrypted = state.isEncrypted,
                        resonance = state.resonance,
                        onSyncClick = { viewModel.startCloudSync() }
                    )

                    FooterActions(
                        isEncrypted = state.isEncrypted,
                        onNextPhaseClick = { viewModel.nextStep() },
                        onResetClick = { viewModel.reset() }
                    )
                }
            }
        }
    }
}
