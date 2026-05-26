package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.VayuViewModel
import com.example.ui.components.GlassCard
import com.example.ui.theme.*

@Composable
fun PlannerScreen(
    viewModel: VayuViewModel,
    modifier: Modifier = Modifier
) {
    val tasks by viewModel.allTasks.collectAsState()
    val subjects by viewModel.allSubjectProgress.collectAsState()
    val codingSkills by viewModel.allCodingProgress.collectAsState()
    val levelState by viewModel.levelState.collectAsState()

    var showAddTaskDialog by remember { mutableStateOf(false) }
    var selectedSubjectToEdit by remember { mutableStateOf<String?>(null) }
    var selectedCodingSkillToEdit by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = modifier
            .testTag("planner_screen")
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- 1. DAILY TIMETABLE CARD ---
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth().testTag("timetable_card"),
                containerColor = BentoBgSteps,
                borderColor = BentoTextSteps
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "2. DAILY SCHEDULER",
                            color = BentoTextSteps.copy(alpha = 0.8f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(BentoTextSteps.copy(alpha = 0.15f))
                                .clickable { showAddTaskDialog = true }
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add Task", tint = BentoTextSteps, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(3.dp))
                            Text("New", color = BentoTextSteps, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    if (tasks.isEmpty()) {
                        Text("No scheduled exercises. Add a dynamic task slot above to initialize.", color = BentoTextSteps.copy(alpha = 0.7f), fontSize = 12.sp)
                    } else {
                        tasks.forEach { task ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(BentoTextSteps.copy(alpha = 0.08f))
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                    Checkbox(
                                        checked = task.completed,
                                        onCheckedChange = { viewModel.toggleTask(task.id, it) },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = BentoTextSteps,
                                            uncheckedColor = BentoTextSteps.copy(alpha = 0.4f),
                                            checkmarkColor = Color.White
                                        ),
                                        modifier = Modifier.testTag("task_checkbox_${task.id}")
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Column {
                                        Text(
                                            text = task.time,
                                            color = BentoTextSteps,
                                            fontSize = 11.sp,
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = task.title,
                                            color = if (task.completed) BentoTextSteps.copy(alpha = 0.5f) else BentoTextSteps,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete Task",
                                    tint = BentoTextSteps.copy(alpha = 0.6f),
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clickable { viewModel.deleteTask(task.id) }
                                )
                            }
                        }
                    }
                }
            }
        }

        // --- 2. UPSC TRACKER ---
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth().testTag("upsc_tracker_card"),
                containerColor = BentoBgSleep,
                borderColor = BentoTextSleep
            ) {
                Column {
                    Text(
                        text = "3. UPSC ACADEMIC TRACKER",
                        color = BentoTextSleep.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(text = "Tap a subject to modify its database value", color = BentoTextSleep.copy(alpha = 0.7f), fontSize = 10.sp)
                    Spacer(modifier = Modifier.height(12.dp))

                    subjects.forEach { subject ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedSubjectToEdit = if (selectedSubjectToEdit == subject.name) null else subject.name }
                                .padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "🛡️ ${subject.name}", color = BentoTextSleep, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text(text = "${subject.progressPercent}%", color = BentoTextSleep, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            // Progress trace background
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(BentoTextSleep.copy(alpha = 0.15f))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(subject.progressPercent / 100f)
                                        .height(6.dp)
                                        .background(BentoTextSleep)
                                )
                            }

                            AnimatedVisibility(visible = selectedSubjectToEdit == subject.name) {
                                Column(modifier = Modifier.padding(top = 8.dp)) {
                                    Text("Adjust progress level:", color = BentoTextSleep.copy(alpha = 0.7f), fontSize = 11.sp)
                                    Slider(
                                        value = subject.progressPercent.toFloat(),
                                        onValueChange = { viewModel.updateSubjectProgress(subject.name, it.toInt()) },
                                        valueRange = 0f..100f,
                                        colors = SliderDefaults.colors(
                                            thumbColor = BentoTextSleep,
                                            activeTrackColor = BentoTextSleep,
                                            inactiveTrackColor = BentoTextSleep.copy(alpha = 0.15f)
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // --- 3. CODING TRACKER ---
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth().testTag("coding_tracker_card"),
                containerColor = BentoBgFocus,
                borderColor = BentoTextFocus
            ) {
                Column {
                    Text(
                        text = "4. CODING & AI SKILLS",
                        color = BentoTextFocus.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(text = "Tap a technology to adjust efficiency level", color = BentoTextFocus.copy(alpha = 0.7f), fontSize = 10.sp)
                    Spacer(modifier = Modifier.height(12.dp))

                    codingSkills.forEach { skill ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedCodingSkillToEdit = if (selectedCodingSkillToEdit == skill.name) null else skill.name }
                                .padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "⚡ ${skill.name}", color = BentoTextFocus, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text(text = "${skill.progressPercent}%", color = BentoTextFocus, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(BentoTextFocus.copy(alpha = 0.15f))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(skill.progressPercent / 100f)
                                        .height(6.dp)
                                        .background(BentoTextFocus)
                                )
                            }

                            AnimatedVisibility(visible = selectedCodingSkillToEdit == skill.name) {
                                Column(modifier = Modifier.padding(top = 8.dp)) {
                                    Text("Adjust skill efficiency level:", color = BentoTextFocus.copy(alpha = 0.7f), fontSize = 11.sp)
                                    Slider(
                                        value = skill.progressPercent.toFloat(),
                                        onValueChange = { viewModel.updateCodingProgress(skill.name, it.toInt()) },
                                        valueRange = 0f..100f,
                                        colors = SliderDefaults.colors(
                                            thumbColor = BentoTextFocus,
                                            activeTrackColor = BentoTextFocus,
                                            inactiveTrackColor = BentoTextFocus.copy(alpha = 0.15f)
                                        )
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Coding Streak", color = BentoTextFocus.copy(alpha = 0.7f), fontSize = 10.sp)
                            Text("${levelState?.codingStreakCount ?: 15} Days", color = BentoTextFocus, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("LeetCode Index", color = BentoTextFocus.copy(alpha = 0.7f), fontSize = 10.sp)
                            Text("${levelState?.leetcodeSolvedCount ?: 1530} Solves", color = BentoTextFocus, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                        }
                    }
                }
            }
        }
    }

    // --- ADD TASK DIALOG DISPLAY ---
    if (showAddTaskDialog) {
        var taskTitle by remember { mutableStateOf("") }
        var taskTime by remember { mutableStateOf("09:00 AM") }
        var taskCategory by remember { mutableStateOf("Study") }

        AlertDialog(
            onDismissRequest = { showAddTaskDialog = false },
            containerColor = DarkNeutral,
            shape = RoundedCornerShape(16.dp),
            title = {
                Text(text = "Initialize New Schedule Slot", color = NeonCyan, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = taskTitle,
                        onValueChange = { taskTitle = it },
                        label = { Text("Task Title") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = SoftWhite,
                            unfocusedTextColor = SoftWhite,
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = CoolGrey,
                            focusedLabelColor = NeonCyan
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("add_task_title")
                    )

                    OutlinedTextField(
                        value = taskTime,
                        onValueChange = { taskTime = it },
                        label = { Text("Time Slot (e.g., 9:00 AM)") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = SoftWhite,
                            unfocusedTextColor = SoftWhite,
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = CoolGrey,
                            focusedLabelColor = NeonCyan
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("add_task_time")
                    )

                    OutlinedTextField(
                        value = taskCategory,
                        onValueChange = { taskCategory = it },
                        label = { Text("Category (Study, Workout, General)") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = SoftWhite,
                            unfocusedTextColor = SoftWhite,
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = CoolGrey,
                            focusedLabelColor = NeonCyan
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("add_task_category")
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (taskTitle.isNotBlank()) {
                            viewModel.addTask(taskTime, taskTitle, taskCategory)
                        }
                        showAddTaskDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonCyan)
                ) {
                    Text("Assemble", color = DarkNeutral, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                Button(
                    onClick = { showAddTaskDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Text("Cancel", color = SoftWhite)
                }
            }
        )
    }
}
