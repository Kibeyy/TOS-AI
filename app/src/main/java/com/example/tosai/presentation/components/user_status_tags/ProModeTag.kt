package com.example.tosai.presentation.components.user_status_tags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun ProModeTag(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50)) // pill shape
            .background(Color(0xFFFFC107))
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(
            text = "PRO MODE",
            fontSize = 12.sp,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun BasicModeTag(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(Color(0xFF00796B)) // light green
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(
            text = "BASIC MODE",
            fontSize = 12.sp,
            color =  Color.White // dark gray text
        )
    }
}

