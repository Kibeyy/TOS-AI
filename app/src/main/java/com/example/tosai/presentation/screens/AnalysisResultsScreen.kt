package com.example.tosai.presentation.screens

import android.content.Context
import android.os.Environment
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.tosai.data.local.ContractAnalysis
import com.example.tosai.data.local.KeyFinding
import com.example.tosai.data.local.Severity
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.properties.TextAlignment
import java.io.File

/**
 * Screen that displays the detailed analysis results of a contract
 * Shows: Risk score gauge, contract title, and list of key findings
 * Also allows exporting the analysis as a PDF
 */
class AnalysisResultScreen(
    val analysis: ContractAnalysis // The analysis data passed from previous screen
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        // Get Android context - needed for file operations and showing toasts
        val context = LocalContext.current

        // Determine color based on risk percentage

        val themeColor = when {
            analysis.riskPercentage >= 75 -> Color(0xFFFF4444) // Red
            analysis.riskPercentage >= 40 -> Color(0xFFFFD54F) // Amber
            else -> Color(0xFF00E5FF)                          // Cyan
        }

        // Animation state for the circular progress gauge
        // Starts at 0 and animates to the actual risk percentage
        val animatedProgress = remember { Animatable(0f) }
        LaunchedEffect(Unit) {
            animatedProgress.animateTo(
                targetValue = analysis.riskPercentage / 100f, // Convert percentage to 0-1 range
                animationSpec = tween(durationMillis = 1500, easing = LinearEasing)
            )
        }

        // Background gradient for the screen
        val bgGradient = Brush.linearGradient(
            colors = listOf(Color(0xFF0A2463), Color(0xFF2E5CB8), Color(0xFF7F4DFF))
        )

        Scaffold(
            containerColor = Color.Transparent, // Make scaffold transparent to show gradient
            topBar = {
                // Top navigation bar with back button and download button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp, start = 20.dp, end = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // BACK BUTTON
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.1f)) // Semi-transparent white
                            .clickable { navigator?.pop() } // Go back to previous screen
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
                    }

                    Spacer(Modifier.width(16.dp))

                    // Screen title
                    Text(
                        text = "Analysis Report",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.weight(1f)) // Push download button to the right

                    // DOWNLOAD PDF BUTTON - Glass circle with download icon
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.1f))
                            .clickable {
                                // When clicked, export this analysis to PDF
                                exportToPdf(context, analysis)
                            }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Download,
                            contentDescription = "Download PDF",
                            tint = Color.White
                        )
                    }
                }
            }
        ) { paddingValues ->
            // Main content area with gradient background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(bgGradient)
                    .padding(paddingValues)
            ) {
                // Scrollable list containing the gauge and findings
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // SECTION 1: Risk Score Gauge
                    item {
                        Spacer(Modifier.height(20.dp))

                        // Box to center the gauge and text on top of each other
                        Box(contentAlignment = Alignment.Center) {
                            // The circular progress ring
                            RiskGauge(
                                progress = animatedProgress.value,
                                color = themeColor
                            )

                            // Text overlaid on top of the gauge
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                // Big percentage number
                                Text(
                                    text = "${(animatedProgress.value * 100).toInt()}%",
                                    color = Color.White,
                                    fontSize = 48.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                                // "RISK SCORE" label
                                Text(
                                    text = "RISK SCORE",
                                    color = Color.White.copy(alpha = 0.6f),
                                    fontSize = 12.sp,
                                    letterSpacing = 2.sp
                                )
                            }
                        }

                        Spacer(Modifier.height(20.dp))

                        // Contract title below the gauge
                        Text(
                            text = analysis.title,
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )

                        Spacer(Modifier.height(30.dp))
                    }

                    // SECTION 2: Key Findings header
                    item {
                        Text(
                            text = "Key Findings",
                            color = Color.White.copy(0.7f),
                            fontSize = 14.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                        Spacer(Modifier.height(10.dp))
                    }

                    // SECTION 3: List of all findings
                    // Loop through each finding and display it as a card
                    items(analysis.keyFindings.size) { index ->
                        FindingCard(analysis.keyFindings[index])
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
        }
    }

    /**
     * Draws the circular risk gauge/progress indicator
     * @param progress Value from 0.0 to 1.0 representing the percentage
     * @param color The color for the progress arc
     */
    @Composable
    fun RiskGauge(progress: Float, color: Color) {
        Canvas(modifier = Modifier.size(200.dp)) {
            val strokeWidth = 20.dp.toPx() // Thickness of the ring
            val diameter = size.minDimension // Get the smaller dimension (width or height)
            val topLeftOffset = Offset(
                (size.width - diameter) / 2,
                (size.height - diameter) / 2
            )
            val sizeSq = Size(diameter, diameter)

            // BACKGROUND CIRCLE (faint white ring showing the full path)
            drawArc(
                color = Color.White.copy(alpha = 0.1f),
                startAngle = 135f,      // Start from bottom-left
                sweepAngle = 270f,      // Go 270 degrees (3/4 of a circle)
                useCenter = false,      // Don't draw lines to center (makes it a ring, not a pie)
                topLeft = topLeftOffset,
                size = sizeSq,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // PROGRESS CIRCLE (colored arc showing the actual risk)
            drawArc(
                color = color,
                startAngle = 135f,
                sweepAngle = 270f * progress, // Only draw part of the arc based on progress
                useCenter = false,
                topLeft = topLeftOffset,
                size = sizeSq,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }
    }

    /**
     * Displays a single finding as a card
     * Shows icon (based on severity), title, and description
     */
    @Composable
    fun FindingCard(finding: KeyFinding) {
        // Choose icon based on severity level
        val icon = when (finding.severity) {
            Severity.HIGH -> Icons.Default.Warning      // Warning triangle
            Severity.MEDIUM -> Icons.Default.Info       // Info circle
            Severity.LOW -> Icons.Default.Info          // Info circle
            Severity.GOOD -> Icons.Default.CheckCircle  // Check mark
        }

        // Choose color based on severity level
        val iconColor = when (finding.severity) {
            Severity.HIGH -> Color(0xFFFF4444)    // Red
            Severity.MEDIUM -> Color(0xFFFFD54F)  // Amber
            Severity.LOW -> Color(0xFF00E5FF)     // Cyan
            Severity.GOOD -> Color(0xFF66BB6A)    // Green
        }

        // Glass card with rounded corners
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White.copy(alpha = 0.08f)) // Semi-transparent glass effect
                .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Icon on the left
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier
                    .size(24.dp)
                    .padding(top = 2.dp) // Slight offset to align with text
            )

            Spacer(Modifier.width(16.dp))

            // Title and description on the right
            Column {
                Text(
                    text = finding.title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = finding.description,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }

    /**
     * Exports the current analysis to a PDF file
     * The PDF is saved to the Downloads folder
     *
     * @param context Android context (needed for file operations and toasts)
     * @param analysis The contract analysis data to export
     */
    private fun exportToPdf(context: Context, analysis: ContractAnalysis) {
        try {
            // STEP 1: Create the file in the Downloads folder
            // Replace spaces in title with underscores for a valid filename
            val fileName = "${analysis.title.replace(" ", "_")}_${System.currentTimeMillis()}.pdf"
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                fileName
            )

            // STEP 2: Create PDF writer and document
            val writer = PdfWriter(file)
            val pdf = PdfDocument(writer)
            val document = Document(pdf)

            // STEP 3: Add title to PDF
            document.add(
                Paragraph(analysis.title)
                    .setFontSize(24f)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
            )
            document.add(Paragraph("\n")) // Add blank line

            // STEP 4: Add risk score with colored text
            // Choose PDF color based on risk level (same logic as UI)
            val riskColor = when {
                analysis.riskPercentage >= 75 -> ColorConstants.RED
                analysis.riskPercentage >= 40 -> ColorConstants.ORANGE
                else -> ColorConstants.GREEN
            }

            document.add(
                Paragraph("Risk Score: ${analysis.riskPercentage}%")
                    .setFontSize(18f)
                    .setBold()
                    .setFontColor(riskColor)
            )
            document.add(Paragraph("\n")) // Add blank line

            // STEP 5: Add "Key Findings" section header
            document.add(
                Paragraph("Key Findings")
                    .setFontSize(16f)
                    .setBold()
            )

            // STEP 6: Loop through each finding and add it to the PDF
            analysis.keyFindings.forEach { finding ->
                // Choose color for this finding based on severity
                val findingColor = when (finding.severity) {
                    Severity.HIGH -> ColorConstants.RED
                    Severity.MEDIUM -> ColorConstants.ORANGE
                    Severity.LOW -> ColorConstants.YELLOW
                    Severity.GOOD -> ColorConstants.GREEN
                }

                // Add spacing before each finding
                document.add(Paragraph("\n"))

                // Add finding title with severity label (e.g., "HIGH: Data Collection")
                document.add(
                    Paragraph("${finding.severity}: ${finding.title}")
                        .setFontSize(14f)
                        .setBold()
                        .setFontColor(findingColor)
                )

                // Add finding description
                document.add(
                    Paragraph(finding.description)
                        .setFontSize(12f)
                )
            }

            // STEP 7: Close the document (finalizes the PDF)
            document.close()

            // STEP 8: Show success message to user
            Toast.makeText(
                context,
                "PDF saved to Downloads/$fileName",
                Toast.LENGTH_LONG
            ).show()

        } catch (e: Exception) {
            // If anything goes wrong, show error message
            Toast.makeText(
                context,
                "Failed to export PDF: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}