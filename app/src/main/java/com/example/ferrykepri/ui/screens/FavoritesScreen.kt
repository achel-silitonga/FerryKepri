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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ferrykepri.data.DummyData
import com.example.ferrykepri.ui.components.EmptyState
import com.example.ferrykepri.ui.components.FerryCard
import com.example.ferrykepri.ui.components.FerryHeader
import com.example.ferrykepri.ui.components.ScheduleCard
import com.example.ferrykepri.ui.components.TagBadge
import com.example.ferrykepri.ui.theme.FerryBlue700
import com.example.ferrykepri.ui.theme.FerryMuted
import com.example.ferrykepri.ui.theme.FerryNavy
import com.example.ferrykepri.ui.theme.FerrySky100
import com.example.ferrykepri.ui.theme.FerrySky50
import com.example.ferrykepri.ui.theme.FerryWhite
import com.example.ferrykepri.viewmodel.FerryViewModel

@Composable
fun FavoritesScreen(
    viewModel: FerryViewModel,
    onOpenDetail: (Int) -> Unit,
    onExplore: () -> Unit
) {
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()
    var selectedRoute by rememberSaveable { mutableStateOf("Semua") }

    val favoriteSchedules = remember(favorites) {
        DummyData.schedules.filter { it.id in favorites }
    }
    val routes = remember(favoriteSchedules) {
        favoriteSchedules.map { "${it.origin} ⇄ ${it.destination}" }.distinct()
    }
    val visible = if (selectedRoute == "Semua") {
        favoriteSchedules
    } else {
        favoriteSchedules.filter { "${it.origin} ⇄ ${it.destination}" == selectedRoute }
    }

    Column(Modifier.fillMaxSize().background(FerrySky50)) {
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
                        text = "FAVORIT",
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
                FavoritesHeaderIcon(Icons.Filled.NotificationsNone) {
                    viewModel.notify("Tidak ada notifikasi baru")
                }
                Spacer(Modifier.width(8.dp))
                FavoritesHeaderIcon(Icons.Filled.Person) {
                    viewModel.notify("Fitur demo")
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column {
                    Text(
                        text = "Rute Tersimpan",
                        style = MaterialTheme.typography.headlineSmall,
                        color = FerryNavy
                    )
                    Text(
                        text = "Akses cepat jadwal favorit yang sering Anda gunakan.",
                        style = MaterialTheme.typography.bodySmall,
                        color = FerryMuted
                    )
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
                            TagBadge(text = "INFO JAM SIBUK")
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = "Jadwal sore dari Singapura cepat penuh. " +
                                    "Pesan 2 hari lebih awal untuk mengamankan tempat.",
                                style = MaterialTheme.typography.bodySmall,
                                color = FerryMuted
                            )
                        }
                    }
                }
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = selectedRoute == "Semua",
                        onClick = { selectedRoute = "Semua" },
                        label = { Text("Semua (${favoriteSchedules.size})") },
                        shape = RoundedCornerShape(50),
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = FerryWhite,
                            selectedContainerColor = FerryBlue700,
                            selectedLabelColor = FerryWhite
                        )
                    )
                    routes.take(2).forEach { route ->
                        FilterChip(
                            selected = selectedRoute == route,
                            onClick = {
                                selectedRoute = if (selectedRoute == route) "Semua" else route
                            },
                            label = {
                                Text(
                                    text = route,
                                    maxLines = 1
                                )
                            },
                            shape = RoundedCornerShape(50),
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = FerryWhite,
                                selectedContainerColor = FerryBlue700,
                                selectedLabelColor = FerryWhite
                            )
                        )
                    }
                }
            }

            if (visible.isEmpty()) {
                item {
                    EmptyState(
                        icon = Icons.Filled.FavoriteBorder,
                        title = "Belum ada rute favorit",
                        message = "Ketuk ikon hati pada jadwal untuk menyimpannya di sini.",
                        action = {
                            Button(
                                onClick = onExplore,
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = FerryBlue700,
                                    contentColor = FerryWhite
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(Modifier.width(6.dp))
                                Text("Jelajahi Jadwal")
                            }
                        }
                    )
                }
            } else {
                items(visible, key = { it.id }) { schedule ->
                    ScheduleCard(
                        schedule = schedule,
                        isFavorite = schedule.id in favorites,
                        onFavoriteClick = { viewModel.toggleFavorite(schedule.id) },
                        onSelect = { onOpenDetail(schedule.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun FavoritesHeaderIcon(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
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
                contentDescription = null,
                tint = FerryWhite,
                modifier = Modifier.size(19.dp)
            )
        }
    }
}
