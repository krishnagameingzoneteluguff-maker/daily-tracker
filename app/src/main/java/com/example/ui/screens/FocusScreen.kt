package com.example.ui.screens

import androidx.compose.foundation.border
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.VayuViewModel
import com.example.ui.components.GlassCard
import com.example.ui.components.GlowCircle
import com.example.ui.theme.*

@Composable
fun FocusScreen(
    viewModel: VayuViewModel,
    modifier: Modifier = Modifier
) {
    val secondsLeft by viewModel.timerSecondsLeft.collectAsState()
    val isRunning by viewModel.timerIsRunning.collectAsState()
    val presetType by viewModel.timerPresetType.collectAsState()
    val selectedSound by viewModel.selectedAmbientSound.collectAsState()

    val minutes = secondsLeft / 60
    val seconds = secondsLeft % 60
    val timeFormatted = String.format("%02d:%02d", minutes, seconds)

    val maxSeconds = when (presetType) {
        "Pomodoro" -> 1500
        "Deep Work" -> 3000
        else -> 600
    }
    val progressPercent = if (maxSeconds > 0) secondsLeft.toFloat() / maxSeconds else 1f

    val infiniteTransition = rememberInfiniteTransition(label = "pulse_trans")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 850),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    Column(
        modifier = modifier
            .testTag("focus_screen")
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- 1. CORE TIMER CARD (Amber Bento) ---
        GlassCard(
            modifier = Modifier.fillMaxWidth().testTag("focus_timer_card"),
            containerColor = BentoBgFocus,
            borderColor = BentoTextFocus
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "5. FOCUS MODE CORE",
                    color = BentoTextFocus.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = if (isRunning) Modifier.alpha(pulseAlpha) else Modifier,
                    contentAlignment = Alignment.Center
                ) {
                    GlowCircle(
                        percentage = progressPercent,
                        circleColorStart = BentoTextFocus,
                        circleColorEnd = NeonPink,
                        modifier = Modifier.size(220.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = timeFormatted,
                                color = BentoTextFocus,
                                fontSize = 44.sp,
                                fontWeight = FontWeight.Black,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = 2.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "FOCUS TIME",
                                color = BentoTextFocus.copy(alpha = 0.7f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Control Toggles
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { if (isRunning) viewModel.pauseTimer() else viewModel.startTimer() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isRunning) NeonPink else BentoTextFocus
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1.2f)
                            .testTag("play_pause_button")
                    ) {
                        Text(
                            text = if (isRunning) "PAUSE FOCUS" else "START FOCUS",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = { viewModel.resetTimer() },
                        colors = ButtonDefaults.buttonColors(containerColor = BentoTextFocus.copy(alpha = 0.15f)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(0.8f)
                            .testTag("reset_timer_button")
                    ) {
                        Text("RESET", color = BentoTextFocus, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }

        // --- 2. PRESET WIDGET CARD (Mint Bento) ---
        GlassCard(
            modifier = Modifier.fillMaxWidth().testTag("focus_presets_card"),
            containerColor = BentoBgStreak,
            borderColor = BentoTextStreak
        ) {
            Column {
                Text(
                    text = "FOCUS MODE PRESETS",
                    color = BentoTextStreak,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PresetButton(
                        label = "Pomodoro",
                        sub = "25/5 min",
                        isSelected = presetType == "Pomodoro",
                        activeColor = BentoTextStreak,
                        textColor = BentoBgStreak,
                        modifier = Modifier.weight(1f).clickable { viewModel.selectPreset("Pomodoro") }
                    )
                    PresetButton(
                        label = "Deep Work",
                        sub = "50/10 min",
                        isSelected = presetType == "Deep Work",
                        activeColor = BentoTextStreak,
                        textColor = BentoBgStreak,
                        modifier = Modifier.weight(1f).clickable { viewModel.selectPreset("Deep Work") }
                    )
                    PresetButton(
                        label = "Custom Focus",
                        sub = "10 min",
                        isSelected = presetType == "Custom",
                        activeColor = BentoTextStreak,
                        textColor = BentoBgStreak,
                        modifier = Modifier.weight(1f).clickable { viewModel.selectPreset("Custom") }
                    )
                }
            }
        }

        // --- 3. AMBIENT AUDIOS CARD (Lavender Bento) ---
        GlassCard(
            modifier = Modifier.fillMaxWidth().testTag("ambient_audio_card"),
            containerColor = BentoBgSleep,
            borderColor = BentoTextSleep
        ) {
            Column {
                Text(
                    text = "SELECT RE-TRIGGERING WHITE SOUNDS",
                    color = BentoTextSleep,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    SoundChip(label = "🌌 No Sound", isSelected = selectedSound == "None", activeColor = BentoTextSleep, parentBg = BentoBgSleep) { viewModel.selectAmbientSound("None") }
                    SoundChip(label = "🧠 Study Wave", isSelected = selectedSound == "Study", activeColor = BentoTextSleep, parentBg = BentoBgSleep) { viewModel.selectAmbientSound("Study") }
                    SoundChip(label = "⚡ Coding Beats", isSelected = selectedSound == "Coding", activeColor = BentoTextSleep, parentBg = BentoBgSleep) { viewModel.selectAmbientSound("Coding") }
                }
            }
        }

        // --- 4. MOTIVATIONAL QUOTE CONTAINER (Blue Bento) ---
        GlassCard(
            modifier = Modifier.fillMaxWidth().testTag("motivational_quote"),
            containerColor = BentoBgSteps,
            borderColor = BentoTextSteps
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Info,
                    contentDescription = "Quote",
                    tint = BentoTextSteps,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "\"Knowledge is power, but focus and routine make that power real.\"",
                    color = BentoTextSteps,
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun PresetButton(
    label: String,
    sub: String,
    isSelected: Boolean,
    activeColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) activeColor else activeColor.copy(alpha = 0.08f))
            .border(
                1.dp,
                if (isSelected) activeColor else activeColor.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label,
                color = if (isSelected) textColor else activeColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(text = sub, color = if (isSelected) textColor.copy(alpha = 0.8f) else activeColor.copy(alpha = 0.7f), fontSize = 9.sp)
        }
    }
}

@Composable
fun SoundChip(
    label: String,
    isSelected: Boolean,
    activeColor: Color,
    parentBg: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) activeColor else activeColor.copy(alpha = 0.08f))
            .border(1.dp, if (isSelected) activeColor else activeColor.copy(alpha = 0.2f), CircleShape)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            color = if (isSelected) parentBg else activeColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
