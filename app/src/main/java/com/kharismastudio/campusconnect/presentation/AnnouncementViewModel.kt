package com.kharismastudio.campusconnect.presentation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kharismastudio.campusconnect.data.repository.AnnouncementRepository
import com.kharismastudio.campusconnect.data.repository.Result
import com.kharismastudio.shared.Pengumuman
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
sealed class AnnouncementUiState {
    object Loading : AnnouncementUiState()

    data class Success(
        val data: List<Pengumuman>
    ) : AnnouncementUiState()
    data class Error(
        val message: String
    ) : AnnouncementUiState()
}
@HiltViewModel
class AnnouncementViewModel @Inject constructor(
    private val repository: AnnouncementRepository
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<AnnouncementUiState>(
            AnnouncementUiState.Loading
        )
    val uiState: StateFlow<AnnouncementUiState> =
        _uiState
    init {
        muatUlang()
    }
    fun muatUlang() {
        viewModelScope.launch {
            _uiState.value =
                AnnouncementUiState.Loading
            _uiState.value =
                when (
                    val hasil =
                        repository.getAnnouncements()
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