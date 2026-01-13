package com.example.tosai.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.TextFields
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

class ScanScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        // Define your brand colors
        val darkBlue = Color(0xFF0A2463)
        val lightBlue = Color(0xFF3E97FF) // A lighter accent blue
        val backgroundGray = Color(0xFFF6F8FA)

        Scaffold(
            containerColor = backgroundGray
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Respect bottom bar padding
            ) {
                // 1. THE DECORATIVE HEADER (The Blue Curve)
                // This replaces the boring top bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(darkBlue, Color(0xFF1E40AF))
                            )
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = 40.dp, start = 24.dp)
                    ) {
                        Text(
                            text = "Magic Scan",
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "AI-powered safety analysis",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 16.sp
                        )
                    }
                }

                // 2. THE CONTENT (Floating above the header)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 140.dp, start = 20.dp, end = 20.dp)
                ) {

                    // --- HERO BUTTON (Camera) ---
                    // This is the main action, so it's big and colorful
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clickable { /* Launch Camera */ },
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                ContainerIcon(Icons.Filled.CameraAlt, darkBlue, Color(0xFFE3F2FD))
                                Spacer(Modifier.height(16.dp))
                                Text(
                                    text = "Scan Contract",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Use camera to capture",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                            // Decorative Illustration could go here
                            Icon(
                                imageVector = Icons.Filled.CameraAlt,
                                contentDescription = null,
                                tint = Color.LightGray.copy(alpha = 0.2f),
                                modifier = Modifier.size(100.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "More Options",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 8.dp, bottom = 12.dp)
                    )

                    // --- GRID FOR OTHER OPTIONS ---
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Option 1: Upload
                        ModernOptionCard(
                            modifier = Modifier.weight(1f),
                            title = "Upload File",
                            icon = Icons.Outlined.Description,
                            color = Color(0xFFFFF4DE), // Light Orange bg
                            iconTint = Color(0xFFFFA000), // Orange icon
                            onClick = { /* Open File */ }
                        )

                        // Option 2: Paste
                        ModernOptionCard(
                            modifier = Modifier.weight(1f),
                            title = "Paste Text",
                            icon = Icons.Outlined.TextFields,
                            color = Color(0xFFE8F5E9), // Light Green bg
                            iconTint = Color(0xFF43A047), // Green icon
                            onClick = { navigator?.push(PasteTextScreen()) }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Option 3: Website (Full Width)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .clickable { },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ContainerIcon(Icons.Outlined.Link, Color(0xFF9C27B0), Color(0xFFF3E5F5))
                            Spacer(Modifier.width(16.dp))
                            Text(
                                text = "Analyze Website Link",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}

// --- HELPER COMPOSABLES ---

@Composable
fun ModernOptionCard(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    color: Color,
    iconTint: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(140.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ContainerIcon(icon, iconTint, color)
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }
    }
}

@Composable
fun ContainerIcon(icon: ImageVector, tint: Color, background: Color) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(background),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(24.dp)
        )
    }
}