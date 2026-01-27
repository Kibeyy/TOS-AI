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
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.tosai.data.local.Severity
import com.example.tosai.presentation.viewmodels.TosViewModel

class ProfileScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: TosViewModel = hiltViewModel()
        val allAnalyses by viewModel.allAnalyses.collectAsState(initial = emptyList())
        val navigator = LocalNavigator.current
        // Calculate stats from real data
        val totalScans = allAnalyses.size
        val highRiskCount = allAnalyses.count { it.riskPercentage >= 70 }
        val safeCount = allAnalyses.count { it.riskPercentage < 30 }
        val status = when {
            allAnalyses.isEmpty() -> "New User"
            highRiskCount > safeCount -> "Caution"
            else -> "Safe"
        }

        val bgGradient = Brush.linearGradient(
            colors = listOf(Color(0xFF0A2463), Color(0xFF2E5CB8), Color(0xFF7F4DFF))
        )

        Scaffold { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(bgGradient)
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(20.dp))
                    Text("My Identity", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

                    Spacer(Modifier.height(30.dp))

                    // --- THE GLASS ID CARD ---
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(Color.White.copy(alpha = 0.1f))
                            .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(30.dp))
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Filled.Person, null, tint = Color(0xFF0A2463), modifier = Modifier.size(40.dp))
                            }
                            Spacer(Modifier.height(16.dp))
                            Text("Collins Kibe", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            Text("Pro Member", color = Color(0xFF69F0AE), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(Modifier.height(30.dp))

                    // --- FLOATING STATS (Real Data) ---
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        ProfileStat("$totalScans", "Scans")
                        ProfileStat("$safeCount", "Safe")
                        ProfileStat(status, "Status")
                    }

                    Spacer(Modifier.height(40.dp))

                    // --- SUBSCRIPTION CARD ---
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF6A1B9A), // Deep Purple
                                        Color(0xFF8E24AA)  // Lighter Purple
                                    )
                                )
                            )
                            .clickable { /* Navigate to subscription screen */ }
                            .padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    "Upgrade to Premium",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    "Unlimited scans • Priority support",
                                    color = Color.White.copy(alpha = 0.8f),
                                    fontSize = 12.sp
                                )
                            }
                            Icon(
                                Icons.AutoMirrored.Outlined.ArrowForward,
                                null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(Modifier.height(30.dp))


                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        GlassMenuItem(Icons.Outlined.Settings, "Preferences", onClick = {navigator?.push(PreferencesScreen())})
                        GlassMenuItem(Icons.Outlined.Security, "Security", onClick = {navigator?.push(SecurityScreen())})
                        GlassMenuItem(Icons.Outlined.Notifications, "Notifications", onClick = {navigator?.push(NotificationsScreen())})
                        GlassMenuItem(Icons.Outlined.HelpOutline, "Support", onClick = {navigator?.push(SupportScreen())})

                        Spacer(Modifier.height(10.dp))

                        // Logout
                        GlassMenuItem(
                            icon = Icons.AutoMirrored.Outlined.Logout,
                            text = "Log Out",
                            textColor = Color(0xFFFF8A80) // Light Red
                        )
                    }

                    Spacer(Modifier.height(50.dp))
                }
            }
        }
    }
}

@Composable
fun ProfileStat(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text(label, fontSize = 12.sp, color = Color.White.copy(alpha = 0.6f))
    }
}

@Composable
fun GlassMenuItem(
    icon: ImageVector,
    text: String,
    textColor: Color = Color.White,
    onClick: () -> Unit = {} // 1. Added parameter
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .clickable(onClick = onClick) // 2. Bind the parameter here
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = textColor.copy(alpha = 0.8f))
        Spacer(Modifier.width(16.dp))
        Text(text, fontSize = 16.sp, color = textColor, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.weight(1f))
        if(textColor == Color.White) {
            Icon(Icons.AutoMirrored.Outlined.ArrowForward, null, tint = Color.White.copy(alpha = 0.3f), modifier = Modifier.size(16.dp))
        }
    }
}