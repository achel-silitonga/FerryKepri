package com.example.ferrykepri.ui.screens

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ferrykepri.data.Schedule
import com.example.ferrykepri.data.SearchState
import com.example.ferrykepri.data.SortOption
import com.example.ferrykepri.data.toDisplayDate
import com.example.ferrykepri.ui.components.EmptyState
import com.example.ferrykepri.ui.components.FerryCard
import com.example.ferrykepri.ui.components.FerryTopBar
import com.example.ferrykepri.ui.components.ScheduleCard
import com.example.ferrykepri.ui.components.TagBadge
import com.example.ferrykepri.ui.theme.FerryBlue700
import com.example.ferrykepri.ui.theme.FerryGreen
import com.example.ferrykepri.ui.theme.FerryGreenSoft
import com.example.ferrykepri.ui.theme.FerryMuted
import com.example.ferrykepri.ui.theme.FerryNavy
import com.example.ferrykepri.ui.theme.FerrySky100
import com.example.ferrykepri.ui.theme.FerrySky50
import com.example.ferrykepri.ui.theme.FerryWhite
import com.example.ferrykepri.viewmodel.FerryViewModel
import kotlinx.coroutines.launch

@Composable
fun ResultsScreen(
    viewModel: FerryViewModel,
    onBack: () -> Unit,
    onOpenDetail: (Int) -> Unit
) {
    val state by viewModel.searchState.collectAsStateWithLifecycle()
    val query by viewModel.lastQuery.collectAsStateWithLifecycle()
    val sortOption by viewModel.sortOption.collectAsStateWithLifecycle()
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()

    var simulateEmpty by rememberSaveable { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val results = (state as? SearchState.Success)?.results.orEmpty()
    val visibleResults: List<Schedule> = if (simulateEmpty) emptyList() else results

    Box(Modifier.fillMaxSize().background(FerrySky50)) {
        Column(Modifier.fillMaxSize()) {
            FerryTopBar(
                title = if (query != null) {
                    "${query!!.origin}  →  ${query!!.destination}"
                } else {
                    "Hasil Jadwal"
                },
                subtitle = buildString {
                    append(query?.date?.toDisplayDate() ?: "")
                    append(" • 1 Penumpang")
                },
                onBack = onBack,
                actions = {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = FerryWhite.copy(alpha = 0.18f),
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Filter lanjutan segera hadir")
                                }
                            }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Filled.Tune,
                                contentDescription = "Filter",
                                tint = FerryWhite,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            )

            // Tab simulasi hasil
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(FerrySky50)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ResultTab(
                    text = "Jadwal Tersedia (${results.size})",
                    selected = !simulateEmpty,
                    modifier = Modifier.weight(1f),
                    onClick = { simulateEmpty = false }
                )
                ResultTab(
                    text = "Simulasi Kosong",
                    selected = simulateEmpty,
                    modifier = Modifier.weight(1f),
                    onClick = { simulateEmpty = true }
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding(),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (query != null && !simulateEmpty) {
                    item {
                        FerryCard {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Column(Modifier.weight(1f)) {
                                    Text(
                                        text = "${query!!.origin} – ${query!!.destination}",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = FerryNavy
                                    )
                                    Spacer(Modifier.height(2.dp))
                                    Text(
                                        text = "${query!!.date.toDisplayDate()} • 1 Penumpang",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = FerryMuted
                                    )
                                }
                                TagBadge(
                                    text = if (results.isEmpty()) "Kosong" else "${results.size} Jadwal",
                                    background = FerryGreenSoft,
                                    foreground = FerryGreen
                                )
                            }
                            Spacer(Modifier.height(10.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                TagBadge(text = "● Selat Lancar")
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = "Kondisi laut tenang",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = FerryMuted
                                )
                            }
                        }
                    }
                }

                if (!simulateEmpty && state !is SearchState.Loading && results.isNotEmpty()) {
                    item {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            SortChip(
                                text = "Jam",
                                selected = sortOption == SortOption.TIME,
                                onClick = { viewModel.setSortOption(SortOption.TIME) }
                            )
                            SortChip(
                                text = "Harga",
                                selected = sortOption == SortOption.PRICE,
                                onClick = { viewModel.setSortOption(SortOption.PRICE) }
                            )
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = FerryWhite,
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    com.example.ferrykepri.ui.theme.FerryBorder
                                ),
                                modifier = Modifier.clickable {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Semua operator ditampilkan")
                                    }
                                }
                            ) {
                                Text(
                                    text = "Semua Operator",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = FerryNavy,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }

                when {
                    simulateEmpty -> item {
                        EmptyState(
                            icon = Icons.Outlined.SearchOff,
                            title = "Jadwal tidak ditemukan",
                            message = "Tidak ada jadwal yang cocok untuk rute dan tanggal ini. " +
                                "Coba ubah pelabuhan asal atau tujuan."
                        )
                    }

                    state is SearchState.Loading -> item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 60.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(color = FerryBlue700)
                            Spacer(Modifier.height(14.dp))
                            Text(
                                text = "Mencari jadwal terbaik...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = FerryMuted
                            )
                        }
                    }

                    state is SearchState.Error -> item {
                        EmptyState(
                            icon = Icons.Filled.SearchOff,
                            title = "Pencarian gagal",
                            message = (state as SearchState.Error).message
                        )
                    }

                    state is SearchState.Empty || results.isEmpty() -> item {
                        EmptyState(
                            icon = Icons.Outlined.SearchOff,
                            title = "Jadwal tidak ditemukan",
                            message = "Tidak ada jadwal untuk rute yang dipilih. " +
                                "Silakan kembali dan pilih rute lain."
                        )
                    }

                    else -> {
                        items(visibleResults, key = { it.id }) { schedule ->
                            ScheduleCard(
                                schedule = schedule,
                                isFavorite = schedule.id in favorites,
                                onFavoriteClick = { viewModel.toggleFavorite(schedule.id) },
                                onSelect = { onOpenDetail(schedule.id) }
                            )
                        }
                        item {
                            FerryCard {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Column(Modifier.weight(1f)) {
                                        Text(
                                            text = "Perjalanan Pulang-Pergi?",
                                            style = MaterialTheme.typography.titleSmall,
                                            color = FerryNavy
                                        )
                                        Text(
                                            text = "Hemat hingga Rp 40.000 untuk tiket return.",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = FerryMuted
                                        )
                                    }
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = FerryBlue700,
                                        modifier = Modifier.clickable {
                                            scope.launch {
                                                snackbarHostState.showSnackbar(
                                                    "Pemesanan return segera hadir"
                                                )
                                            }
                                        }
                                    ) {
                                        Text(
                                            text = "+ Return",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = FerryWhite,
                                            modifier = Modifier.padding(
                                                horizontal = 12.dp,
                                                vertical = 8.dp
                                            )
                                        )
                                    }
                                }
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
}

@Composable
private fun ResultTab(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = if (selected) FerryBlue700 else FerryWhite,
        border = if (selected) null else androidx.compose.foundation.BorderStroke(
            1.dp,
            com.example.ferrykepri.ui.theme.FerryBorder
        ),
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = if (selected) FerryWhite else FerryNavy,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
        )
    }
}

@Composable
private fun SortChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(text) },
        shape = RoundedCornerShape(50),
        colors = FilterChipDefaults.filterChipColors(
            containerColor = FerryWhite,
            labelColor = FerryNavy,
            selectedContainerColor = FerrySky100,
            selectedLabelColor = FerryBlue700
        )
    )
}
