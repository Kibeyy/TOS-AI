package com.example.tosai.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

class SupportScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        val bgGradient = Brush.linearGradient(
            colors = listOf(Color(0xFF0A2463), Color(0xFF2E5CB8), Color(0xFF7F4DFF))
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp, start = 20.dp, end = 20.dp, bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.1f))
                            .clickable { navigator?.pop() }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "Support",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(bgGradient)
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(20.dp))

                    // Help Card
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF0277BD),
                                        Color(0xFF0288D1)
                                    )
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Column {
                            Text(
                                "Need Help?",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "We're here to assist you 24/7",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(Modifier.height(30.dp))

                    // Contact Methods
                    SectionHeader("Get in Touch")
                    Spacer(Modifier.height(12.dp))

                    SupportOption(
                        icon = Icons.Outlined.Email,
                        title = "Email Support",
                        description = "support@tosai.com",
                        onClick = { /* Open email */ }
                    )

                    Spacer(Modifier.height(12.dp))

                    SupportOption(
                        icon = Icons.Outlined.ChatBubbleOutline,
                        title = "Live Chat",
                        description = "Chat with our team",
                        onClick = { /* Open chat */ }
                    )

                    Spacer(Modifier.height(12.dp))

                    SupportOption(
                        icon = Icons.Outlined.Phone,
                        title = "Call Us",
                        description = "+1 (555) 123-4567",
                        onClick = { /* Dial phone */ }
                    )

                    Spacer(Modifier.height(30.dp))

                    // Resources
                    SectionHeader("Resources")
                    Spacer(Modifier.height(12.dp))

                    SupportOption(
                        icon = Icons.Outlined.Book,
                        title = "User Guide",
                        description = "Learn how to use TOS AI",
                        onClick = { /* Open guide */ }
                    )

                    Spacer(Modifier.height(12.dp))

                    SupportOption(
                        icon = Icons.Outlined.QuestionAnswer,
                        title = "FAQ",
                        description = "Frequently asked questions",
                        onClick = { /* Open FAQ */ }
                    )

                    Spacer(Modifier.height(12.dp))

                    SupportOption(
                        icon = Icons.Outlined.BugReport,
                        title = "Report a Bug",
                        description = "Help us improve",
                        onClick = { /* Open bug report */ }
                    )

                    Spacer(Modifier.height(50.dp))
                }
            }
        }
    }
}

@Composable
fun SupportOption(
    icon: ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                null,
                tint = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = description,
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 13.sp
                )
            }
        }
    }
}