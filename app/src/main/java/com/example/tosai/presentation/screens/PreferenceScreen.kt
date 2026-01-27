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

class PreferencesScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var notificationsEnabled by remember { mutableStateOf(true) }
        var darkModeEnabled by remember { mutableStateOf(false) }
        var autoSaveEnabled by remember { mutableStateOf(true) }
        var soundEnabled by remember { mutableStateOf(false) }

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
                        text = "Preferences",
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

                    // General Section
                    SectionHeader("General")
                    Spacer(Modifier.height(12.dp))

                    PreferenceToggle(
                        title = "Push Notifications",
                        description = "Get alerts for analysis results",
                        checked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it }
                    )

                    Spacer(Modifier.height(12.dp))

                    PreferenceToggle(
                        title = "Auto-Save Analyses",
                        description = "Automatically save all scans",
                        checked = autoSaveEnabled,
                        onCheckedChange = { autoSaveEnabled = it }
                    )

                    Spacer(Modifier.height(30.dp))

                    // Appearance Section
                    SectionHeader("Appearance")
                    Spacer(Modifier.height(12.dp))

                    PreferenceToggle(
                        title = "Dark Mode",
                        description = "Use dark theme (Coming soon)",
                        checked = darkModeEnabled,
                        onCheckedChange = { darkModeEnabled = it },
                        enabled = false
                    )

                    Spacer(Modifier.height(30.dp))

                    // Sound Section
                    SectionHeader("Sound & Haptics")
                    Spacer(Modifier.height(12.dp))

                    PreferenceToggle(
                        title = "Sound Effects",
                        description = "Play sounds for actions",
                        checked = soundEnabled,
                        onCheckedChange = { soundEnabled = it }
                    )

                    Spacer(Modifier.height(30.dp))

                    // About Section
                    SectionHeader("About")
                    Spacer(Modifier.height(12.dp))

                    PreferenceItem(
                        title = "Version",
                        value = "1.0.0"
                    )

                    Spacer(Modifier.height(12.dp))

                    PreferenceItem(
                        title = "Terms of Service",
                        value = "View",
                        onClick = { /* Navigate to ToS */ }
                    )

                    Spacer(Modifier.height(12.dp))

                    PreferenceItem(
                        title = "Privacy Policy",
                        value = "View",
                        onClick = { /* Navigate to Privacy */ }
                    )

                    Spacer(Modifier.height(50.dp))
                }
            }
        }
    }
}

@Composable
fun SectionHeader(text: String) {
    Text(
        text = text,
        color = Color.White.copy(alpha = 0.6f),
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 1.sp
    )
}

@Composable
fun PreferenceToggle(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true
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
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = if (enabled) Color.White else Color.White.copy(alpha = 0.4f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = description,
                    color = if (enabled) Color.White.copy(alpha = 0.6f) else Color.White.copy(alpha = 0.3f),
                    fontSize = 13.sp
                )
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled,
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
fun PreferenceItem(
    title: String,
    value: String,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
            .then(
                if (onClick != null) Modifier.clickable { onClick() }
                else Modifier
            )
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = value,
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 14.sp
            )
        }
    }
}