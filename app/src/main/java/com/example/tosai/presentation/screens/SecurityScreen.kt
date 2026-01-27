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
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

class SecurityScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var biometricEnabled by remember { mutableStateOf(false) }
        var pinEnabled by remember { mutableStateOf(false) }
        var autoLockEnabled by remember { mutableStateOf(true) }

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
                        text = "Security",
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

                    // Security Status Card
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF1B5E20),
                                        Color(0xFF388E3C)
                                    )
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Outlined.Shield,
                                null,
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(Modifier.width(16.dp))
                            Column {
                                Text(
                                    "Your account is secure",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "All security features active",
                                    color = Color.White.copy(alpha = 0.8f),
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(30.dp))

                    // Authentication Section
                    SectionHeader("Authentication")
                    Spacer(Modifier.height(12.dp))

                    SecurityToggle(
                        icon = Icons.Outlined.Fingerprint,
                        title = "Biometric Login",
                        description = "Use fingerprint or face ID",
                        checked = biometricEnabled,
                        onCheckedChange = { biometricEnabled = it }
                    )

                    Spacer(Modifier.height(12.dp))

                    SecurityToggle(
                        icon = Icons.Outlined.Lock,
                        title = "PIN Code",
                        description = "Require PIN to open app",
                        checked = pinEnabled,
                        onCheckedChange = { pinEnabled = it }
                    )

                    Spacer(Modifier.height(30.dp))

                    // Privacy Section
                    SectionHeader("Privacy")
                    Spacer(Modifier.height(12.dp))

                    SecurityToggle(
                        icon = Icons.Outlined.Lock,
                        title = "Auto-Lock",
                        description = "Lock app after 5 minutes",
                        checked = autoLockEnabled,
                        onCheckedChange = { autoLockEnabled = it }
                    )

                    Spacer(Modifier.height(30.dp))

                    // Data Management Section
                    SectionHeader("Data Management")
                    Spacer(Modifier.height(12.dp))

                    SecurityAction(
                        title = "Clear Cache",
                        description = "Remove temporary files",
                        onClick = { /* Clear cache logic */ }
                    )

                    Spacer(Modifier.height(12.dp))

                    SecurityAction(
                        title = "Delete All Data",
                        description = "Permanently remove all analyses",
                        onClick = { /* Delete data logic */ },
                        textColor = Color(0xFFFF8A80)
                    )

                    Spacer(Modifier.height(50.dp))
                }
            }
        }
    }
}

@Composable
fun SecurityToggle(
    icon: ImageVector,
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    icon,
                    null,
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(16.dp))
                Column {
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

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF69F0AE),
                    uncheckedThumbColor = Color.White.copy(alpha = 0.6f),
                    uncheckedTrackColor = Color.White.copy(alpha = 0.2f)
                )
            )
        }
    }
}

@Composable
fun SecurityAction(
    title: String,
    description: String,
    onClick: () -> Unit,
    textColor: Color = Color.White
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
        Column {
            Text(
                text = title,
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = description,
                color = textColor.copy(alpha = 0.6f),
                fontSize = 13.sp
            )
        }
    }
}