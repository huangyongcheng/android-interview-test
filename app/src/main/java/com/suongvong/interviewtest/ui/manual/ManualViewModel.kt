package com.suongvong.interviewtest.ui.manual

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suongvong.interviewtest.model.QRData
import com.suongvong.interviewtest.repository.DBRepository
import kotlinx.coroutines.launch

class ManualViewModel (  private val repository: DBRepository) : ViewModel() {

    fun insert(qrData: QRData) = viewModelScope.launch {
        repository.insert(qrData){

        }
    }


}