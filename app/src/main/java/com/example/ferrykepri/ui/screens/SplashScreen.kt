package com.example.ferrykepri.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBoat
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ferrykepri.ui.theme.FerryBlue300
import com.example.ferrykepri.ui.theme.FerryBlue500
import com.example.ferrykepri.ui.theme.FerryBlue800
import com.example.ferrykepri.ui.theme.FerrySky50
import com.example.ferrykepri.ui.theme.FerryWhite
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(FerryBlue800, FerryBlue500)))
    ) {
        Canvas(Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height * 0.38f)
            listOf(190f, 300f, 430f, 580f, 740f).forEachIndexed { index, radius ->
                drawCircle(
                    color = FerryWhite.copy(alpha = 0.05f + index * 0.012f),
                    radius = radius,
                    center = center
                )
            }
        }

        Row(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SplashChip(text = "● LIVE MARINE AIS")
            Spacer(Modifier.weight(1f))
            SplashChip(text = "1.1304° N, 104.0531° E", icon = true)
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = RoundedCornerShape(28.dp),
                color = FerryWhite.copy(alpha = 0.18f),
                modifier = Modifier.size(112.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Filled.DirectionsBoat,
                        contentDescription = null,
                        tint = FerryWhite,
                        modifier = Modifier.size(58.dp)
                    )
                }
            }
            Spacer(Modifier.height(22.dp))
            Text(
                text = "FerryKepri",
                style = MaterialTheme.typography.displaySmall,
                fontSize = 40.sp,
                color = FerryWhite,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "Batam  ⇄  Singapura",
                style = MaterialTheme.typography.titleMedium,
                color = FerryWhite.copy(alpha = 0.9f)
            )
            Spacer(Modifier.height(26.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SplashChip("HarbourFront")
                SplashChip("Batam Centre")
                SplashChip("Tanah Merah")
            }
            Spacer(Modifier.height(8.dp))
            SplashChip("Sekupang")
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = FerryWhite.copy(alpha = 0.9f),
                strokeWidth = 2.dp,
                modifier = Modifier.size(30.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "MEMPERBARUI JADWAL HARBOURFRONT...",
                style = MaterialTheme.typography.labelSmall,
                color = FerryWhite.copy(alpha = 0.85f),
                letterSpacing = 1.5.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "v1.0.0 • Kepulauan Riau & Singapore",
                style = MaterialTheme.typography.bodySmall,
                color = FerryWhite.copy(alpha = 0.65f)
            )
        }
    }
}

@Composable
private fun SplashChip(text: String, icon: Boolean = false) {
    Surface(
        shape = CircleShape,
        color = FerryWhite.copy(alpha = 0.16f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon) {
                Icon(
                    imageVector = Icons.Filled.Public,
                    contentDescription = null,
                    tint = FerryWhite.copy(alpha = 0.9f),
                    modifier = Modifier.size(12.dp)
                )
                Spacer(Modifier.width(5.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = FerryWhite.copy(alpha = 0.92f)
            )
        }
    }
}
