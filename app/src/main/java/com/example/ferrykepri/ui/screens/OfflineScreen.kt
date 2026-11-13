package com.example.ferrykepri.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBoat
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material.icons.outlined.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ferrykepri.ui.components.FerryCard
import com.example.ferrykepri.ui.components.FerryHeader
import com.example.ferrykepri.ui.components.TagBadge
import com.example.ferrykepri.ui.theme.FerryBlue500
import com.example.ferrykepri.ui.theme.FerryBlue700
import com.example.ferrykepri.ui.theme.FerryBorder
import com.example.ferrykepri.ui.theme.FerryGreen
import com.example.ferrykepri.ui.theme.FerryGreenSoft
import com.example.ferrykepri.ui.theme.FerryMuted
import com.example.ferrykepri.ui.theme.FerryNavy
import com.example.ferrykepri.ui.theme.FerryRed
import com.example.ferrykepri.ui.theme.FerryRedSoft
import com.example.ferrykepri.ui.theme.FerrySky50
import com.example.ferrykepri.ui.theme.FerryWhite

@Composable
fun OfflineScreen(
    onRetry: () -> Unit,
    onOpenTickets: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FerrySky50)
            .verticalScroll(rememberScrollState())
    ) {
        FerryHeader {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(
                        text = "FerryKepri",
                        style = MaterialTheme.typography.titleLarge,
                        color = FerryWhite,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "MODE OFFLINE",
                        style = MaterialTheme.typography.labelSmall,
                        color = FerryWhite.copy(alpha = 0.75f),
                        letterSpacing = 1.6.sp
                    )
                }
                TagBadge(
                    text = "ID  /  SG",
                    background = FerryWhite.copy(alpha = 0.18f),
                    foreground = FerryWhite
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.size(170.dp)) {
                Surface(
                    shape = CircleShape,
                    color = FerryWhite,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Filled.DirectionsBoat,
                            contentDescription = null,
                            tint = FerryBlue700,
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }
                Surface(
                    shape = CircleShape,
                    color = FerryRedSoft,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp)
                        .size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Outlined.WifiOff,
                            contentDescription = null,
                            tint = FerryRed,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(18.dp))
            TagBadge(
                text = "Koneksi internet terputus",
                background = FerryRedSoft,
                foreground = FerryRed
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "Tidak Ada Koneksi",
                style = MaterialTheme.typography.headlineMedium,
                color = FerryNavy,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Periksa sambungan Wi-Fi atau data seluler Anda untuk " +
                    "memperbarui jadwal ferry Batam–Singapura secara langsung.",
                style = MaterialTheme.typography.bodyMedium,
                color = FerryMuted,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(22.dp))

            Button(
                onClick = onRetry,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = FerryBlue700,
                    contentColor = FerryWhite
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text("Coba Lagi", style = MaterialTheme.typography.labelLarge)
            }

            Spacer(Modifier.height(10.dp))

            OutlinedButton(
                onClick = onOpenTickets,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, FerryBorder)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ConfirmationNumber,
                    contentDescription = null,
                    tint = FerryBlue700,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Lihat Tiket Tersimpan Offline",
                    style = MaterialTheme.typography.labelLarge,
                    color = FerryBlue700
                )
            }

            Spacer(Modifier.height(22.dp))

            FerryCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.ConfirmationNumber,
                        contentDescription = null,
                        tint = FerryBlue700,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Data Tersimpan Lokal",
                        style = MaterialTheme.typography.titleSmall,
                        color = FerryNavy,
                        modifier = Modifier.weight(1f)
                    )
                    TagBadge(
                        text = "Tersedia",
                        background = FerryGreenSoft,
                        foreground = FerryGreen
                    )
                }
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Tiket perjalanan dan e-boarding pass yang sudah diunduh " +
                        "tetap dapat ditunjukkan kepada petugas.",
                    style = MaterialTheme.typography.bodySmall,
                    color = FerryMuted
                )
                Spacer(Modifier.height(12.dp))
                Surface(shape = RoundedCornerShape(12.dp), color = FerrySky50) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(shape = RoundedCornerShape(8.dp), color = FerryWhite) {
                            Icon(
                                imageVector = Icons.Filled.DirectionsBoat,
                                contentDescription = null,
                                tint = FerryBlue700,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(22.dp)
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                        Column(Modifier.weight(1f)) {
                            Text(
                                text = "Batam Fast Ferry",
                                style = MaterialTheme.typography.titleSmall,
                                color = FerryNavy
                            )
                            Text(
                                text = "1x Dewasa • Gate B",
                                style = MaterialTheme.typography.bodySmall,
                                color = FerryMuted
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "14:45 WIB",
                                style = MaterialTheme.typography.titleSmall,
                                color = FerryBlue700
                            )
                            Text(
                                text = "Siap Scan",
                                style = MaterialTheme.typography.labelSmall,
                                color = FerryGreen
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            FerryCard {
                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        imageVector = Icons.Outlined.SupportAgent,
                        contentDescription = null,
                        tint = FerryBlue500,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Butuh bantuan di dermaga?",
                            style = MaterialTheme.typography.titleSmall,
                            color = FerryNavy
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Hubungi petugas terminal melalui panggilan darurat " +
                                "bebas pulsa atau kunjungi loket informasi pelabuhan.",
                            style = MaterialTheme.typography.bodySmall,
                            color = FerryMuted
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }

        Spacer(Modifier.navigationBarsPadding())
    }
}
