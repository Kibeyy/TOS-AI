package com.example.tosai.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DocumentScanner
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person2
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.example.tosai.presentation.components.user_status_tags.PasteTextButton
import com.example.tosai.presentation.components.user_status_tags.ProModeTag
import com.example.tosai.presentation.components.user_status_tags.ProtectionStatusCard
import com.example.tosai.presentation.components.user_status_tags.ScanDocumentButton
import com.example.tosai.presentation.components.user_status_tags.SimpleAnalysisCard

class HomeScreen: Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        Navigator(MainScreen()){navigator ->
            Scaffold(

                bottomBar = {
                    BottomAppBar(containerColor = Color(0xFF0A2463)) {
                        NavigationBar(modifier = Modifier.fillMaxWidth(),
                            containerColor = Color(0xFF0A2463)                      ) {
                            NavigationBarItem(
                                selected = navigator.lastItem is MainScreen, // Highlight logic
                                onClick = {
                                    // Use 'replaceAll' to switch tabs cleanly without building a huge stack
                                    navigator.replaceAll(MainScreen())
                                },
                                icon = { Icon(Icons.Outlined.Home, null, tint = Color.White) },
                                label = { Text("Home", color = Color.White) }
                            )
                            NavigationBarItem(
                                selected = navigator.lastItem is HistoryScreen,
                                onClick = {
                                    navigator.push(HistoryScreen())
                                },
                                icon = { Icon(Icons.Outlined.History, null, tint = Color.White) },
                                label = { Text("History", color = Color.White) }
                            )
                            NavigationBarItem(
                                selected = navigator.lastItem is ScanScreen,
                                onClick = {
                                    // Add Scan Screen navigation here
                                    navigator.push(ScanScreen())
                                },
                                icon = { Icon(Icons.Outlined.DocumentScanner, null, tint = Color.White) },
                                label = { Text("Scan", color = Color.White) }
                            )
                            NavigationBarItem(
                                selected = false,
                                onClick = {
                                    // Add Profile Screen navigation here
                                },
                                icon = { Icon(Icons.Outlined.Person2, null, tint = Color.White) },
                                label = { Text("Profile", color = Color.White) }
                            )
                        }
                    }

                }
            ) { paddingValues ->

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = paddingValues.calculateBottomPadding())
                ) {
                    CurrentScreen()

                }


            }
        }
    }
}