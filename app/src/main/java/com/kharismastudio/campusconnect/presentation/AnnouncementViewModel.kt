package com.kharisma.campusconnect.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kharisma.campusconnect.data.repository.AnnouncementRepository
import com.kharismastudio.shared.Pengumuman
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnnouncementViewModel @Inject constructor(
    private val repository: AnnouncementRepository
) : ViewModel() {

    private val _announcements = MutableStateFlow<List<Pengumuman>>(emptyList())
    val announcements: StateFlow<List<Pengumuman>> = _announcements.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _refreshError = MutableStateFlow<String?>(null)
    val refreshError: StateFlow<String?> = _refreshError.asStateFlow()

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
                _refreshError.value =
                    "Gagal memperbarui pengumuman. Data tersimpan tetap ditampilkan."
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}
