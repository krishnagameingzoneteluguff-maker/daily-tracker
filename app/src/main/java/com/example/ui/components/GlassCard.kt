package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.CosmicSpaceCard
import com.example.ui.theme.GlowBorder

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    borderColor: Color = GlowBorder,
    cornerRadius: Dp = 24.dp,
    containerColor: Color? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val finalContainerColor = containerColor ?: CosmicSpaceCard
    Box(
        modifier = modifier
            .shadow(
                elevation = 1.5.dp,
                shape = RoundedCornerShape(cornerRadius),
                ambientColor = Color.Black.copy(alpha = 0.03f),
                spotColor = Color.Black.copy(alpha = 0.03f)
            )
            .clip(RoundedCornerShape(cornerRadius))
            .background(finalContainerColor)
            .border(
                BorderStroke(
                    width = 1.dp,
                    color = borderColor.copy(alpha = 0.35f)
                ),
                shape = RoundedCornerShape(cornerRadius)
            )
    ) {
        Box(modifier = Modifier.padding(20.dp)) {
            content()
        }
    }
}
