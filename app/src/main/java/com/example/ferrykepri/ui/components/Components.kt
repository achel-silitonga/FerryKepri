package com.example.ferrykepri.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsBoat
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.DirectionsBoat
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ferrykepri.data.Schedule
import com.example.ferrykepri.data.arrivalTime
import com.example.ferrykepri.data.durationLabel
import com.example.ferrykepri.data.toRupiah
import com.example.ferrykepri.data.toSgd
import com.example.ferrykepri.ui.theme.FerryBlue500
import com.example.ferrykepri.ui.theme.FerryBlue700
import com.example.ferrykepri.ui.theme.FerryBlue800
import com.example.ferrykepri.ui.theme.FerryBorder
import com.example.ferrykepri.ui.theme.FerryGreen
import com.example.ferrykepri.ui.theme.FerryGreenSoft
import com.example.ferrykepri.ui.theme.FerryMuted
import com.example.ferrykepri.ui.theme.FerryNavy
import com.example.ferrykepri.ui.theme.FerrySky100
import com.example.ferrykepri.ui.theme.FerrySky50
import com.example.ferrykepri.ui.theme.FerryWhite

/** Gelombang dekoratif di bagian bawah header biru. */
@Composable
fun WaveBottom(
    modifier: Modifier = Modifier,
    color: Color = FerrySky50
) {
    Canvas(modifier = modifier) {
        val shallow = Path().apply {
            moveTo(0f, size.height * 0.75f)
            cubicTo(
                size.width * 0.22f, size.height * 0.10f,
                size.width * 0.40f, size.height * 1.05f,
                size.width * 0.62f, size.height * 0.62f
            )
            cubicTo(
                size.width * 0.78f, size.height * 0.30f,
                size.width * 0.90f, size.height * 0.40f,
                size.width, size.height * 0.85f
            )
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        drawPath(shallow, color.copy(alpha = 0.55f))
        drawPath(
            Path().apply {
                moveTo(0f, size.height * 0.95f)
                cubicTo(
                    size.width * 0.28f, size.height * 0.35f,
                    size.width * 0.55f, size.height * 1.10f,
                    size.width * 0.78f, size.height * 0.72f
                )
                cubicTo(
                    size.width * 0.88f, size.height * 0.55f,
                    size.width * 0.95f, size.height * 0.70f,
                    size.width, size.height * 0.90f
                )
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            },
            color
        )
    }
}

/** Header biru dengan gradasi dan gelombang, dipakai hampir di semua layar. */
@Composable
fun FerryHeader(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Brush.verticalGradient(listOf(FerryBlue800, FerryBlue500)))
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 28.dp),
            content = content
        )
        WaveBottom(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(20.dp)
        )
    }
}

@Composable
fun FerryTopBar(
    title: String,
    subtitle: String? = null,
    onBack: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    FerryHeader {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (onBack != null) {
                Surface(
                    shape = CircleShape,
                    color = FerryWhite.copy(alpha = 0.18f),
                    modifier = Modifier
                        .size(40.dp)
                        .clickable(onClick = onBack)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = FerryWhite,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(Modifier.width(12.dp))
            }
            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = FerryWhite,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = FerryWhite.copy(alpha = 0.85f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            actions()
        }
    }
}

@Composable
fun FerryCard(
    modifier: Modifier = Modifier,
    contentPadding: Dp = 16.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = FerryWhite,
        border = BorderStroke(1.dp, FerryBorder),
        shadowElevation = 2.dp
    ) {
        Column(Modifier.padding(contentPadding), content = content)
    }
}

@Composable
fun TagBadge(
    text: String,
    background: Color = FerrySky100,
    foreground: Color = FerryBlue700,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = background
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = foreground,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier,
    trailing: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = FerryNavy,
            modifier = Modifier.weight(1f)
        )
        trailing?.invoke()
    }
}

@Composable
fun EmptyState(
    icon: ImageVector,
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    action: (@Composable () -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = CircleShape,
            color = FerrySky100,
            modifier = Modifier.size(88.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = FerryBlue500,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        Spacer(Modifier.height(18.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = FerryNavy
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = FerryMuted,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        if (action != null) {
            Spacer(Modifier.height(20.dp))
            action()
        }
    }
}

/** Field pilihan pelabuhan bergaya OutlinedTextField dengan dropdown sederhana. */
@Composable
fun PortDropdownField(
    label: String,
    value: String,
    placeholder: String,
    options: List<String>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    badge: String? = null,
    leadingIcon: ImageVector = Icons.Outlined.DirectionsBoat,
    isError: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(FerrySky50)
                .border(
                    width = 1.dp,
                    color = if (isError) MaterialTheme.colorScheme.error else FerryBorder,
                    shape = RoundedCornerShape(14.dp)
                )
                .clickable { expanded = true }
                .padding(horizontal = 14.dp, vertical = 10.dp)
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
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = FerryBlue500,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = value.ifBlank { placeholder },
                    style = MaterialTheme.typography.titleSmall,
                    color = if (value.isBlank()) FerryMuted else FerryNavy,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                if (badge != null) {
                    TagBadge(text = badge)
                    Spacer(Modifier.width(6.dp))
                }
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    tint = FerryMuted,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

/** Kartu ringkas satu jadwal, dipakai di Beranda, Hasil Jadwal, dan Favorit. */
@Composable
fun ScheduleCard(
    schedule: Schedule,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    FerryCard(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(shape = CircleShape, color = FerrySky100, modifier = Modifier.size(38.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Filled.DirectionsBoat,
                        contentDescription = null,
                        tint = FerryBlue700,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(Modifier.width(10.dp))
            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = schedule.operator,
                        style = MaterialTheme.typography.titleMedium,
                        color = FerryNavy,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    if (schedule.tag != null) {
                        Spacer(Modifier.width(6.dp))
                        TagBadge(text = schedule.tag)
                    }
                }
                Text(
                    text = schedule.vessel,
                    style = MaterialTheme.typography.bodySmall,
                    color = FerryMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = if (isFavorite) "Hapus dari favorit" else "Tambah ke favorit",
                tint = if (isFavorite) MaterialTheme.colorScheme.error else FerryMuted,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onFavoriteClick)
            )
        }

        Spacer(Modifier.height(14.dp))

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
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = schedule.durationLabel(),
                    style = MaterialTheme.typography.labelSmall,
                    color = FerryBlue700
                )
                Canvas(Modifier.fillMaxWidth().height(10.dp)) {
                    val y = size.height / 2
                    drawLine(
                        color = FerryBlue500.copy(alpha = 0.35f),
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = 2f
                    )
                    drawCircle(FerryBlue700, radius = 4f, center = Offset(0f, y))
                    drawCircle(FerryBlue700, radius = 4f, center = Offset(size.width, y))
                }
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
            }
        }

        Spacer(Modifier.height(14.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = schedule.priceAdult.toRupiah(),
                    style = MaterialTheme.typography.titleMedium,
                    color = FerryBlue700
                )
                Text(
                    text = "≈ ${schedule.priceAdult.toSgd()} • ${schedule.seatsLeft} kursi tersisa",
                    style = MaterialTheme.typography.bodySmall,
                    color = FerryMuted
                )
            }
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = FerryBlue700,
                modifier = Modifier.clickable(onClick = onSelect)
            ) {
                Text(
                    text = "Pilih Jadwal",
                    style = MaterialTheme.typography.labelLarge,
                    color = FerryWhite,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )
            }
        }
    }
}
