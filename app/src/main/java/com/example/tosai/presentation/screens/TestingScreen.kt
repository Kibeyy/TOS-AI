package com.example.tosai.presentation.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen

class TestingScreen: Screen {
    @Composable
    override fun Content() {
        val targetSweepAngle = remember { mutableStateOf(0f) }
        val animatedSweep = animateFloatAsState(
            targetValue = targetSweepAngle.value,
            animationSpec = tween(5000),
            label = "ArcAnimation",
        )
        Column (
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)

        ){
            Canvas(
                modifier = Modifier
                    .size(300.dp)
            ) {
                drawArc(
                    color = Color.Red,
                    startAngle = 135f,
                    sweepAngle = 270f,
                    useCenter = false,
                    size = this.size,
                    style = Stroke(width = 60f, cap = StrokeCap.Round),

                )

                drawArc(
                    color = Color.Blue,
                    startAngle = 135f,
                    sweepAngle = animatedSweep.value,
                    useCenter = false,
                    size = this.size,
                    style = Stroke(width = 60f, cap = StrokeCap.Round),

                    )


            }
            Spacer(Modifier.height(20.dp))
            Button(
                onClick = {
                    //to animate from here
                    targetSweepAngle.value = if (targetSweepAngle.value == 0f) 200f else 0f
                }
            ) {
                Text("ANIMATE")
            }

        }
    }
}