package com.example.ferrykepri.data

/**
 * Model jadwal feri. Satu objek merepresentasikan satu keberangkatan
 * dari pelabuhan asal menuju pelabuhan tujuan.
 */
data class Schedule(
    val id: Int,
    val operator: String,
    val origin: String,
    val destination: String,
    val departTime: String,
    val durationMin: Int,
    val priceAdult: Int,
    val priceChild: Int,
    val vessel: String,
    val facilities: List<String>,
    val seatsLeft: Int = 0,
    val tag: String? = null
)

/**
 * Tiket hasil simulasi pemesanan.
 */
data class Ticket(
    val id: String,
    val schedule: Schedule,
    val date: String,
    val adults: Int,
    val children: Int,
    val total: Int,
    val passengerName: String = "Penumpang Dewasa"
) {
    val passengerCount: Int get() = adults + children
}

/**
 * Kondisi hasil pencarian agar UI bisa beradaptasi tanpa crash.
 */
sealed interface SearchState {
    data object Idle : SearchState
    data object Loading : SearchState
    data class Success(val results: List<Schedule>) : SearchState
    data object Empty : SearchState
    data class Error(val message: String) : SearchState
}

/**
 * Query pencarian terakhir, dipakai untuk menampilkan header hasil.
 * Tanggal disimpan dalam format dd-MM-yyyy.
 */
data class SearchQuery(
    val origin: String,
    val destination: String,
    val date: String
)

enum class SortOption(val label: String) {
    TIME("Jam"),
    PRICE("Harga")
}
