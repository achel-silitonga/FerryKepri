package com.example.ferrykepri.data

/**
 * Seluruh data operasional aplikasi disimpan lokal di dalam kode (dummy data).
 */
object DummyData {

    const val DUMMY_EMAIL = "mahasiswa@trpl.ac.id"
    const val DUMMY_PASSWORD = "123456"

    val batamPorts = listOf(
        "Batam Centre",
        "Harbour Bay",
        "Sekupang",
        "Waterfront City"
    )

    val singaporePorts = listOf(
        "HarbourFront",
        "Tanah Merah"
    )

    val ports: List<String> = batamPorts + singaporePorts

    val operators = listOf(
        "Batam Fast",
        "Horizon Fast Ferry",
        "Sindo Ferry",
        "Majestic Fast Ferry"
    )

    val schedules: List<Schedule> = listOf(
        // ---- Batam Centre -> HarbourFront ----
        Schedule(
            id = 1,
            operator = "Batam Fast",
            origin = "Batam Centre",
            destination = "HarbourFront",
            departTime = "08:30",
            durationMin = 70,
            priceAdult = 430_000,
            priceChild = 330_000,
            vessel = "MV Ocean Dragon",
            facilities = listOf("AC", "Bagasi 20 kg", "Tempat Duduk Luas"),
            seatsLeft = 14,
            tag = "Paling Cepat"
        ),
        Schedule(
            id = 2,
            operator = "Majestic Fast Ferry",
            origin = "Batam Centre",
            destination = "HarbourFront",
            departTime = "09:45",
            durationMin = 70,
            priceAdult = 450_000,
            priceChild = 350_000,
            vessel = "Majestic Flair",
            facilities = listOf("AC", "Bagasi 20 kg", "USB Charging", "Tempat Duduk Luas"),
            seatsLeft = 22,
            tag = "Populer"
        ),
        Schedule(
            id = 3,
            operator = "Sindo Ferry",
            origin = "Batam Centre",
            destination = "HarbourFront",
            departTime = "10:40",
            durationMin = 70,
            priceAdult = 420_000,
            priceChild = 320_000,
            vessel = "Sindo Express",
            facilities = listOf("AC", "Bagasi 15 kg", "Boarding Gate 02"),
            seatsLeft = 9,
            tag = "Pagi Terakhir"
        ),
        Schedule(
            id = 4,
            operator = "Batam Fast",
            origin = "Batam Centre",
            destination = "HarbourFront",
            departTime = "12:30",
            durationMin = 70,
            priceAdult = 430_000,
            priceChild = 330_000,
            vessel = "MV Wavemaster 5",
            facilities = listOf("AC", "Bagasi 20 kg", "Snack"),
            seatsLeft = 8,
            tag = "Siang"
        ),

        // ---- HarbourFront -> Batam Centre ----
        Schedule(
            id = 5,
            operator = "Batam Fast",
            origin = "HarbourFront",
            destination = "Batam Centre",
            departTime = "08:10",
            durationMin = 70,
            priceAdult = 430_000,
            priceChild = 330_000,
            vessel = "MV Ocean Dragon",
            facilities = listOf("AC", "Bagasi 20 kg", "Tempat Duduk Luas"),
            seatsLeft = 16,
            tag = "Pagi"
        ),
        Schedule(
            id = 6,
            operator = "Majestic Fast Ferry",
            origin = "HarbourFront",
            destination = "Batam Centre",
            departTime = "09:45",
            durationMin = 70,
            priceAdult = 450_000,
            priceChild = 350_000,
            vessel = "Majestic Glory",
            facilities = listOf("AC", "Bagasi 20 kg", "USB Charging", "Snack"),
            seatsLeft = 20,
            tag = "Populer"
        ),
        Schedule(
            id = 7,
            operator = "Sindo Ferry",
            origin = "HarbourFront",
            destination = "Batam Centre",
            departTime = "10:40",
            durationMin = 70,
            priceAdult = 420_000,
            priceChild = 320_000,
            vessel = "Sindo Express",
            facilities = listOf("AC", "Bagasi 15 kg", "Tempat Duduk Luas"),
            seatsLeft = 11
        ),
        Schedule(
            id = 8,
            operator = "Sindo Ferry",
            origin = "HarbourFront",
            destination = "Batam Centre",
            departTime = "18:30",
            durationMin = 70,
            priceAdult = 420_000,
            priceChild = 320_000,
            vessel = "Sindo Express",
            facilities = listOf("AC", "Bagasi 15 kg", "Boarding Gate 4B"),
            seatsLeft = 6,
            tag = "Sore"
        ),

        // ---- Harbour Bay <-> HarbourFront ----
        Schedule(
            id = 9,
            operator = "Horizon Fast Ferry",
            origin = "Harbour Bay",
            destination = "HarbourFront",
            departTime = "07:30",
            durationMin = 65,
            priceAdult = 410_000,
            priceChild = 310_000,
            vessel = "Horizon Star",
            facilities = listOf("AC", "Bagasi 20 kg", "Tempat Duduk Luas"),
            seatsLeft = 12,
            tag = "Pagi"
        ),
        Schedule(
            id = 10,
            operator = "Horizon Fast Ferry",
            origin = "HarbourFront",
            destination = "Harbour Bay",
            departTime = "16:30",
            durationMin = 65,
            priceAdult = 410_000,
            priceChild = 310_000,
            vessel = "Horizon Star",
            facilities = listOf("AC", "Bagasi 20 kg", "Snack"),
            seatsLeft = 10,
            tag = "Sore"
        ),

        // ---- Sekupang <-> Tanah Merah ----
        Schedule(
            id = 11,
            operator = "Batam Fast",
            origin = "Sekupang",
            destination = "Tanah Merah",
            departTime = "09:00",
            durationMin = 80,
            priceAdult = 400_000,
            priceChild = 300_000,
            vessel = "MV Ocean Dragon",
            facilities = listOf("AC", "Bagasi 20 kg", "Tempat Duduk Luas"),
            seatsLeft = 18
        ),
        Schedule(
            id = 12,
            operator = "Batam Fast",
            origin = "Tanah Merah",
            destination = "Sekupang",
            departTime = "14:00",
            durationMin = 80,
            priceAdult = 400_000,
            priceChild = 300_000,
            vessel = "MV Ocean Dragon",
            facilities = listOf("AC", "Bagasi 20 kg", "Snack"),
            seatsLeft = 15
        ),

        // ---- Waterfront City <-> Tanah Merah ----
        Schedule(
            id = 13,
            operator = "Majestic Fast Ferry",
            origin = "Waterfront City",
            destination = "Tanah Merah",
            departTime = "11:00",
            durationMin = 75,
            priceAdult = 420_000,
            priceChild = 320_000,
            vessel = "Majestic Flair",
            facilities = listOf("AC", "Bagasi 20 kg", "USB Charging"),
            seatsLeft = 13,
            tag = "Populer"
        ),
        Schedule(
            id = 14,
            operator = "Majestic Fast Ferry",
            origin = "Tanah Merah",
            destination = "Waterfront City",
            departTime = "17:30",
            durationMin = 75,
            priceAdult = 420_000,
            priceChild = 320_000,
            vessel = "Majestic Flair",
            facilities = listOf("AC", "Bagasi 20 kg", "Tempat Duduk Luas"),
            seatsLeft = 9
        )
    )

    fun scheduleById(id: Int): Schedule? = schedules.firstOrNull { it.id == id }

    fun destinationsFrom(origin: String): List<String> = schedules
        .filter { it.origin == origin }
        .map { it.destination }
        .distinct()

    fun popularRoutes(): List<Pair<String, String>> = listOf(
        "Batam Centre" to "HarbourFront",
        "Sekupang" to "Tanah Merah",
        "Harbour Bay" to "HarbourFront"
    )
}
