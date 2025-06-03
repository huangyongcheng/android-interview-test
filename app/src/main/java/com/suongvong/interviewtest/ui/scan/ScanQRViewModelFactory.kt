package com.suongvong.interviewtest.ui.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.suongvong.interviewtest.repository.DBRepository

class ScanQRViewModelFactory (private val repository: DBRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScanQRViewModel::class.java)) {
            return ScanQRViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}