package com.example.tosai.presentation.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DocumentScanner
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person2
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator


class HomeScreen: Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val navItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color(0xFF0A2463),
            selectedTextColor = Color(0xFF0A2463),
            indicatorColor = Color.White.copy(alpha = .5f),
            unselectedIconColor = Color.Gray,
            unselectedTextColor = Color.Gray
        )


        Navigator(MainScreen()){navigator ->
            Scaffold(


                bottomBar = {
                   NavigationBar(modifier = Modifier.fillMaxWidth(),

                            containerColor = Color(0xFF7F4DFF) ) {

                            NavigationBarItem(

                                selected = navigator.lastItem is MainScreen, // Highlight logic

                                onClick = {
                                    navigator.replaceAll(MainScreen())
                                },
                                icon = { Icon(Icons.Outlined.Home, null, tint = Color.White) },
                                label = { Text("Home", color = Color.White) },
                                colors = navItemColors

                            )

                            NavigationBarItem(
                                selected = navigator.lastItem is HistoryScreen,
                                onClick = {
                                    navigator.push(HistoryScreen())
                                },
                                icon = { Icon(Icons.Outlined.History, null, tint = Color.White) },
                                label = { Text("History", color = Color.White) },
                                colors = navItemColors

                            )

                            NavigationBarItem(
                                selected = navigator.lastItem is ScanScreen,
                                onClick = {
                                    navigator.push(ScanScreen())
                                },
                                icon = { Icon(Icons.Outlined.DocumentScanner, null, tint = Color.White) },
                                label = { Text("Scan", color = Color.White) },
                                colors = navItemColors

                            )

                            NavigationBarItem(
                                selected = navigator.lastItem is ProfileScreen,
                                onClick = {
                                    navigator.push(ProfileScreen())
                                },
                                icon = { Icon(Icons.Outlined.Person2, null, tint = Color.White) },
                                label = { Text("Profile", color = Color.White) },
                                colors = navItemColors,
                            )
                    }
                }
            ) { paddingValues ->

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = paddingValues.calculateBottomPadding())
                        .imePadding()
                ) {
                    CurrentScreen()

                }


            }
        }
    }
}