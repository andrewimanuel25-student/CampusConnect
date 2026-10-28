# Progress Praktikum - Modul 4
## Refactoring Arsitektur: MVVM + Clean Architecture + Hilt

### 1. Diagram Alur Data
Berikut adalah alur komunikasi antar-komponen setelah dilakukan refactoring arsitektur pada aplikasi CampusConnect:

┌────────────────────────────────────────────────────────┐
│                   PRESENTATION LAYER                   │
│  [HomeScreen (UI/View)] ──> [HomeViewModel (State)]    │
└───────────────────────────────┬────────────────────────┘
                                │ (Mengonsumsi UseCase)
┌───────────────────────────────▼────────────────────────┐
│                      DOMAIN LAYER                      │
│                [GetCoursesUseCase]                     │
└───────────────────────────────┬────────────────────────┘
                                │ (Mengakses Abstraksi / Interface)
┌───────────────────────────────▼────────────────────────┐
│                      DATA LAYER                        │
│         [CourseRepository] <── [CourseRepositoryImpl]  │
│         (di modul :shared)        (di modul :shared)   │
└────────────────────────────────────────────────────────┘

### 2. Analisis Singkat Komponen
*   **Presentation Layer:** `HomeScreen` bertindak sebagai View deklaratif murni yang meng-collect data dari `HomeViewModel` secara reaktif menggunakan StateFlow.
*   **Domain Layer:** `GetCoursesUseCase` bertindak sebagai jembatan aturan bisnis murni (Pure Kotlin) yang tidak terikat oleh framework Android maupun library eksternal.
*   **Data Layer:** `CourseRepositoryImpl` di dalam modul `:shared` bertindak sebagai penyedia data konkret.
*   **Dependency Injection:** Seluruh instansiasi objek diatur dan disuntikkan secara otomatis oleh Hilt di tingkat compile-time untuk menjamin keamanan tipe data dan efisiensi performa runtime.
