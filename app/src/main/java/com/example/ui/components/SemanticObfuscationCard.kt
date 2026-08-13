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
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Security
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyberPurple
import com.example.ui.theme.CyberPurpleLight
import com.example.ui.theme.CyberSurfaceBorder
import com.example.ui.theme.CyberTextMuted

@Composable
fun SemanticObfuscationCard(
    memoInput: String,
    onMemoChange: (String) -> Unit,
    isOfuscating: Boolean,
    semanticShadow: String,
    onOfuscateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(CyberPurple.copy(alpha = 0.05f))
            .border(1.dp, CyberPurple.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
            .padding(14.dp)
            .testTag("semantic_obfuscation_card")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = "Shield Security",
                    tint = CyberPurpleLight,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "OFUSCACIÓN SEMÁNTICA",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = CyberPurpleLight
                )
            }

            Button(
                onClick = onOfuscateClick,
                enabled = memoInput.isNotBlank() && !isOfuscating,
                modifier = Modifier
                    .height(32.dp)
                    .testTag("ofuscate_button"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CyberPurple.copy(alpha = 0.25f),
                    contentColor = Color.White
                )
            ) {
                if (isOfuscating) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(12.dp),
                        color = CyberPurpleLight,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "OFUSCAR NOTA",
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = memoInput,
            onValueChange = onMemoChange,
            placeholder = {
                Text(
                    text = "Escriba memo sensible para ofuscar...",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    color = CyberTextMuted
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .testTag("memo_input_field"),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 11.sp,
                color = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Black.copy(alpha = 0.4f),
                unfocusedContainerColor = Color.Black.copy(alpha = 0.4f),
                focusedBorderColor = CyberPurple,
                unfocusedBorderColor = CyberSurfaceBorder
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )

        AnimatedVisibility(
            visible = semanticShadow.isNotBlank(),
            enter = fadeIn() + slideInVertically()
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Black.copy(alpha = 0.6f))
                    .border(1.dp, CyberPurple.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    .padding(10.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Code,
                            contentDescription = "Code",
                            tint = CyberPurpleLight.copy(alpha = 0.6f),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "SHADOW_CODE:",
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            color = CyberPurpleLight.copy(alpha = 0.6f)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = semanticShadow,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        color = CyberPurpleLight
                    )
                }
            }
        }
    }
}
