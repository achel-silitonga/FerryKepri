package com.example.ferrykepri.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Anchor
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsBoat
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ferrykepri.data.DummyData
import com.example.ferrykepri.data.Schedule
import com.example.ferrykepri.data.arrivalTime
import com.example.ferrykepri.data.durationLabel
import com.example.ferrykepri.data.toDisplayDate
import com.example.ferrykepri.data.toLocalDateOrNull
import com.example.ferrykepri.data.toQueryString
import com.example.ferrykepri.data.toRupiah
import com.example.ferrykepri.data.toSgd
import com.example.ferrykepri.ui.components.FerryCard
import com.example.ferrykepri.ui.components.FerryTopBar
import com.example.ferrykepri.ui.components.SectionTitle
import com.example.ferrykepri.ui.components.TagBadge
import com.example.ferrykepri.ui.theme.FerryAmber
import com.example.ferrykepri.ui.theme.FerryAmberSoft
import com.example.ferrykepri.ui.theme.FerryBlue700
import com.example.ferrykepri.ui.theme.FerryBorder
import com.example.ferrykepri.ui.theme.FerryGreen
import com.example.ferrykepri.ui.theme.FerryGreenSoft
import com.example.ferrykepri.ui.theme.FerryMuted
import com.example.ferrykepri.ui.theme.FerryNavy
import com.example.ferrykepri.ui.theme.FerrySky50
import com.example.ferrykepri.ui.theme.FerryWhite
import com.example.ferrykepri.viewmodel.FerryViewModel
import java.time.LocalDate

@Composable
fun DetailScreen(
    viewModel: FerryViewModel,
    scheduleId: Int,
    onBack: () -> Unit,
    onBooked: () -> Unit
) {
    val schedule = remember(scheduleId) { DummyData.scheduleById(scheduleId) }
    val query by viewModel.lastQuery.collectAsStateWithLifecycle()
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()

    var adults by rememberSaveable(scheduleId) { mutableIntStateOf(1) }
    var children by rememberSaveable(scheduleId) { mutableIntStateOf(0) }

    if (schedule == null) {
        Column(Modifier.fillMaxSize().background(FerrySky50)) {
            FerryTopBar(title = "Detail Pelayaran", onBack = onBack)
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Jadwal tidak ditemukan", color = FerryMuted)
            }
        }
        return
    }

    val bookingDate = query?.date ?: LocalDate.now().toQueryString()
    val total = viewModel.calcTotal(schedule, adults, children)
    val isFavorite = schedule.id in favorites

    Column(Modifier.fillMaxSize().background(FerrySky50)) {
        FerryTopBar(
            title = "Detail Pelayaran",
            subtitle = "${schedule.origin} → ${schedule.destination}",
            onBack = onBack
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                FerryCard {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(shape = CircleShape, color = FerrySky50, modifier = Modifier.size(44.dp)) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Filled.DirectionsBoat,
                                    contentDescription = null,
                                    tint = FerryBlue700,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = schedule.operator,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = FerryNavy
                                )
                                Spacer(Modifier.width(6.dp))
                                TagBadge(text = "Mitra")
                            }
                            Text(
                                text = "${schedule.vessel} • Kapasitas 312 penumpang",
                                style = MaterialTheme.typography.bodySmall,
                                color = FerryMuted
                            )
                        }
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Favorit",
                            tint = if (isFavorite) FerryAmber else FerryMuted,
                            modifier = Modifier
                                .size(26.dp)
                                .clickable { viewModel.toggleFavorite(schedule.id) }
                        )
                    }

                    Spacer(Modifier.height(14.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "● LIVE DEPARTURE RADAR",
                            style = MaterialTheme.typography.labelSmall,
                            color = FerryGreen,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }

            item {
                FerryCard {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(
                                text = "ASAL (ID)",
                                style = MaterialTheme.typography.labelSmall,
                                color = FerryMuted
                            )
                            Text(
                                text = schedule.departTime,
                                style = MaterialTheme.typography.headlineMedium,
                                color = FerryNavy
                            )
                            Text(
                                text = schedule.origin,
                                style = MaterialTheme.typography.bodySmall,
                                color = FerryMuted
                            )
                            Text(
                                text = "WIB (UTC+7)",
                                style = MaterialTheme.typography.labelSmall,
                                color = FerryBlue700
                            )
                        }
                        Column(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = schedule.durationLabel(),
                                style = MaterialTheme.typography.labelSmall,
                                color = FerryBlue700
                            )
                            Icon(
                                imageVector = Icons.Filled.DirectionsBoat,
                                contentDescription = null,
                                tint = FerryBlue700,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Langsung",
                                style = MaterialTheme.typography.labelSmall,
                                color = FerryMuted
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "TUJUAN (SG)",
                                style = MaterialTheme.typography.labelSmall,
                                color = FerryMuted
                            )
                            Text(
                                text = schedule.arrivalTime(),
                                style = MaterialTheme.typography.headlineMedium,
                                color = FerryNavy
                            )
                            Text(
                                text = schedule.destination,
                                style = MaterialTheme.typography.bodySmall,
                                color = FerryMuted
                            )
                            Text(
                                text = "SGT (UTC+8)",
                                style = MaterialTheme.typography.labelSmall,
                                color = FerryBlue700
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = FerrySky50
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Air,
                                contentDescription = null,
                                tint = FerryBlue700,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Laut: 0,4 m • Angin: 8 knot",
                                style = MaterialTheme.typography.bodySmall,
                                color = FerryNavy,
                                modifier = Modifier.weight(1f)
                            )
                            TagBadge(
                                text = "Tenang",
                                background = FerryGreenSoft,
                                foreground = FerryGreen
                            )
                        }
                    }
                }
            }

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Tanggal Keberangkatan",
                        style = MaterialTheme.typography.titleSmall,
                        color = FerryNavy,
                        modifier = Modifier.weight(1f)
                    )
                    TagBadge(text = bookingDate.toDisplayDate())
                }
            }

            item {
                SectionTitle(title = "Pilih Kelas Kabin")
            }

            item {
                CabinCard(
                    title = "Kelas Ekonomi",
                    price = schedule.priceAdult,
                    badge = "Dipilih",
                    highlight = true,
                    facilities = listOf(
                        "Bagasi 20 kg",
                        "Dek ber-AC",
                        "Port USB",
                        "Kursi standar"
                    ),
                    onClick = { }
                )
            }

            item {
                CabinCard(
                    title = "Kelas VIP Panoramik",
                    price = schedule.priceAdult + 200_000,
                    badge = "Prioritas",
                    highlight = false,
                    facilities = listOf(
                        "Boarding prioritas",
                        "Lounge dek atas",
                        "Recliner kulit",
                        "Camilan gratis"
                    ),
                    onClick = {
                        viewModel.notify("Kelas VIP belum tersedia pada simulasi ini")
                    }
                )
            }

            item {
                SectionTitle(title = "Logistik Terminal")
            }

            item {
                TerminalCard(
                    title = "${schedule.origin} Ferry Terminal",
                    description = "Datang 90 menit sebelum keberangkatan. Gerbang boarding " +
                        "ditutup 30 menit sebelum jadwal.",
                    icon = Icons.Outlined.LocationOn
                )
            }

            item {
                TerminalCard(
                    title = "${schedule.destination} Terminal",
                    description = "Terhubung langsung ke MRT. Ikuti jalur e-gate dan ramp feri " +
                        "di lantai keberangkatan.",
                    icon = Icons.Outlined.LocationOn
                )
            }

            item {
                SectionTitle(
                    title = "Penumpang",
                    trailing = {
                        Text(
                            text = "Maks. 8 Penumpang",
                            style = MaterialTheme.typography.bodySmall,
                            color = FerryMuted
                        )
                    }
                )
            }

            item {
                FerryCard {
                    PassengerStepper(
                        title = "Dewasa",
                        subtitle = "Usia 12 tahun ke atas",
                        count = adults,
                        onMinus = {
                            if (adults <= 1) {
                                viewModel.notify("Jumlah penumpang dewasa minimal 1")
                            } else {
                                adults -= 1
                            }
                        },
                        onPlus = {
                            if (adults + children >= 8) {
                                viewModel.notify("Total penumpang maksimal 8 orang")
                            } else {
                                adults += 1
                            }
                        }
                    )
                    Spacer(Modifier.height(12.dp))
                    androidx.compose.material3.HorizontalDivider(color = FerryBorder)
                    Spacer(Modifier.height(12.dp))
                    PassengerStepper(
                        title = "Anak",
                        subtitle = "Usia 2–11 tahun",
                        count = children,
                        onMinus = {
                            if (children <= 0) {
                                viewModel.notify("Jumlah penumpang anak tidak boleh negatif")
                            } else {
                                children -= 1
                            }
                        },
                        onPlus = {
                            if (adults + children >= 8) {
                                viewModel.notify("Total penumpang maksimal 8 orang")
                            } else {
                                children += 1
                            }
                        }
                    )
                }
            }

            item {
                SectionTitle(
                    title = "Rincian Biaya",
                    trailing = {
                        Text(
                            text = "IDR • SGD",
                            style = MaterialTheme.typography.bodySmall,
                            color = FerryMuted
                        )
                    }
                )
            }

            item {
                FerryCard {
                    CostRow(
                        label = "$adults × Tiket Dewasa",
                        value = (schedule.priceAdult * adults).toRupiah()
                    )
                    Spacer(Modifier.height(8.dp))
                    CostRow(
                        label = "$children × Tiket Anak",
                        value = (schedule.priceChild * children).toRupiah()
                    )
                    Spacer(Modifier.height(12.dp))
                    androidx.compose.material3.HorizontalDivider(color = FerryBorder)
                    Spacer(Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(
                                text = "Total Pembayaran",
                                style = MaterialTheme.typography.titleMedium,
                                color = FerryNavy
                            )
                            Text(
                                text = "Termasuk semua pajak",
                                style = MaterialTheme.typography.bodySmall,
                                color = FerryMuted
                            )
                        }
                        Text(
                            text = total.toRupiah(),
                            style = MaterialTheme.typography.headlineSmall,
                            color = FerryBlue700
                        )
                    }
                }
            }
        }

        Surface(shadowElevation = 12.dp, color = FerryWhite) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        text = "Total Pembayaran",
                        style = MaterialTheme.typography.labelSmall,
                        color = FerryMuted
                    )
                    Text(
                        text = total.toRupiah(),
                        style = MaterialTheme.typography.titleLarge,
                        color = FerryNavy
                    )
                    Text(
                        text = "≈ ${total.toSgd()} • ${adults + children} Penumpang",
                        style = MaterialTheme.typography.bodySmall,
                        color = FerryMuted
                    )
                }
                Button(
                    onClick = {
                        val ticket = viewModel.book(
                            schedule = schedule,
                            date = bookingDate,
                            adults = adults,
                            children = children
                        )
                        if (ticket != null) onBooked()
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = FerryBlue700,
                        contentColor = FerryWhite
                    )
                ) {
                    Text("Pesan Tiket", style = MaterialTheme.typography.labelLarge)
                    Spacer(Modifier.width(6.dp))
                    Text("→")
                }
            }
        }
    }
}

@Composable
private fun CabinCard(
    title: String,
    price: Int,
    badge: String,
    highlight: Boolean,
    facilities: List<String>,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = if (highlight) FerrySky50 else FerryWhite,
        border = BorderStroke(
            1.5.dp,
            if (highlight) FerryBlue700 else FerryBorder
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (highlight) Icons.Filled.CheckCircle else Icons.Outlined.ConfirmationNumber,
                    contentDescription = null,
                    tint = if (highlight) FerryBlue700 else FerryMuted,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = FerryNavy,
                    modifier = Modifier.weight(1f)
                )
                TagBadge(
                    text = badge,
                    background = if (highlight) FerryBlue700 else FerryAmberSoft,
                    foreground = if (highlight) FerryWhite else FerryAmber
                )
            }
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = price.toRupiah(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = FerryBlue700
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = "/ ${price.toSgd()}",
                    style = MaterialTheme.typography.bodySmall,
                    color = FerryMuted
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = facilities.joinToString("   •   "),
                style = MaterialTheme.typography.bodySmall,
                color = FerryMuted
            )
        }
    }
}

@Composable
private fun TerminalCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    FerryCard {
        Row(verticalAlignment = Alignment.Top) {
            Surface(shape = CircleShape, color = FerrySky50, modifier = Modifier.size(34.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = FerryBlue700,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            Spacer(Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = FerryNavy
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = FerryMuted
                )
            }
        }
    }
}

@Composable
private fun PassengerStepper(
    title: String,
    subtitle: String,
    count: Int,
    onMinus: () -> Unit,
    onPlus: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = FerryNavy
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = FerryMuted
            )
        }
        StepperButton(
            icon = Icons.Filled.Remove,
            enabled = count > 0,
            onClick = onMinus
        )
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.titleMedium,
            color = FerryNavy,
            modifier = Modifier.width(40.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        StepperButton(
            icon = Icons.Filled.Add,
            enabled = true,
            onClick = onPlus
        )
    }
}

@Composable
private fun StepperButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = CircleShape,
        color = if (enabled) FerrySky50 else FerrySky50.copy(alpha = 0.5f),
        border = BorderStroke(1.dp, FerryBorder),
        modifier = Modifier
            .size(36.dp)
            .then(if (enabled) Modifier.clickable(onClick = onClick) else Modifier)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (enabled) FerryBlue700 else FerryMuted.copy(alpha = 0.4f),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun CostRow(label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = FerryMuted,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = FerryNavy,
            fontWeight = FontWeight.Medium
        )
    }
}
