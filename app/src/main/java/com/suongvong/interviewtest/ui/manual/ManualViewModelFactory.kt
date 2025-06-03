package com.suongvong.interviewtest.ui.manual

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.suongvong.interviewtest.repository.DBRepository

class ManualViewModelFactory(private val repository: DBRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ManualViewModel::class.java)) {
            return ManualViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}