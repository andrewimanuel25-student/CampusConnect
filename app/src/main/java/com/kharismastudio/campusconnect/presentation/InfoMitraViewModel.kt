package com.kharismastudio.campusconnect.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kharismastudio.campusconnect.data.repository.InfoMitraRepository
import com.kharismastudio.campusconnect.data.repository.NegaraMitra
import com.kharismastudio.campusconnect.data.repository.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class InfoMitraUiState {
    object Loading : InfoMitraUiState()
    data class Success(
        val data: List<NegaraMitra>
    ) : InfoMitraUiState()
    data class Error(
        val message: String
    ) : InfoMitraUiState()
}

@HiltViewModel
class InfoMitraViewModel @Inject constructor(
    private val repository: InfoMitraRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<InfoMitraUiState>(
            InfoMitraUiState.Loading
        )
    val uiState: StateFlow<InfoMitraUiState> =
        _uiState

    init {
        muatData()
    }

    fun muatData() {
        viewModelScope.launch {
            _uiState.value =
                InfoMitraUiState.Loading

            _uiState.value =
                when (val result = repository.getCountries()) {
                    is Result.Success -> {
                        InfoMitraUiState.Success(
                            result.data
                        )
                    }
                    is Result.Error -> {
                        InfoMitraUiState.Error(
                            result.message
                        )
                    }
                }
        }
    }
}
