package com.example.tosai.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
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
import com.example.tosai.presentation.components.user_status_tags.ScanOptions

class ScanScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        // SAME GRADIENT (Keep consistent!)
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
                // Background Decorations
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(300.dp)
                        .border(1.dp, Color.White.copy(alpha = 0.05f), CircleShape)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(450.dp)
                        .border(1.dp, Color.White.copy(alpha = 0.03f), CircleShape)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(40.dp))

                    Text(
                        "Input Source",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 16.sp
                    )
                    Text(
                        "Start Analysis",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.weight(1f))

                    // --- THE CENTER PIECE (Pulsing Button) ---
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.1f)) // Outer Glow
                            .clickable { /* Launch Camera */ }
                            .padding(20.dp), // Spacing for inner circle
                        contentAlignment = Alignment.Center
                    ) {
                        // Inner Solid Circle
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CameraAlt,
                                contentDescription = null,
                                tint = Color(0xFF0A2463),
                                modifier = Modifier.size(60.dp)
                            )
                        }
                    }

                    Spacer(Modifier.height(20.dp))
                    Text("Tap to Scan", color = Color.White, fontWeight = FontWeight.Bold)

                    Spacer(Modifier.weight(1f))

                    // --- SATELLITE OPTIONS (Glass Row) ---
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ScanOptions(Icons.Outlined.Description, "Upload") { /* Upload */ }
                        ScanOptions(Icons.Outlined.TextFields, "Paste") { navigator?.push(PasteTextScreen()) }
                        ScanOptions(Icons.Outlined.Link, "Link") { /* Link */ }
                    }

                    Spacer(Modifier.height(40.dp))
                }
            }
        }
    }
}

