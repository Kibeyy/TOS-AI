package com.example.tosai.presentation.screens

import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.tosai.presentation.viewmodels.TosViewModel
import android.util.Log // <--- Add this import
import androidx.compose.runtime.collectAsState // <--- Add this import
import androidx.compose.runtime.LaunchedEffect // <--- Add this import
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tosai.presentation.viewmodels.AnalysisState

class PasteTextScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val context = LocalContext.current
        val pastedText = remember { mutableStateOf("") }
        val viewModel: TosViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsState().value
        val keyboardController = LocalSoftwareKeyboardController.current

        LaunchedEffect(uiState) {  // ← Fixed: removed 'val state ='
            when (val state = uiState) {  // ← Fixed: moved 'val state =' here
                is AnalysisState.Loading -> {
                    Log.d("TOS_TEST", "--- AI IS THINKING... ---")
                    Toast.makeText(context, "Analyzing...", Toast.LENGTH_SHORT).show()
                }
                is AnalysisState.Success -> {
                    Log.d("TOS_TEST", "--- SUCCESS! ---")
                    Log.d("TOS_TEST", "TITLE: ${state.analysis.title}")
                    Log.d("TOS_TEST", "RISK PERCENTAGE: ${state.analysis.riskPercentage}%")
                    Log.d("TOS_TEST", "FINDINGS:")
                    state.analysis.keyFindings.forEach { finding ->
                        Log.d("TOS_TEST", "- ${finding.title} [${finding.severity}]: ${finding.description}")
                    }

                    // Navigate to results screen
                    navigator?.push(AnalysisResultScreen(analysis = state.analysis))

                    // Reset state so it doesn't navigate again when coming back
                    viewModel.resetState()
                }
                is AnalysisState.Error -> {
                    Log.e("TOS_TEST", "--- ERROR ---")
                    Log.e("TOS_TEST", state.message)  // ← Fixed: use 'state' instead of 'uiState'
                    Toast.makeText(context, "Error: ${state.message}", Toast.LENGTH_LONG).show()  // ← Fixed
                }
                is AnalysisState.Idle -> {
                    // Idle state
                }
            }
        }

        val bgGradient = Brush.linearGradient(
            colors = listOf(Color(0xFF0A2463), Color(0xFF2E5CB8), Color(0xFF7F4DFF))
        )

        // Clipboard Logic
        fun pasteFromClipboard() {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            if (clipboard.hasPrimaryClip() && clipboard.primaryClipDescription?.hasMimeType("text/plain") == true) {
                val item = clipboard.primaryClip?.getItemAt(0)
                pastedText.value = item?.text?.toString() ?: ""
                Toast.makeText(context, "Text pasted from clipboard", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Clipboard is empty", Toast.LENGTH_SHORT).show()
            }
        }

        Scaffold(
            // Make Scaffold transparent so gradient shows through
            containerColor = Color.Transparent,
            modifier = Modifier.imePadding(), // Resizes screen when keyboard opens
            topBar = {
                // Custom Glass Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Back Button (Glass Circle)
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.1f))
                            .clickable { navigator?.popUntilRoot() }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "Text Analysis",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            bottomBar = {
                // Floating Action Button Area
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Button(
                        onClick = {
                            keyboardController?.hide()
                            Log.d("TOS_TEST", "Button Clicked. Text length: ${pastedText.value.length}")
                            viewModel.analyzeContract(pastedText.value)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White // Stark White for contrast
                        ),
                        shape = RoundedCornerShape(16.dp),
                        enabled = uiState !is AnalysisState.Loading
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.AutoAwesome, null, tint = Color(0xFF0A2463))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Analyze Contract",
                                color = Color(0xFF0A2463),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            // ROOT CONTAINER WITH GRADIENT
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(bgGradient)
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    // --- THE GLASS INPUT BOARD ---
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f) // Takes all available space!
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color.White.copy(alpha = 0.1f)) // Glass Effect
                            .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(24.dp))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp)
                        ) {
                            // Header Row inside the glass
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Paste Contract",
                                    color = Color.White.copy(alpha = 0.8f),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )

                                // Paste Button
                                Row(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.White.copy(alpha = 0.15f))
                                        .clickable { pasteFromClipboard() }
                                        .padding(horizontal = 12.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Filled.ContentPaste, null, tint = Color.White, modifier = Modifier.size(14.dp))
                                    Spacer(Modifier.width(6.dp))
                                    Text("Paste", color = Color.White, fontSize = 12.sp)
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // --- TRANSPARENT TEXT FIELD ---
                            OutlinedTextField(
                                value = pastedText.value,
                                onValueChange = { pastedText.value = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f), // Fills the rest of the glass card
                                placeholder = {
                                    Text(
                                        "Long press to paste or type here...",
                                        color = Color.White.copy(alpha = 0.4f)
                                    )
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Transparent,
                                    unfocusedBorderColor = Color.Transparent,
                                    cursorColor = Color.White,
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                ),
                                textStyle = LocalTextStyle.current.copy(
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp
                                ),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                            )

                            // Character Counter
                            Text(
                                text = "${pastedText.value.length} chars",
                                color = Color.White.copy(alpha = 0.5f),
                                fontSize = 12.sp,
                                modifier = Modifier.align(Alignment.End)
                            )
                        }
                    }
                }
            }
        }
    }
}