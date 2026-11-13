package com.example.ferrykepri.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DirectionsBoat
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ferrykepri.data.Ticket
import com.example.ferrykepri.data.arrivalTime
import com.example.ferrykepri.data.durationLabel
import com.example.ferrykepri.data.toDisplayDate
import com.example.ferrykepri.data.toRupiah
import com.example.ferrykepri.ui.components.EmptyState
import com.example.ferrykepri.ui.components.FerryCard
import com.example.ferrykepri.ui.components.FerryHeader
import com.example.ferrykepri.ui.components.TagBadge
import com.example.ferrykepri.ui.theme.FerryAmber
import com.example.ferrykepri.ui.theme.FerryAmberSoft
import com.example.ferrykepri.ui.theme.FerryBlue700
import com.example.ferrykepri.ui.theme.FerryBorder
import com.example.ferrykepri.ui.theme.FerryGreen
import com.example.ferrykepri.ui.theme.FerryGreenSoft
import com.example.ferrykepri.ui.theme.FerryMuted
import com.example.ferrykepri.ui.theme.FerryNavy
import com.example.ferrykepri.ui.theme.FerryRed
import com.example.ferrykepri.ui.theme.FerryRedSoft
import com.example.ferrykepri.ui.theme.FerrySky100
import com.example.ferrykepri.ui.theme.FerrySky50
import com.example.ferrykepri.ui.theme.FerryWhite
import com.example.ferrykepri.viewmodel.FerryViewModel
import kotlin.random.Random

@Composable
fun TicketsScreen(
    viewModel: FerryViewModel,
    onExplore: () -> Unit
) {
    val tickets by viewModel.tickets.collectAsStateWithLifecycle()
    val cancelled by viewModel.cancelledTickets.collectAsStateWithLifecycle()

    var tab by rememberSaveable { mutableStateOf(0) }
    var cancelTarget by remember { mutableStateOf<Ticket?>(null) }

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
                        text = "TIKET",
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
                TicketHeaderIcon(Icons.Filled.NotificationsNone) {
                    viewModel.notify("Tidak ada notifikasi baru")
                }
                Spacer(Modifier.width(8.dp))
                TicketHeaderIcon(Icons.Filled.Person) {
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
                        text = "Tiket Saya",
                        style = MaterialTheme.typography.headlineSmall,
                        color = FerryNavy
                    )
                    Text(
                        text = "Kelola tiket dan boarding pass penyeberangan Anda.",
                        style = MaterialTheme.typography.bodySmall,
                        color = FerryMuted
                    )
                }
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    TicketTab(
                        text = "Aktif (${tickets.size})",
                        selected = tab == 0,
                        modifier = Modifier.weight(1f),
                        onClick = { tab = 0 }
                    )
                    TicketTab(
                        text = "Dibatalkan (${cancelled.size})",
                        selected = tab == 1,
                        modifier = Modifier.weight(1f),
                        onClick = { tab = 1 }
                    )
                }
            }

            if (tab == 0) {
                if (tickets.isEmpty()) {
                    item {
                        EmptyState(
                            icon = Icons.Outlined.ConfirmationNumber,
                            title = "Belum ada tiket",
                            message = "Tiket yang Anda pesan akan muncul di sini beserta boarding pass-nya.",
                            action = {
                                Button(
                                    onClick = onExplore,
                                    shape = RoundedCornerShape(14.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = FerryBlue700,
                                        contentColor = FerryWhite
                                    )
                                ) {
                                    Text("Cari Jadwal")
                                }
                            }
                        )
                    }
                } else {
                    items(tickets, key = { it.id }) { ticket ->
                        TicketCard(
                            ticket = ticket,
                            cancelled = false,
                            onCancel = { cancelTarget = ticket },
                            onDemo = { viewModel.notify("Fitur demo") }
                        )
                    }
                }
            } else {
                if (cancelled.isEmpty()) {
                    item {
                        EmptyState(
                            icon = Icons.Outlined.ConfirmationNumber,
                            title = "Tidak ada tiket dibatalkan",
                            message = "Riwayat pembatalan tiket akan tampil di sini."
                        )
                    }
                } else {
                    items(cancelled, key = { "c-${it.id}" }) { ticket ->
                        TicketCard(
                            ticket = ticket,
                            cancelled = true,
                            onCancel = { },
                            onDemo = { viewModel.notify("Fitur demo") }
                        )
                    }
                }
            }
        }
    }

    cancelTarget?.let { ticket ->
        AlertDialog(
            onDismissRequest = { cancelTarget = null },
            title = { Text("Batalkan Tiket?") },
            text = {
                Text(
                    "Tiket ${ticket.id} untuk rute ${ticket.schedule.origin} → " +
                        "${ticket.schedule.destination} akan dibatalkan."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.cancelTicket(ticket.id)
                        cancelTarget = null
                    }
                ) {
                    Text("Ya, Batalkan", color = FerryRed)
                }
            },
            dismissButton = {
                TextButton(onClick = { cancelTarget = null }) {
                    Text("Tutup")
                }
            }
        )
    }
}

@Composable
private fun TicketCard(
    ticket: Ticket,
    cancelled: Boolean,
    onCancel: () -> Unit,
    onDemo: () -> Unit
) {
    val schedule = ticket.schedule
    FerryCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(shape = CircleShape, color = FerrySky100, modifier = Modifier.size(40.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Filled.DirectionsBoat,
                        contentDescription = null,
                        tint = FerryBlue700,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
            Spacer(Modifier.width(10.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = schedule.operator,
                    style = MaterialTheme.typography.titleMedium,
                    color = FerryNavy
                )
                Text(
                    text = "Ref ${ticket.id}",
                    style = MaterialTheme.typography.bodySmall,
                    color = FerryMuted
                )
            }
            TagBadge(
                text = if (cancelled) "DIBATALKAN" else "● TERKONFIRMASI",
                background = if (cancelled) FerryRedSoft else FerryGreenSoft,
                foreground = if (cancelled) FerryRed else FerryGreen
            )
        }

        Spacer(Modifier.height(14.dp))

        Surface(shape = RoundedCornerShape(16.dp), color = FerrySky50) {
            Column(Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(
                            text = schedule.departTime,
                            style = MaterialTheme.typography.headlineSmall,
                            color = FerryNavy
                        )
                        Text(
                            text = schedule.origin,
                            style = MaterialTheme.typography.bodySmall,
                            color = FerryMuted
                        )
                        Text(
                            text = "WIB",
                            style = MaterialTheme.typography.labelSmall,
                            color = FerryMuted
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = schedule.durationLabel(),
                            style = MaterialTheme.typography.labelSmall,
                            color = FerryBlue700
                        )
                        Text(
                            text = "→",
                            style = MaterialTheme.typography.titleMedium,
                            color = FerryBlue700
                        )
                        Text(
                            text = "Langsung",
                            style = MaterialTheme.typography.labelSmall,
                            color = FerryMuted
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = schedule.arrivalTime(),
                            style = MaterialTheme.typography.headlineSmall,
                            color = FerryNavy
                        )
                        Text(
                            text = schedule.destination,
                            style = MaterialTheme.typography.bodySmall,
                            color = FerryMuted
                        )
                        Text(
                            text = "SGT",
                            style = MaterialTheme.typography.labelSmall,
                            color = FerryMuted
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.CalendarMonth,
                        contentDescription = null,
                        tint = FerryBlue700,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = ticket.date.toDisplayDate(),
                        style = MaterialTheme.typography.bodySmall,
                        color = FerryNavy,
                        modifier = Modifier.weight(1f)
                    )
                    TagBadge(text = "GMT+8")
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Row {
            Column(Modifier.weight(1f)) {
                Text(
                    text = "KAPAL & BERTH",
                    style = MaterialTheme.typography.labelSmall,
                    color = FerryMuted
                )
                Text(
                    text = schedule.vessel,
                    style = MaterialTheme.typography.titleSmall,
                    color = FerryNavy
                )
                Text(
                    text = "Gate 3 • Berth 2",
                    style = MaterialTheme.typography.bodySmall,
                    color = FerryMuted
                )
            }
            Column(Modifier.weight(1f)) {
                Text(
                    text = "PENUMPANG",
                    style = MaterialTheme.typography.labelSmall,
                    color = FerryMuted
                )
                Text(
                    text = ticket.passengerName,
                    style = MaterialTheme.typography.titleSmall,
                    color = FerryNavy
                )
                Text(
                    text = "${ticket.adults} Dewasa • ${ticket.children} Anak",
                    style = MaterialTheme.typography.bodySmall,
                    color = FerryMuted
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "TOTAL",
                    style = MaterialTheme.typography.labelSmall,
                    color = FerryMuted
                )
                Text(
                    text = ticket.total.toRupiah(),
                    style = MaterialTheme.typography.titleSmall,
                    color = FerryBlue700
                )
            }
        }

        if (!cancelled) {
            Spacer(Modifier.height(14.dp))
            HorizontalDivider(color = FerryBorder)
            Spacer(Modifier.height(12.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "PINDAI DI E-GATE & RAMP FERI",
                    style = MaterialTheme.typography.labelSmall,
                    color = FerryMuted,
                    letterSpacing = 0.8.sp
                )
                Spacer(Modifier.height(10.dp))
                QrPlaceholder(seed = ticket.id.hashCode())
                Spacer(Modifier.height(8.dp))
                Text(
                    text = ticket.id,
                    style = MaterialTheme.typography.bodySmall,
                    color = FerryMuted,
                    letterSpacing = 2.sp
                )
            }

            Spacer(Modifier.height(14.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedButton(
                    onClick = onDemo,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, FerryBorder)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Download,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text("e-Ticket (PDF)", style = MaterialTheme.typography.labelMedium)
                }
                OutlinedButton(
                    onClick = onDemo,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, FerryBorder)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AccountBalanceWallet,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text("Dompet Digital", style = MaterialTheme.typography.labelMedium)
                }
            }

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Batalkan Tiket",
                style = MaterialTheme.typography.labelMedium,
                color = FerryRed,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable(onClick = onCancel)
                    .padding(vertical = 8.dp)
            )
        } else {
            Spacer(Modifier.height(10.dp))
            TagBadge(
                text = "Dana dikembalikan sesuai kebijakan operator",
                background = FerryAmberSoft,
                foreground = FerryAmber
            )
        }
    }
}

@Composable
private fun QrPlaceholder(seed: Int) {
    Canvas(
        modifier = Modifier
            .size(132.dp)
            .background(FerryWhite, RoundedCornerShape(8.dp))
            .padding(6.dp)
    ) {
        val cells = 17
        val cell = size.minDimension / cells
        val random = Random(seed)
        val dark = Color(0xFF10243E)

        for (row in 0 until cells) {
            for (col in 0 until cells) {
                val inFinder = (row < 6 && col < 6) ||
                    (row < 6 && col >= cells - 6) ||
                    (row >= cells - 6 && col < 6)
                if (!inFinder && random.nextFloat() > 0.52f) {
                    drawRect(
                        color = dark,
                        topLeft = Offset(col * cell, row * cell),
                        size = Size(cell, cell)
                    )
                }
            }
        }

        listOf(0 to 0, 0 to cells - 5, cells - 5 to 0).forEach { (row, col) ->
            drawRect(
                color = dark,
                topLeft = Offset(col * cell, row * cell),
                size = Size(cell * 5, cell * 5)
            )
            drawRect(
                color = FerryWhite,
                topLeft = Offset((col + 1) * cell, (row + 1) * cell),
                size = Size(cell * 3, cell * 3)
            )
            drawRect(
                color = dark,
                topLeft = Offset((col + 2) * cell, (row + 2) * cell),
                size = Size(cell, cell)
            )
        }
    }
}

@Composable
private fun TicketTab(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = if (selected) FerryBlue700 else FerryWhite,
        border = if (selected) null else androidx.compose.foundation.BorderStroke(1.dp, FerryBorder),
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = if (selected) FerryWhite else FerryNavy,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
        )
    }
}

@Composable
private fun TicketHeaderIcon(
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
