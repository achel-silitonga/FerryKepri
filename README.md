# ⛴️ FerryKepri: Jadwal Ferry Batam–Singapura

Aplikasi mobile Android (frontend only) untuk melihat jadwal ferry Batam–Singapura, menyimpan rute favorit, dan mensimulasikan pemesanan tiket.

> **Catatan:** Seluruh data jadwal, harga, dan operator pada aplikasi ini adalah **data fiktif (dummy)** yang dibuat untuk keperluan praktikum. Bukan informasi resmi operator ferry.

## 📋 Informasi Tugas
| | |
|---|---|
| Mata Kuliah | Pemrograman Perangkat Bergerak (Praktikum), RPL317 |
| Tugas | Asesmen Tengah Semester (ATS) Praktikum |
| Program Studi | Teknologi Rekayasa Perangkat Lunak |
| Kelas | TRPL 3B-Pagi |
| Nama | **Achel Verawati Silitonga** |
| NIM | **4342501047** |
| Tahun Akademik | Ganjil 2026/2027 |

## ✨ Fitur Utama
1. **Cari & filter jadwal**: pilih pelabuhan asal, tujuan, dan tanggal, lalu urutkan hasil berdasarkan jam atau harga.
2. **Favorit**: simpan dan hapus jadwal/rute langganan.
3. **Simulasi pemesanan tiket**: atur jumlah penumpang dewasa dan anak, total harga terhitung otomatis, lalu tiket konfirmasi dibuat.
4. **Riwayat tiket** *(bonus)*: lihat tiket yang sudah dipesan dan batalkan bila perlu.

## 📱 Daftar Screen
| No | Screen | Keterangan |
|---|---|---|
| 1 | Splash | Logo dan transisi otomatis ke Login |
| 2 | Login | Validasi email dan password |
| 3 | Beranda | Form cari rute dan rute populer |
| 4 | Hasil Jadwal | Daftar jadwal dengan urutan jam/harga |
| 5 | Detail Jadwal | Info kapal, jumlah penumpang, rincian biaya, pesan tiket |
| 6 | Favorit & Riwayat | Jadwal favorit dan tiket yang sudah dipesan |
| 7 | Tiket | Tiket konfirmasi dengan QR placeholder |

Ditambah tampilan **kondisi offline** saat koneksi internet terputus.

## 🛠️ Teknologi
- **Bahasa:** Kotlin
- **UI:** Jetpack Compose + Material Design 3
- **Navigasi:** Navigation Compose
- **State Management:** ViewModel + StateFlow (state bertahan saat rotasi layar dan perpindahan halaman)
- **Data:** Dummy data lokal (tanpa backend/API)
- **Desain:** Google Stitch
- **Min SDK:** 26

## 🛡️ Validasi & Penanganan Error
- Email dan password tidak boleh kosong, format email harus valid.
- Pelabuhan asal dan tujuan tidak boleh sama.
- Tanggal tidak boleh kosong, harus berformat `dd-MM-yyyy`, dan tidak boleh di masa lalu.
- Minimal 1 penumpang dewasa dan maksimal 8 penumpang per pemesanan.
- Pesan jika jadwal tidak ditemukan.
- Tampilan khusus dan tombol "Coba Lagi" saat koneksi internet terputus.
- Operasi berisiko dibungkus `try-catch` agar aplikasi tidak force close.

## 🏗️ Struktur Project
```
app/src/main/java/com/example/ferrykepri/
├── data/
│   ├── Models.kt          # Schedule, Ticket, SearchState
│   ├── DummyData.kt       # Data jadwal fiktif
│   └── Validators.kt      # Fungsi validasi input
├── viewmodel/
│   └── FerryViewModel.kt  # State dan logika fitur
├── navigation/
│   ├── Routes.kt          # Daftar rute
│   └── AppNavHost.kt      # Navigasi antar screen
├── ui/
│   ├── theme/             # Warna, tipografi, tema
│   └── screens/           # Splash, Login, Home, dst.
└── MainActivity.kt
```

## 🚀 Cara Menjalankan
1. Clone repository ini:
   ```bash
   git clone https://github.com/achel-silitonga/FerryKepri.git
   ```
2. Buka folder project dengan **Android Studio** (versi terbaru disarankan).
3. Tunggu proses **Gradle Sync** selesai.
4. Jalankan di emulator atau perangkat Android (**Run ▶**).

## 🔑 Akun Login Dummy
| Email | Password |
|---|---|
| `mahasiswa@trpl.ac.id` | `123456` |

## 🖼️ Screenshot
> Tambahkan screenshot aplikasi di folder `screenshots/`, lalu tampilkan di sini.

| Splash | Login | Beranda |
|---|---|---|
| ![Splash](<img width="336" height="763" alt="image" src="https://github.com/user-attachments/assets/16d0c6a8-f175-4467-a4ec-89f1f74f07d6" />
) | ![Login](screenshots/login.png) | ![Beranda](screenshots/home.png) |

| Hasil Jadwal | Detail | Tiket |
|---|---|---|
| ![Hasil](screenshots/results.png) | ![Detail](screenshots/detail.png) | ![Tiket](screenshots/ticket.png) |

## 🎨 Desain UI/UX
Desain dibuat dengan Google Stitch bertema biru modern. Beberapa elemen pada desain awal (misalnya status "live", login Google/Singpass, dan data paspor) disederhanakan atau hanya ditampilkan secara statis agar sesuai dengan cakupan praktikum.

## 🎥 Vlog Pengerjaan
Link video: **(tempel link vlog di sini)**

## 📄 Lisensi
Dibuat untuk keperluan akademik.
