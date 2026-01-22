package com.example.tosai.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.tosai.data.local.ContractAnalysis
import com.example.tosai.data.local.Severity
import com.example.tosai.presentation.components.user_status_tags.GlassCard
import com.example.tosai.presentation.components.user_status_tags.TosAnalysisCard
import com.example.tosai.presentation.viewmodels.TosViewModel

class MainScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: TosViewModel = hiltViewModel()

        // Get real data from database
        val allAnalyses by viewModel.allAnalyses.collectAsState(initial = emptyList())

        // Take only the 5 most recent
        val recentAnalyses = allAnalyses.take(5)

        val bgGradient = Brush.linearGradient(
            colors = listOf(
                Color(0xFF0A2463),
                Color(0xFF2E5CB8),
                Color(0xFF7F4DFF)
            )
        )

        Scaffold { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(bgGradient)
                    .padding(paddingValues)
            ) {

                Box(
                    modifier = Modifier
                        .offset(x = 200.dp, y = (-100).dp)
                        .size(300.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.05f))
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 24.dp, end = 24.dp, top = 20.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    Spacer(modifier = Modifier.height(20.dp))

                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Good Afternoon,",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 16.sp
                            )
                            Text(
                                text = "Collins",
                                color = Color.White,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .border(2.dp, Color.White.copy(alpha = 0.5f), CircleShape)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f))
                                .clickable { navigator?.push(ProfileScreen()) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("CK", fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    // Status Card - Dynamic based on recent analyses
                    val averageRisk = if (recentAnalyses.isNotEmpty()) {
                        recentAnalyses.map { it.riskPercentage }.average().toInt()
                    } else 0

                    val (statusText, statusSubtext, statusColor) = when {
                        averageRisk < 30 -> Triple("You are Safe", "Low risk detected", Color(0xFF4CAF50))
                        averageRisk < 70 -> Triple("Moderate Risk", "Review your contracts", Color(0xFFFFC107))
                        else -> Triple("High Risk", "Action required", Color(0xFFFF5252))
                    }

                    GlassCard(height = 100.dp) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(statusColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Outlined.Shield, null, tint = Color.White)
                            }
                            Spacer(Modifier.width(16.dp))
                            Column {
                                Text(
                                    statusText,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Text(
                                    statusSubtext,
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    // Action Buttons
                    Text(
                        "Start Analyzing",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Row(modifier = Modifier.fillMaxWidth().height(160.dp)) {

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(24.dp))
                                .background(Color.White)
                                .clickable { navigator?.push(ScanScreen()) }
                                .padding(20.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF0A2463)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Filled.Add, null, tint = Color.White)
                                }
                                Text(
                                    "New\nScan",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0A2463),
                                    lineHeight = 26.sp
                                )
                            }
                        }

                        Spacer(Modifier.width(16.dp))

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        ) {
                            GlassCard(
                                modifier = Modifier.weight(1f).clickable { navigator?.push(PasteTextScreen()) },
                                color = Color.White.copy(alpha = 0.15f)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(Icons.Filled.AutoAwesome, null, tint = Color.White)
                                    Spacer(Modifier.width(8.dp))
                                    Text("Paste Text", color = Color.White, fontWeight = FontWeight.SemiBold)
                                }
                            }

                            Spacer(Modifier.height(16.dp))

                            GlassCard(
                                modifier = Modifier.weight(1f).clickable { navigator?.push(HistoryScreen()) },
                                color = Color.White.copy(alpha = 0.15f)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(Icons.Outlined.History, null, tint = Color.White)
                                    Spacer(Modifier.width(8.dp))
                                    Text("History", color = Color.White, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    // Recent Scans Section
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Recent Analysis", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Icon(Icons.Outlined.Description, null, tint = Color.White.copy(alpha = 0.6f))
                    }

                    Spacer(Modifier.height(16.dp))

                    // Real data from database
                    if (recentAnalyses.isEmpty()) {
                        // Empty state
                        GlassCard(height = 180.dp) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    "No analyses yet",
                                    color = Color.White.copy(alpha = 0.6f),
                                    fontSize = 14.sp
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    "Start scanning to see results here",
                                    color = Color.White.copy(alpha = 0.4f),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    } else {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(recentAnalyses) { analysis ->
                                TosAnalysisCard(
                                    analysis = analysis,
                                    onClick = {
                                        navigator?.push(AnalysisResultScreen(analysis = analysis))
                                    }
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(100.dp))
                }
            }
        }
    }
}




