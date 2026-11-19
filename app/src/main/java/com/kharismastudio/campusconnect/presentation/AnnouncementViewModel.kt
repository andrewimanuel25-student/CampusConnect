package com.kharismastudio.campusconnect.presentation

//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.kharismastudio.campusconnect.data.repository.AnnouncementRepository
//import com.kharismastudio.shared.Pengumuman
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import javax.inject.Inject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kharismastudio.campusconnect.data.repository.AnnouncementRepository
import com.kharismastudio.shared.Pengumuman
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.kharismastudio.campusconnect.data.repository.Result

//@HiltViewModel
//class AnnouncementViewModel @Inject constructor(
//    private val repository: AnnouncementRepository
//) : ViewModel() {
//
//    private val _announcements = MutableStateFlow<List<Pengumuman>>(emptyList())
//    val announcements: StateFlow<List<Pengumuman>> = _announcements.asStateFlow()
//
//    private val _isRefreshing = MutableStateFlow(false)
//    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()
//
//    private val _refreshError = MutableStateFlow<String?>(null)
//    val refreshError: StateFlow<String?> = _refreshError.asStateFlow()
//
//    init {
//        viewModelScope.launch {
//            repository.observeAnnouncements().collect { data ->
//                _announcements.value = data
//            }
//        }
//        refreshAnnouncements()
//    }
//
//    fun refreshAnnouncements() {
//        viewModelScope.launch {
//            _isRefreshing.value = true
//            _refreshError.value = null
//            try {
//                repository.refresh()
//            } catch (e: Exception) {
//                _refreshError.value =
//                    "Gagal memperbarui pengumuman. Data tersimpan tetap ditampilkan."
//            } finally {
//                _isRefreshing.value = false
//            }
//        }
//    }
//}

sealed class AnnouncementUiState {
    object Loading : AnnouncementUiState()

    data class Success(
        val data: List<Pengumuman>
    ) : AnnouncementUiState()

    data class Error(
        val message: String
    ) : AnnouncementUiState()
}

//MODUL 5
//@HiltViewModel
//class AnnouncementViewModel @Inject constructor(
//    private val repository: AnnouncementRepository
//) : ViewModel() {
//    private val _uiState = MutableStateFlow<AnnouncementUiState>(
//        AnnouncementUiState.Loading
//    )
//
//    val uiState: StateFlow<AnnouncementUiState> = _uiState
//
//    init {
//        muatUlang()
//    }
//
//    fun muatUlang() {
//        viewModelScope.launch {
//            _uiState.value = AnnouncementUiState.Loading
//
//            _uiState.value = when (
//                val hasil = repository.getAnnouncements()
//            ) {
//                is Result.Success -> {
//                    AnnouncementUiState.Success(
//                        hasil.data
//                    )
//                }
//
//                is Result.Error -> {
//                    AnnouncementUiState.Error(
//                        hasil.message
//                    )
//                }
//            }
//        }
//    }
//}

//MODUL 6
@HiltViewModel
class AnnouncementViewModel @Inject constructor(
    private val repository: AnnouncementRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<AnnouncementUiState>(
        AnnouncementUiState.Loading
    )

    val uiState: StateFlow<AnnouncementUiState> = _uiState

    private val _announcements =
        MutableStateFlow<List<Pengumuman>>(emptyList())

    val announcements: StateFlow<List<Pengumuman>> =
        _announcements.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _refreshError = MutableStateFlow<String?>(null)

    val refreshError: StateFlow<String?> = _refreshError.asStateFlow()

    init {
        muatUlang()
    }

    init {
        viewModelScope.launch {
            repository.observeAnnouncements().collect { data ->
                _announcements.value = data
            }
        }

        refreshAnnouncements()
    }

    fun refreshAnnouncements() {
        viewModelScope.launch {
            _isRefreshing.value = true
            _refreshError.value = null

            try {
                repository.refresh()
            } catch (e: Exception) {
                _refreshError.value = "Gagal memperbarui pengumuman. Data tersimpan tetap ditampilkan." }
            finally {
                _isRefreshing.value = false
            }
        }
    }

        fun muatUlang() {
        viewModelScope.launch {
            _uiState.value = AnnouncementUiState.Loading

            _uiState.value = when (
                val hasil = repository.getAnnouncements()
            ) {
                is Result.Success -> {
                    AnnouncementUiState.Success(
                        hasil.data
                    )
                }

                is Result.Error -> {
                    AnnouncementUiState.Error(
                        hasil.message
                    )
                }
            }
        }
    }
}