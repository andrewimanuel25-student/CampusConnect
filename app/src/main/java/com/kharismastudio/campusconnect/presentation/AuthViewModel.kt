package com.kharismastudio.campusconnect.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _isLoggedIn =
        MutableStateFlow(auth.currentUser != null)

    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _isLoading = MutableStateFlow(false)

    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)

    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _errorMessage.value = "Email dan password wajib diisi."
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    _isLoggedIn.value = true
                    _isLoading.value = false
                }
                .addOnFailureListener {
                    _errorMessage.value =
                        it.localizedMessage ?: "Login gagal."
                    _isLoading.value = false
                }
        }
    }

    fun register(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _errorMessage.value = "Email dan password wajib diisi."
            return
        }
        if (password.length < 6) {
            _errorMessage.value = "Password minimal 6 karakter."
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    _isLoggedIn.value = true
                    _isLoading.value = false
                }
                .addOnFailureListener {
                    _errorMessage.value =
                        it.localizedMessage ?: "Registrasi gagal."
                    _isLoading.value = false
                }
        }
    }

    fun logout() {
        auth.signOut()
        _isLoggedIn.value = false
        _errorMessage.value = null
    }
}