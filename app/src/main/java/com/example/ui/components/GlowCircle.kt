package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.DarkNeutral
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonPink

@Composable
fun GlowCircle(
    modifier: Modifier = Modifier,
    percentage: Float, // value between 0.0f and 1.0f
    circleColorStart: Color = NeonCyan,
    circleColorEnd: Color = NeonPink,
    trackColor: Color = DarkNeutral,
    strokeWidth: Dp = 12.dp,
    centerContent: @Composable () -> Unit
) {
    val animatedPercentage by animateFloatAsState(
        targetValue = percentage.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 1000)
    )

    Box(
        modifier = modifier.aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(strokeWidth / 2)) {
            // Background trace
            drawCircle(
                color = trackColor,
                radius = size.minDimension / 2,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )

            // Neon glowing arc
            drawArc(
                brush = Brush.sweepGradient(
                    colors = listOf(circleColorStart, circleColorEnd, circleColorStart)
                ),
                startAngle = -90f,
                sweepAngle = animatedPercentage * 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }

        Box(modifier = Modifier.padding(strokeWidth + 8.dp)) {
            centerContent()
        }
    }
}
