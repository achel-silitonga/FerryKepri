package com.example.ferrykepri.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DirectionsBoat
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ferrykepri.data.DummyData
import com.example.ferrykepri.data.Validators
import com.example.ferrykepri.data.toDisplayDate
import com.example.ferrykepri.data.toQueryString
import com.example.ferrykepri.ui.components.FerryCard
import com.example.ferrykepri.ui.components.FerryHeader
import com.example.ferrykepri.ui.components.PortDropdownField
import com.example.ferrykepri.ui.components.ScheduleCard
import com.example.ferrykepri.ui.components.SectionTitle
import com.example.ferrykepri.ui.components.TagBadge
import com.example.ferrykepri.ui.theme.FerryBlue500
import com.example.ferrykepri.ui.theme.FerryBlue700
import com.example.ferrykepri.ui.theme.FerryBorder
import com.example.ferrykepri.ui.theme.FerryMuted
import com.example.ferrykepri.ui.theme.FerryNavy
import com.example.ferrykepri.ui.theme.FerrySky100
import com.example.ferrykepri.ui.theme.FerrySky50
import com.example.ferrykepri.ui.theme.FerryWhite
import com.example.ferrykepri.viewmodel.FerryViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: FerryViewModel,
    onSearch: () -> Unit,
    onOpenDetail: (Int) -> Unit
) {
    var origin by rememberSaveable { mutableStateOf("Batam Centre") }
    var destination by rememberSaveable { mutableStateOf("HarbourFront") }
    var date by rememberSaveable { mutableStateOf(LocalDate.now().toQueryString()) }
    var roundTrip by rememberSaveable { mutableStateOf(false) }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var originError by remember { mutableStateOf(false) }
    var destinationError by remember { mutableStateOf(false) }
    var dateError by remember { mutableStateOf(false) }

    val favorites by viewModel.favorites.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val todayUtcMillis = remember {
        LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
    }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = todayUtcMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean =
                utcTimeMillis >= todayUtcMillis
        }
    )

    fun notify(message: String) {
        scope.launch { snackbarHostState.showSnackbar(message) }
    }

    fun runSearch() {
        val error = Validators.search(origin, destination, date)
        originError = origin.isBlank()
        destinationError = destination.isBlank()
        dateError = date.isBlank()
        if (error != null) {
            notify(error)
            return
        }
        viewModel.search(origin, destination, date)
        onSearch()
    }

    val departures = remember(origin, destination) {
        DummyData.schedules.filter { it.origin == origin && it.destination == destination }
            .ifEmpty {
                DummyData.schedules.filter {
                    it.origin == "Batam Centre" && it.destination == "HarbourFront"
                }
            }
            .take(3)
    }

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
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
                            text = "BERANDA",
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
                    Spacer(Modifier.width(10.dp))
                    HeaderIcon(Icons.Filled.NotificationsNone, "Notifikasi") {
                        notify("Tidak ada notifikasi baru")
                    }
                    Spacer(Modifier.width(8.dp))
                    HeaderIcon(Icons.Filled.Person, "Profil") {
                        notify("Fitur demo")
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(FerrySky50),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = FerrySky100
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Selamat datang, Traveler!",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = FerryNavy
                                )
                                Spacer(Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.Filled.DirectionsBoat,
                                    contentDescription = null,
                                    tint = FerryBlue700,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "Jelajahi jadwal ferry langsung antara Batam dan Singapura.",
                                style = MaterialTheme.typography.bodySmall,
                                color = FerryMuted
                            )
                        }
                    }
                }

                item {
                    FerryCard(contentPadding = 16.dp) {
                        Box {
                            Column {
                                PortDropdownField(
                                    label = "PELABUHAN ASAL",
                                    value = origin,
                                    placeholder = "Pilih pelabuhan asal",
                                    options = DummyData.batamPorts,
                                    badge = "WIB",
                                    isError = originError,
                                    onSelect = {
                                        origin = it
                                        originError = false
                                    }
                                )
                                Spacer(Modifier.height(8.dp))
                                PortDropdownField(
                                    label = "PELABUHAN TUJUAN",
                                    value = destination,
                                    placeholder = "Pilih pelabuhan tujuan",
                                    options = DummyData.singaporePorts,
                                    badge = "SGT",
                                    isError = destinationError,
                                    onSelect = {
                                        destination = it
                                        destinationError = false
                                    }
                                )
                            }
                            Surface(
                                shape = CircleShape,
                                color = FerryBlue700,
                                shadowElevation = 3.dp,
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 12.dp)
                                    .size(38.dp)
                                    .clickable {
                                        val temp = origin
                                        origin = destination
                                        destination = temp
                                    }
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Filled.SwapVert,
                                        contentDescription = "Tukar pelabuhan",
                                        tint = FerryWhite,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            FilterChip(
                                selected = !roundTrip,
                                onClick = { roundTrip = false },
                                label = { Text("Sekali Jalan") },
                                shape = RoundedCornerShape(12.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = FerryBlue700,
                                    selectedLabelColor = FerryWhite
                                )
                            )
                            FilterChip(
                                selected = roundTrip,
                                onClick = {
                                    roundTrip = true
                                    notify("Pemesanan pulang-pergi segera hadir")
                                },
                                label = { Text("Pulang-Pergi") },
                                shape = RoundedCornerShape(12.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = FerryBlue700,
                                    selectedLabelColor = FerryWhite
                                )
                            )
                        }

                        Spacer(Modifier.height(8.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            SelectorField(
                                modifier = Modifier.weight(1f),
                                label = "TANGGAL BERANGKAT",
                                value = date.toDisplayDate(),
                                icon = Icons.Filled.CalendarMonth,
                                isError = dateError,
                                onClick = { showDatePicker = true }
                            )
                            SelectorField(
                                modifier = Modifier.weight(1f),
                                label = "PENUMPANG",
                                value = "1 Dewasa",
                                icon = Icons.Filled.Groups,
                                onClick = { notify("Atur jumlah penumpang di halaman detail") }
                            )
                        }

                        Spacer(Modifier.height(14.dp))

                        Button(
                            onClick = { runSearch() },
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
                                imageVector = Icons.Filled.DirectionsBoat,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("Cek Jadwal Ferry", style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }

                item {
                    SectionTitle(title = "Rute Populer")
                }

                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        DummyData.popularRoutes().forEach { (from, to) ->
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = FerryWhite,
                                border = androidx.compose.foundation.BorderStroke(1.dp, FerryBorder),
                                modifier = Modifier.clickable {
                                    origin = from
                                    destination = to
                                    originError = false
                                    destinationError = false
                                }
                            ) {
                                Text(
                                    text = "$from  ⇄  $to",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = FerryBlue700,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }

                item {
                    FerryCard {
                        Row(verticalAlignment = Alignment.Top) {
                            Surface(shape = CircleShape, color = FerrySky100, modifier = Modifier.size(34.dp)) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Outlined.Info,
                                        contentDescription = null,
                                        tint = FerryBlue700,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                            Spacer(Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Waktu Lintas Batas",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = FerryNavy
                                )
                                Spacer(Modifier.height(2.dp))
                                Text(
                                    text = "Waktu Singapura GMT+8 (1 jam lebih cepat dari Batam GMT+7). " +
                                        "Masa berlaku paspor minimal 6 bulan saat pemeriksaan.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = FerryMuted
                                )
                            }
                        }
                    }
                }

                item {
                    SectionTitle(
                        title = "Keberangkatan Hari Ini",
                        trailing = {
                            TagBadge(
                                text = "${departures.size} Aktif",
                                background = FerrySky100,
                                foreground = FerryBlue700
                            )
                        }
                    )
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "● Status Pelabuhan: Normal",
                            style = MaterialTheme.typography.labelSmall,
                            color = com.example.ferrykepri.ui.theme.FerryGreen
                        )
                    }
                }

                items(departures, key = { it.id }) { schedule ->
                    ScheduleCard(
                        schedule = schedule,
                        isFavorite = schedule.id in favorites,
                        onFavoriteClick = { viewModel.toggleFavorite(schedule.id) },
                        onSelect = { onOpenDetail(schedule.id) }
                    )
                }

                item {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color.Transparent
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .background(
                                    Brush.linearGradient(
                                        listOf(FerryBlue700, FerryBlue500)
                                    ),
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .padding(16.dp)
                        ) {
                            Column(Modifier.align(Alignment.BottomStart)) {
                                TagBadge(
                                    text = "LIVE SEA ADVISORY",
                                    background = FerryWhite.copy(alpha = 0.2f),
                                    foreground = FerryWhite
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = "Perairan Tenang • Tinggi Gelombang 0,4 m • 28°C",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = FerryWhite
                                )
                                Text(
                                    text = "Kondisi ideal untuk penyeberangan hari ini",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = FerryWhite.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            date = Instant.ofEpochMilli(millis)
                                .atZone(ZoneOffset.UTC)
                                .toLocalDate()
                                .toQueryString()
                            dateError = false
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("Pilih")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Batal")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
private fun HeaderIcon(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    Surface(
        shape = CircleShape,
        color = FerryWhite.copy(alpha = 0.18f),
        modifier = Modifier
            .size(36.dp)
            .clickable(onClick = onClick)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = FerryWhite,
                modifier = Modifier.size(19.dp)
            )
        }
    }
}

@Composable
private fun SelectorField(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false
) {
    Column(
        modifier = modifier
            .background(FerrySky50, RoundedCornerShape(14.dp))
            .border(
                width = 1.dp,
                color = if (isError) Color(0xFFC62828) else FerryBorder,
                shape = RoundedCornerShape(14.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = FerryMuted,
            fontSize = 10.sp
        )
        Spacer(Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = FerryBlue500,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.labelMedium,
                color = FerryNavy,
                maxLines = 1
            )
        }
    }
}
