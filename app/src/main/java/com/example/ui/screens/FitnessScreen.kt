package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.VayuViewModel
import com.example.ui.components.GlassCard
import com.example.ui.theme.*

@Composable
fun FitnessScreen(
    viewModel: VayuViewModel,
    modifier: Modifier = Modifier
) {
    val stats by viewModel.fitnessStats.collectAsState()

    Column(
        modifier = modifier
            .testTag("fitness_screen")
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- 1. CORE METRICS CARD (Blue Bento) ---
        GlassCard(
            modifier = Modifier.fillMaxWidth().testTag("fitness_metrics_card"),
            containerColor = BentoBgSteps,
            borderColor = BentoTextSteps
        ) {
            Column {
                Text(
                    text = "6. FITNESS & HEALTH TRACE",
                    color = BentoTextSteps.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(12.dp))

                stats?.let { validStats ->
                    MetricDisplayRow(icon = "🏃", name = "Running Distance", info = "${validStats.runningKm} km", color = BentoTextSteps)
                    MetricDisplayRow(icon = "🏋️", name = "Workout Logs", info = String.format("%.2f hours", validStats.workoutHours), color = BentoTextSteps)
                    MetricDisplayRow(icon = "🔥", name = "Calories Burned", info = "${validStats.calories} kcal", color = BentoTextSteps)
                    MetricDisplayRow(icon = "⚖️", name = "Current Weight", info = "${validStats.weightKg} kg", color = BentoTextSteps)
                    MetricDisplayRow(icon = "💧", name = "Water Hydration Intake", info = "${validStats.waterIntakeL} L", color = BentoTextSteps)
                    MetricDisplayRow(icon = "🛌", name = "Sleep Duration", info = String.format("%.2f hours", validStats.sleepHours), color = BentoTextSteps)
                } ?: run {
                    Text("No metrics found in device space core.", color = BentoTextSteps, fontSize = 12.sp)
                }
            }
        }

        // --- 2. WATER RE-CHARGE ACTION WIDGET (Coral/Water Bento) ---
        GlassCard(
            modifier = Modifier.fillMaxWidth().testTag("water_recharge_card"),
            containerColor = BentoBgWater,
            borderColor = BentoTextWater
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "HYDRATION ASSIST",
                        color = BentoTextWater,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Add 250ml water intake to state", color = BentoTextWater.copy(alpha = 0.8f), fontSize = 11.sp, fontWeight = FontWeight.Medium)
                }

                Button(
                    onClick = { viewModel.addWaterCup() },
                    colors = ButtonDefaults.buttonColors(containerColor = BentoTextWater),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.testTag("add_water_button")
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Water", tint = Color.White)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Drink", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }

        // --- 3. WEEKLY WORKOUT OVERVIEW CHART (Green Bento) ---
        GlassCard(
            modifier = Modifier.fillMaxWidth().testTag("weekly_progress_card"),
            containerColor = BentoBgStreak,
            borderColor = BentoTextStreak
        ) {
            Column {
                Text(
                    text = "FITNESS WEEKLY ARCHITECTURE",
                    color = BentoTextStreak.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val calories = listOf(350f, 600f, 480f, 560f, 720f, 300f, 510f)
                        val maxCal = 800f
                        val widthInterval = size.width / 7
                        val barWidth = 12.dp.toPx()

                        calories.forEachIndexed { index, value ->
                            val x = index * widthInterval + (widthInterval - barWidth) / 2
                            val barHeight = (value / maxCal) * (size.height - 30.dp.toPx())
                            val y = size.height - 25.dp.toPx() - barHeight

                            drawRoundRect(
                                brush = Brush.verticalGradient(
                                    colors = listOf(BentoTextStreak, BentoTextStreak.copy(alpha = 0.3f))
                                ),
                                topLeft = Offset(x, y),
                                size = Size(barWidth, barHeight),
                                cornerRadius = CornerRadius(4.dp.toPx())
                            )
                        }
                    }

                    // Days indicator names
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val days = listOf("M", "T", "W", "T", "F", "S", "S")
                        days.forEach { day ->
                            Text(
                                text = day,
                                color = BentoTextStreak.copy(alpha = 0.7f),
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.width(36.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MetricDisplayRow(
    icon: String,
    name: String,
    info: String,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.08f))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = icon, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = name, color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
        Text(text = info, color = color, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, fontFamily = FontFamily.Monospace)
    }
}
