package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberCyanDark
import com.example.ui.theme.CyberSurfaceBorder
import com.example.ui.theme.CyberTextMuted

@Composable
fun CantorMatrixView(
    matrix: List<List<Int>>,
    step: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.GridOn,
                contentDescription = "Matrix Icon",
                tint = CyberCyan.copy(alpha = 0.6f),
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.padding(start = 6.dp))
            Text(
                text = "MATRIZ DE MEMORIA (ℵ₁)",
                style = MaterialTheme.typography.labelSmall,
                fontSize = 10.sp,
                fontWeight = FontWeight.Black,
                color = CyberCyan.copy(alpha = 0.6f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Black.copy(alpha = 0.4f))
                .border(1.dp, CyberSurfaceBorder, RoundedCornerShape(16.dp))
                .padding(12.dp)
                .testTag("cantor_matrix_grid"),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            matrix.forEachIndexed { i, row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    row.forEachIndexed { j, bit ->
                        val isDiagonal = i == j && step >= 2
                        val bgColor by animateColorAsState(
                            targetValue = if (isDiagonal) CyberCyanDark else Color.Transparent,
                            animationSpec = tween(500),
                            label = "bgColor"
                        )
                        val borderColor by animateColorAsState(
                            targetValue = if (isDiagonal) Color.White else CyberSurfaceBorder,
                            animationSpec = tween(500),
                            label = "borderColor"
                        )
                        val textColor by animateColorAsState(
                            targetValue = if (isDiagonal) Color.White else CyberTextMuted,
                            animationSpec = tween(500),
                            label = "textColor"
                        )

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(bgColor)
                                .border(1.dp, borderColor, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = bit.toString(),
                                fontFamily = FontFamily.Monospace,
                                fontWeight = if (isDiagonal) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 12.sp,
                                color = textColor
                            )
                        }
                    }
                }
            }
        }
    }
}
