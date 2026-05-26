package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.VayuViewModel
import com.example.ui.components.GlassCard
import com.example.ui.components.GlowCircle
import com.example.ui.theme.*

@Composable
fun ProfileScreen(
    viewModel: VayuViewModel,
    modifier: Modifier = Modifier
) {
    val levelState by viewModel.levelState.collectAsState()

    var showEditDialog by remember { mutableStateOf(false) }
    var targetGoalText by remember { mutableStateOf("UPSC Rank 1") }
    var userRankText by remember { mutableStateOf("#1287") }

    Column(
        modifier = modifier
            .testTag("profile_screen")
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- 1. BIOMETRIC CORE CARD (Blue Bento) ---
        GlassCard(
            modifier = Modifier.fillMaxWidth().testTag("profile_core_card"),
            containerColor = BentoBgSteps,
            borderColor = BentoTextSteps
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "11. USER PROFILE INTEGRATION",
                    color = BentoTextSteps.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlowCircle(
                        percentage = 0.85f,
                        circleColorStart = BentoTextSteps,
                        circleColorEnd = BentoTextSteps.copy(alpha = 0.4f),
                        modifier = Modifier.size(80.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(BentoTextSteps),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🦸", fontSize = 28.sp)
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = "Vayu Warrior",
                            color = BentoTextSteps,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "Elite Level ${levelState?.level ?: 7}",
                            color = BentoTextSteps,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "Global Ledger index: $userRankText",
                            color = BentoTextSteps.copy(alpha = 0.7f),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Stats summaries
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ProfileIndicator(label = "Primary Goal", value = targetGoalText, color = BentoTextSteps)
                    ProfileIndicator(label = "Achievements", value = "24 Earned", color = BentoTextSteps)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { showEditDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = BentoTextSteps.copy(alpha = 0.15f)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("edit_profile_button")
                ) {
                    Text("MODIFY PROFILE CRITERIA", color = BentoTextSteps, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                }
            }
        }

        // --- 2. THE EARNED ACHIEVEMENTS (Coral/Water Bento) ---
        GlassCard(
            modifier = Modifier.fillMaxWidth().testTag("achievements_card"),
            containerColor = BentoBgWater,
            borderColor = BentoTextWater
        ) {
            Column {
                Text(
                    text = "DISCIPLINE ACCOMPLISHMENTS",
                    color = BentoTextWater,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                AchievementBadgeRow(title = "⏰ Early Bird Protocol", desc = "Wake up at 5:00 AM before dawn.", unlocked = true, color = BentoTextWater)
                AchievementBadgeRow(title = "🔥 Streak Architect", desc = "Maintain daily discipline for 12+ days.", unlocked = true, color = BentoTextWater)
                AchievementBadgeRow(title = "🛡️ UPSC Sage", desc = "Reach 75%+ study completion on Polity.", unlocked = true, color = BentoTextWater)
                AchievementBadgeRow(title = "💻 Infinite Loops", desc = "Solve 1500+ LeetCode problems.", unlocked = true, color = BentoTextWater)
            }
        }

        // --- 3. SYSTEM SPEC FILE (Green Bento) ---
        GlassCard(
            modifier = Modifier.fillMaxWidth().testTag("tech_spec_card"),
            containerColor = BentoBgStreak,
            borderColor = BentoTextStreak
        ) {
            Column {
                Text(
                    text = "12. SPECIFICATIONS SHEET",
                    color = BentoTextStreak.copy(alpha = 0.8f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                SpecItem(prop = "Visual Palette", desc = "Pastel Mosaic Bento Grid", color = BentoTextStreak)
                SpecItem(prop = "Database core", desc = "Room Database Reactive Engine", color = BentoTextStreak)
                SpecItem(prop = "Neural Agent", desc = "Gemini AI Neural Processor", color = BentoTextStreak)
            }
        }
    }

    if (showEditDialog) {
        var tempGoal by remember { mutableStateOf(targetGoalText) }
        var tempRank by remember { mutableStateOf(userRankText) }

        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showEditDialog = false },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp),
            title = {
                Text("Modify Profile Directives", color = BentoTextSteps, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = tempGoal,
                        onValueChange = { tempGoal = it },
                        label = { Text("Primary Target Goal") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = BentoTextSteps,
                            unfocusedTextColor = BentoTextSteps,
                            focusedBorderColor = BentoTextSteps,
                            unfocusedBorderColor = BentoTextSteps.copy(alpha = 0.3f),
                            focusedLabelColor = BentoTextSteps
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("edit_goal_field")
                    )

                    OutlinedTextField(
                        value = tempRank,
                        onValueChange = { tempRank = it },
                        label = { Text("Global Rank Index") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = BentoTextSteps,
                            unfocusedTextColor = BentoTextSteps,
                            focusedBorderColor = BentoTextSteps,
                            unfocusedBorderColor = BentoTextSteps.copy(alpha = 0.3f),
                            focusedLabelColor = BentoTextSteps
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("edit_rank_field")
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        targetGoalText = tempGoal
                        userRankText = tempRank
                        showEditDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BentoTextSteps)
                ) {
                    Text("Apply Changes", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                Button(
                    onClick = { showEditDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Text("Cancel", color = BentoTextSteps)
                }
            }
        )
    }
}

@Composable
fun ProfileIndicator(
    label: String,
    value: String,
    color: Color
) {
    Column {
        Text(text = label, color = color.copy(alpha = 0.7f), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        Text(text = value, color = color, fontSize = 14.sp, fontWeight = FontWeight.Black)
    }
}

@Composable
fun AchievementBadgeRow(
    title: String,
    desc: String,
    unlocked: Boolean,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.08f))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(if (unlocked) color else color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Star,
                contentDescription = "Medal",
                tint = if (unlocked) Color.White else color.copy(alpha = 0.7f),
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = title, color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(text = desc, color = color.copy(alpha = 0.7f), fontSize = 10.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun SpecItem(prop: String, desc: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = prop, color = color.copy(alpha = 0.7f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Text(text = desc, color = color, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
    }
}
