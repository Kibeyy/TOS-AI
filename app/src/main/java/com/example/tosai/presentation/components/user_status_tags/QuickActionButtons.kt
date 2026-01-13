package com.example.tosai.presentation.components.user_status_tags

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentPasteGo
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun ScanDocumentButton(
    onClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable { onClick() }
            .padding(16.dp)
    ) {

        // Icon background
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(44.dp)
                .background(
                    color = Color(0xFFE8F1FF),
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.PhotoCamera,
                contentDescription = "Scan document",
                tint = Color(0xFF1F4FD8)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Texts
        Column {
            Text(
                text = "Scan Document",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = "Take a photo",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}


@Composable
fun PasteTextButton(
    onClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable { onClick() }
            .padding(16.dp)
    ) {

        // Icon background
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(44.dp)
                .background(
                    color = Color(0xFFE8F1FF),
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.ContentPasteGo,
                contentDescription = "Paste Text",
                tint = Color(0xFF00796B)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Texts
        Column {
            Text(
                text = "Paste Text",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )

            Text(
                text = "Copy & Analyze",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

