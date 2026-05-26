package com.example.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.VayuViewModel
import com.example.ui.components.GlassCard
import com.example.ui.components.GlowCircle
import com.example.ui.theme.*

@Composable
fun DashboardScreen(
    viewModel: VayuViewModel,
    modifier: Modifier = Modifier
) {
    val tasks by viewModel.allTasks.collectAsState()
    val levelState by viewModel.levelState.collectAsState()

    val totalTasks = tasks.size
    val completedTasks = tasks.count { it.completed }
    val pendingTasks = totalTasks - completedTasks
    val progressPercent = if (totalTasks > 0) completedTasks.toFloat() / totalTasks else 0.78f

    LazyColumn(
        modifier = modifier
            .testTag("dashboard_screen")
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- SEC 1: HOME METRIC PROGRESS ---
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth().testTag("hero_progress_card"),
                containerColor = BentoBgOverall,
                borderColor = BentoTextOverall
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1.2f)) {
                        Text(
                            text = "1. HOME DASHBOARD",
                            color = BentoTextOverall.copy(alpha = 0.8f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Daily Progress",
                            color = BentoTextOverall,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        TaskStatRow(label = "Today's Tasks", value = totalTasks.toString(), color = BentoTextOverall, textColor = BentoTextOverall)
                        TaskStatRow(label = "Completed", value = completedTasks.toString(), color = BentoTextOverall, textColor = BentoTextOverall)
                        TaskStatRow(label = "Pending", value = pendingTasks.toString(), color = NeonPink, textColor = BentoTextOverall)
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        GlowCircle(
                            percentage = progressPercent,
                            circleColorStart = BentoTextOverall,
                            circleColorEnd = NeonPink,
                            modifier = Modifier.size(130.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "${(progressPercent * 100).toInt()}%",
                                    color = BentoTextOverall,
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = "Done",
                                    color = BentoTextOverall.copy(alpha = 0.7f),
                                    fontSize = 10.sp,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // --- SEC 2: STREAK & XP SYSTEM ---
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth().testTag("streak_xp_card"),
                containerColor = BentoBgStreak,
                borderColor = BentoTextStreak
            ) {
                Column {
                    Text(
                        text = "7. STREAK & XP SYSTEM",
                        color = BentoTextStreak.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "Current Streak", color = BentoTextStreak.copy(alpha = 0.7f), fontSize = 12.sp)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "${levelState?.streakCount ?: 12} Days",
                                    color = BentoTextStreak,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = "Streak",
                                    tint = BentoTextStreak,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "Current Rank Title", color = BentoTextStreak.copy(alpha = 0.7f), fontSize = 12.sp)
                            Text(
                                text = "Warrior (Lvl ${levelState?.level ?: 7})",
                                color = BentoTextStreak,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "XP Progress: ${levelState?.xp ?: 2450} / 3000",
                        color = BentoTextStreak,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Glassy XP Progress Bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(BentoTextStreak.copy(alpha = 0.15f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(((levelState?.xp ?: 2450).toFloat() / 3000f).coerceIn(0f, 1f))
                                .height(10.dp)
                                .background(BentoTextStreak)
                        )
                    }
                }
            }
        }

        // --- SEC 3: ANALYTICS CHARTS ---
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth().testTag("analytics_card"),
                containerColor = BentoBgSleep,
                borderColor = BentoTextSleep
            ) {
                Column {
                    Text(
                        text = "8. ANALYTICS (WEEKLY OVERVIEW)",
                        color = BentoTextSleep.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = "Study & Learning Hours", color = BentoTextSleep, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))

                    // Custom Canvas Bar & Line Chart
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                            val hours = listOf(4.5f, 6.0f, 5.2f, 7.0f, 8.1f, 4.0f, 5.5f)
                            val maxHour = 10f
                            val widthInterval = size.width / 7
                            val barWidth = 14.dp.toPx()

                            // Draw bars
                            hours.forEachIndexed { index, value ->
                                val x = index * widthInterval + (widthInterval - barWidth) / 2
                                val barHeight = (value / maxHour) * (size.height - 40.dp.toPx())
                                val y = size.height - 30.dp.toPx() - barHeight

                                // Draw single neon bar
                                drawRoundRect(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(BentoTextSleep, BentoTextSleep.copy(alpha = 0.3f))
                                    ),
                                    topLeft = Offset(x, y),
                                    size = Size(barWidth, barHeight),
                                    cornerRadius = CornerRadius(4.dp.toPx())
                                )
                            }

                            // Draw Line representing Focus intensity
                            val linePoints = listOf(3.0f, 5.5f, 4.0f, 6.5f, 7.5f, 3.2f, 4.8f)
                            val path = Path()
                            linePoints.forEachIndexed { index, value ->
                                val x = index * widthInterval + widthInterval / 2
                                val pointHeight = (value / maxHour) * (size.height - 40.dp.toPx())
                                val y = size.height - 30.dp.toPx() - pointHeight
                                if (index == 0) {
                                    path.moveTo(x, y)
                                } else {
                                    path.lineTo(x, y)
                                }
                            }

                            drawPath(
                                path = path,
                                color = NeonPink,
                                style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                            )
                        }

                        // Labels below Canvas
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(horizontal = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                            days.forEach { day ->
                                Text(
                                    text = day,
                                    color = BentoTextSleep.copy(alpha = 0.7f),
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

        // --- SEC 4: NOTIFICATIONS BANNER ---
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth().testTag("notifications_card"),
                containerColor = BentoBgWater,
                borderColor = BentoTextWater
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "10. ALERTS & NOTIFICATIONS",
                            color = BentoTextWater,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = "Alerts",
                            tint = BentoTextWater,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    NotificationItem(title = "Study Reminder (UPSC)", time = "09:00 AM", isAlert = false, textColor = BentoTextWater)
                    NotificationItem(title = "Water Hydration Intake Check", time = "12:00 PM", isAlert = false, textColor = BentoTextWater)
                    NotificationItem(title = "Warning: Streak status critical!", time = "Immediate", isAlert = true, textColor = BentoTextWater)
                }
            }
        }
    }
}

@Composable
fun TaskStatRow(
    label: String,
    value: String,
    color: Color = SoftWhite,
    textColor: Color = SoftWhite
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = textColor.copy(alpha = 0.7f), fontSize = 12.sp)
        Text(text = value, color = color, fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun NotificationItem(
    title: String,
    time: String,
    isAlert: Boolean,
    textColor: Color = SoftWhite
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isAlert) NeonPink.copy(alpha = 0.2f) else textColor.copy(alpha = 0.08f))
            .padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(if (isAlert) NeonPink else textColor)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                color = if (isAlert) NeonPink else textColor,
                fontSize = 12.sp,
                fontWeight = if (isAlert) FontWeight.Bold else FontWeight.Medium
            )
        }
        Text(
            text = time,
            color = if (isAlert) NeonPink.copy(alpha = 0.8f) else textColor.copy(alpha = 0.6f),
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace
        )
    }
}
