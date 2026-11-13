package com.example.ferrykepri.data

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 * Kumpulan fungsi validasi. Semua fungsi mengembalikan pesan kesalahan
 * berupa String, atau null apabila masukan sudah valid.
 */
object Validators {

    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    private val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    fun login(email: String, password: String): String? {
        if (email.isBlank()) return "Email tidak boleh kosong"
        if (!emailRegex.matches(email.trim())) return "Format email tidak sesuai"
        if (password.isBlank()) return "Kata sandi tidak boleh kosong"
        return null
    }

    fun isDummyAccount(email: String, password: String): Boolean =
        email.trim().equals(DummyData.DUMMY_EMAIL, ignoreCase = true) &&
            password == DummyData.DUMMY_PASSWORD

    fun search(origin: String, destination: String, date: String): String? {
        if (origin.isBlank()) return "Pilih pelabuhan asal"
        if (destination.isBlank()) return "Pilih pelabuhan tujuan"
        if (origin == destination) return "Asal dan tujuan tidak boleh sama"
        if (date.isBlank()) return "Tanggal tidak boleh kosong"
        return try {
            val d = LocalDate.parse(date, dateFormatter)
            if (d.isBefore(LocalDate.now())) "Tanggal tidak boleh di masa lalu" else null
        } catch (e: DateTimeParseException) {
            "Format tanggal harus dd-MM-yyyy"
        }
    }

    /** Dewasa minimal 1 dan total penumpang maksimal 8 orang. */
    fun booking(adults: Int, children: Int): String? {
        if (adults < 1) return "Jumlah penumpang dewasa minimal 1"
        if (children < 0) return "Jumlah penumpang anak tidak valid"
        if (adults + children > 8) return "Total penumpang maksimal 8 orang"
        return null
    }
}
