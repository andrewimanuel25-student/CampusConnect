package com.kharismastudio.campusconnect.presentation

import androidx.lifecycle.ViewModel
import com.kharismastudio.campusconnect.domain.GetCoursesUseCase
import com.kharismastudio.campusconnect.shared.MataKuliah
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCoursesUseCase: GetCoursesUseCase
) : ViewModel() {

    // MutableStateFlow menampung data secara internal yang bisa diubah
    private val _courses = MutableStateFlow<List<MataKuliah>>(emptyList())

    // StateFlow terekspos ke View (HomeScreen) sebagai data read-only yang reaktif
    val courses: StateFlow<List<MataKuliah>> = _courses

    init {
        // Mengambil data dari layer domain saat ViewModel pertama kali dibuat
        _courses.value = getCoursesUseCase()
    }
}