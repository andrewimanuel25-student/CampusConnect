(1) mengapa CampusConnect pada tahap ini dibangun dengan pendekatan native (Kotlin + Compose) dan bukan Flutter/hybrid?
Jawab:
Ada beberapa alasan strategis mengapa pendekatan Native (Kotlin + Jetpack Compose) dipilih untuk tahap awal pengembangan CampusConnect:
-> Performa Maksimal dan Responsif (Zero Overhead): Aplikasi native dikompilasi langsung ke kode mesin Android. Tidak ada lapisan penerjemah (bridge) seperti pada framework hybrid. Untuk aplikasi kampus yang nantinya mungkin mengolah data akademik, jadwal real-time, atau navigasi peta kampus, performa native memastikan aplikasi tetap mulus dan hemat baterai.
-> Integrasi Fitur Hardware dan OS yang Mendalam: Aplikasi kampus sering kali membutuhkan fitur bawaan ponsel seperti GPS (untuk absensi berbasis lokasi/geofencing), Kamera (untuk scan QR code fasilitas kampus), atau Notifikasi Sistem (untuk pengingat kelas). Native memberikan akses instan dan paling stabil ke API Android terbaru tanpa harus menunggu plugin pihak ketiga diperbarui.
-> Modern UI dengan Jetpack Compose: Jetpack Compose adalah toolkit modern dari Google yang menggunakan pendekatan deklaratif (mirip seperti Flutter). Membuat UI menjadi jauh lebih cepat, kode lebih sedikit, dan desainnya otomatis mengikuti standar Material Design terbaru milik Google secara presisi.
-> Fokus Optimasi di Satu Platform Dahulu: Memulai dengan native Android (Kotlin) memungkinkan tim/Anda untuk fokus mematangkan fitur utama (core functionalities) dan memvalidasi kebutuhan pengguna (mahasiswa/dosen) tanpa terdistraksi oleh bug lintas platform yang sering terjadi di framework hybrid.


(2) apa konsekuensi teknis dan biaya yang harus ditanggung apabila di masa depan aplikasi ini juga perlu dirilis untuk iOS, dikaitkan dengan materi evolusi arsitektur mobile pada bagian A.
Jawab:
-> Harus menulis ulang 100% kode di iOS. Memelihara 2 codebase terpisah.
-> Reuse Kode ~0%
-> Biaya Paling Tinggi (Butuh waktu 2x lebih lama atau tambah SDM iOS).