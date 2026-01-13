package com.example.tosai.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.tosai.presentation.components.user_status_tags.PasteTextButton
import com.example.tosai.presentation.components.user_status_tags.ProModeTag
import com.example.tosai.presentation.components.user_status_tags.ProtectionStatusCard
import com.example.tosai.presentation.components.user_status_tags.ScanDocumentButton
import com.example.tosai.presentation.components.user_status_tags.SimpleAnalysisCard

class MainScreen: Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0A2463)),
                    navigationIcon = { Box(contentAlignment = Alignment.Center,modifier = Modifier.padding(start = 20.dp)) { Icon(
                        Icons.Outlined.Security, contentDescription = null) } },
                    title = { Text("TOS.ai", fontWeight = FontWeight.Bold,color = Color.White) },
                    actions = {
                        Box(contentAlignment = Alignment.Center,modifier = Modifier.padding(end = 10.dp)) {ProModeTag()}
                    },

                    )
            },
        ){paddingValues ->
            Column(
                modifier = Modifier
                      .padding(paddingValues)
                    .padding(start = 20.dp ,end = 20.dp, top = 30.dp,bottom = 5.dp)
            ) {

                ProtectionStatusCard()
                Spacer(Modifier.height(30.dp))
                Text("Quick Scan")
                Spacer(Modifier.height(30.dp))
                ScanDocumentButton()
                Spacer(Modifier.height(20.dp))
                PasteTextButton(onClick = {navigator?.push(PasteTextScreen())})
                Spacer(Modifier.height(30.dp))
                Text("Recent Scans")
                Spacer(Modifier.height(20.dp))
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(5){item ->
                        SimpleAnalysisCard()
                        Spacer(Modifier.height(10.dp))

                    }
                }



            }

        }

    }
}