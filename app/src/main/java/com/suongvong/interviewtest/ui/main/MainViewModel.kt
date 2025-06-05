package com.suongvong.interviewtest.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.suongvong.interviewtest.model.QRData
import com.suongvong.interviewtest.repository.DBRepository
import com.suongvong.interviewtest.ui.base.BaseViewModel
import com.suongvong.interviewtest.utils.QRProcessor
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DBRepository) : BaseViewModel<MainNavigator>() {

    val allQRData: LiveData<List<QRData>> = repository.allQRData

    fun delete(qrData: QRData) = viewModelScope.launch {
        repository.delete(qrData)
    }


}