package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberSurfaceBorder
import com.example.ui.theme.CyberTextMuted

@Composable
fun NeuroSyncCard(
    neuroInput: String,
    onInputChange: (String) -> Unit,
    isGeneratingMantra: Boolean,
    sovereignMantra: String,
    onSyncClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(CyberCyan.copy(alpha = 0.05f))
            .border(1.dp, CyberCyan.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
            .padding(14.dp)
            .testTag("neuro_sync_card")
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Psychology,
                contentDescription = "Brain Circuit",
                tint = CyberCyan,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = "Sparkles",
                tint = CyberEmerald,
                modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "NEURO-SINCRONIZACIÓN",
                style = MaterialTheme.typography.labelSmall,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = CyberCyan
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = neuroInput,
            onValueChange = onInputChange,
            placeholder = {
                Text(
                    text = "Ingrese override neuronal...",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    color = CyberTextMuted
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .testTag("neuro_input_field"),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 11.sp,
                color = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Black.copy(alpha = 0.4f),
                unfocusedContainerColor = Color.Black.copy(alpha = 0.4f),
                focusedBorderColor = CyberCyan,
                unfocusedBorderColor = CyberSurfaceBorder
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onSyncClick,
            enabled = neuroInput.isNotBlank() && !isGeneratingMantra,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .testTag("neuro_sync_button"),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CyberCyan.copy(alpha = 0.2f),
                contentColor = Color.White,
                disabledContainerColor = CyberCyan.copy(alpha = 0.05f),
                disabledContentColor = CyberTextMuted
            )
        ) {
            if (isGeneratingMantra) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = CyberCyan,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "SINCRONIZANDO...",
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "SINCRONIZAR NÚCLEO",
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        AnimatedVisibility(
            visible = sovereignMantra.isNotBlank(),
            enter = fadeIn() + slideInVertically()
        ) {
            Column(modifier = Modifier.padding(top = 10.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(CyberEmerald.copy(alpha = 0.08f))
                        .border(1.dp, CyberEmerald.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                        .padding(10.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "MANTRA DE SOBERANÍA",
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 8.sp,
                                color = CyberEmerald.copy(alpha = 0.7f)
                            )
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = "Bolt",
                                tint = CyberEmerald,
                                modifier = Modifier.size(10.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "\"$sovereignMantra\"",
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
