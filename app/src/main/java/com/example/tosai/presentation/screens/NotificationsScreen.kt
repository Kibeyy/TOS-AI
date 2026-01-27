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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

class NotificationsScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var analysisComplete by remember { mutableStateOf(true) }
        var highRiskDetected by remember { mutableStateOf(true) }
        var weeklyReport by remember { mutableStateOf(false) }
        var tipsAndTricks by remember { mutableStateOf(true) }

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
                        text = "Notifications",
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

                    // Analysis Notifications
                    SectionHeader("Analysis Alerts")
                    Spacer(Modifier.height(12.dp))

                    PreferenceToggle(
                        title = "Analysis Complete",
                        description = "Get notified when scan finishes",
                        checked = analysisComplete,
                        onCheckedChange = { analysisComplete = it }
                    )

                    Spacer(Modifier.height(12.dp))

                    PreferenceToggle(
                        title = "High Risk Detected",
                        description = "Alert for contracts with 70%+ risk",
                        checked = highRiskDetected,
                        onCheckedChange = { highRiskDetected = it }
                    )

                    Spacer(Modifier.height(30.dp))

                    // Reports
                    SectionHeader("Reports")
                    Spacer(Modifier.height(12.dp))

                    PreferenceToggle(
                        title = "Weekly Summary",
                        description = "Get weekly analysis reports",
                        checked = weeklyReport,
                        onCheckedChange = { weeklyReport = it }
                    )

                    Spacer(Modifier.height(30.dp))

                    // Other
                    SectionHeader("Other")
                    Spacer(Modifier.height(12.dp))

                    PreferenceToggle(
                        title = "Tips & Tricks",
                        description = "Learn how to use TOS AI better",
                        checked = tipsAndTricks,
                        onCheckedChange = { tipsAndTricks = it }
                    )

                    Spacer(Modifier.height(50.dp))
                }
            }
        }
    }
}