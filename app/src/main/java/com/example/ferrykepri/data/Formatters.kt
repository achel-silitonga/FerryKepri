package com.example.ferrykepri.data

import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val localeId = Locale.forLanguageTag("id-ID")
private val idrFormat: NumberFormat = NumberFormat.getIntegerInstance(localeId)
private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US)
private val displayDateFormatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy", localeId)

/** Rp 430.000 */
fun Int.toRupiah(): String = "Rp " + idrFormat.format(this.toLong())

/** Perkiraan harga dalam dolar Singapura, dipakai sebagai info sekunder. */
fun Int.toSgd(): String = "S$" + "%.0f".format(Locale.US, this / 11_300.0)

/** 08:30 + 70 menit -> 09:40 */
fun Schedule.arrivalTime(): String = try {
    LocalTime.parse(departTime, timeFormatter)
        .plusMinutes(durationMin.toLong())
        .format(timeFormatter)
} catch (e: Exception) {
    "--:--"
}

/** 70 -> "1j 10m" */
fun Schedule.durationLabel(): String = "${durationMin / 60}j ${durationMin % 60}m"

/** Tanggal dari query (dd-MM-yyyy) menjadi "Sab, 14 Nov 2026". */
fun String.toDisplayDate(): String = try {
    LocalDate.parse(this, Validators.dateFormatter).format(displayDateFormatter)
} catch (e: Exception) {
    this
}

fun LocalDate.toQueryString(): String = format(Validators.dateFormatter)

fun String.toLocalDateOrNull(): LocalDate? = try {
    LocalDate.parse(this, Validators.dateFormatter)
} catch (e: Exception) {
    null
}
