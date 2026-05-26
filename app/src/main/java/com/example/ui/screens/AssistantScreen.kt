package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
fun AssistantScreen(
    viewModel: VayuViewModel,
    modifier: Modifier = Modifier
) {
    val messages by viewModel.chatMessages.collectAsState()
    val isGenerating by viewModel.isGeneratingResponse.collectAsState()
    val listState = rememberLazyListState()

    var inputMessage by remember { mutableStateOf("") }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = modifier
            .testTag("assistant_screen")
            .padding(horizontal = 16.dp)
    ) {
        // --- 1. NEURAL CORE HIGHLIGHT CARD (Lavender Bento) ---
        GlassCard(
            modifier = Modifier.fillMaxWidth().testTag("neural_core_card"),
            containerColor = BentoBgSleep,
            borderColor = BentoTextSleep
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlowCircle(
                    percentage = 0.95f,
                    circleColorStart = BentoTextSleep,
                    circleColorEnd = BentoTextSleep.copy(alpha = 0.5f),
                    modifier = Modifier.size(54.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(BentoTextSleep),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🤖", fontSize = 16.sp)
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "9. VAYU AI ASSISTANT",
                        color = BentoTextSleep,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(text = "Online. Ready to aid.", color = BentoTextSleep.copy(alpha = 0.7f), fontSize = 11.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                }

                Icon(
                    Icons.Default.Refresh,
                    contentDescription = "Clear Session",
                    tint = BentoTextSleep,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { viewModel.clearChat() }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // --- 2. QUICK ACTION SUGGEST CHIPS (Amber Bento) ---
        Text(text = "SUGGESTED DISCUSSIONS:", color = BentoTextFocus, fontSize = 10.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 0.5.sp)
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            SuggestedChip(text = "UPSC Polity Plan", modifier = Modifier.weight(1f)) {
                viewModel.askQuestion("Draft me a quick Polity revision schedule.")
            }
            SuggestedChip(text = "UPSC Geo MCQs", modifier = Modifier.weight(1f)) {
                viewModel.askQuestion("Generate 3 Geography MCQs for self-assessment.")
            }
            SuggestedChip(text = "Python DSA Roadmap", modifier = Modifier.weight(1f)) {
                viewModel.askQuestion("Draft a quick Python & DSA focus schedule.")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // --- 3. CONVERSATIONAL STREAM THREAD ---
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF7F5FC))
                .border(1.dp, BentoTextSleep.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages) { msg ->
                ChatBubble(msg = msg)
            }

            if (isGenerating) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            color = BentoTextSleep,
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Synthesizing AI context...", color = BentoTextSleep, fontSize = 12.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // --- 4. TEXT ENTRY AREA ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputMessage,
                onValueChange = { inputMessage = it },
                placeholder = { Text("Command Vayu...", color = BentoTextSleep.copy(alpha = 0.6f), fontSize = 13.sp) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = BentoTextSleep,
                    unfocusedTextColor = BentoTextSleep,
                    focusedBorderColor = BentoTextSleep,
                    unfocusedBorderColor = BentoTextSleep.copy(alpha = 0.3f),
                    focusedLabelColor = BentoTextSleep
                ),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .weight(1f)
                    .testTag("chat_input_field"),
                singleLine = true
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(BentoTextSleep)
                    .clickable {
                        if (inputMessage.isNotBlank()) {
                            viewModel.askQuestion(inputMessage)
                            inputMessage = ""
                        }
                    }
                    .testTag("send_chat_button"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Transmit",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun SuggestedChip(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(BentoBgFocus)
            .border(1.dp, BentoTextFocus.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = BentoTextFocus,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
    }
}

@Composable
fun ChatBubble(msg: com.example.data.AssistantChatMessage) {
    val bubbleColor = if (msg.isUser) BentoBgWater else BentoBgSleep
    val textColor = if (msg.isUser) BentoTextWater else BentoTextSleep
    val accentColor = if (msg.isUser) BentoTextWater else BentoTextSleep
    val alignment = if (msg.isUser) Alignment.End else Alignment.Start

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = alignment) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (msg.isUser) 16.dp else 4.dp,
                        bottomEnd = if (msg.isUser) 4.dp else 16.dp
                    )
                )
                .background(bubbleColor)
                .border(
                    1.dp,
                    accentColor.copy(alpha = 0.15f),
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (msg.isUser) 16.dp else 4.dp,
                        bottomEnd = if (msg.isUser) 4.dp else 16.dp
                    )
                )
                .padding(12.dp)
        ) {
            Column {
                Text(
                    text = if (msg.isUser) "WARRIOR LOG" else "VAYU ASSIST",
                    color = accentColor.copy(alpha = 0.7f),
                    fontSize = 9.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp,
                    fontFamily = FontFamily.Monospace
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = msg.message,
                    color = textColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 16.sp
                )
            }
        }
    }
}
