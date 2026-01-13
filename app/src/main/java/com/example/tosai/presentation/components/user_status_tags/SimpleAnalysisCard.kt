package com.example.tosai.presentation.components.user_status_tags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun SimpleAnalysisCard(
    title: String = "Instagram Terms",
    subtitle: String = "Yesterday",
    score: Int = 85
) {
    val riskColor = when {
        score >= 70 -> Color(0xFFD32F2F) // High risk – red
        score >= 40 -> Color(0xFFFBC02D) // Medium risk – yellow
        else -> Color(0xFF388E3C)        // Low risk – green
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
           // .padding(horizontal = 12.dp, vertical = 6.dp),
        ,shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(82.dp)
        ) {

            // Main content
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Text(
                    text = score.toString(),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = riskColor
                )
            }

            // Risk color bar
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .fillMaxHeight()
                    .background(
                        color = riskColor,
                        shape = RoundedCornerShape(
                            topEnd = 12.dp,
                            bottomEnd = 12.dp
                        )
                    )
            )
        }
    }
}
