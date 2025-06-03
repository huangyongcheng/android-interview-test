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


    fun save(qrData: QRData) {
        val view = getNavigator() ?: return
        viewModelScope.launch {
            repository.insert(qrData) { isInsert ->
                if (isInsert) {
                    view.onSaveSecretKeySuccess(qrData)
                } else {
                    view.onUpdateSecretKeySuccess(qrData)
                }
            }

        }
    }

    fun onProcessDeeplinkData(qrUrl: String) {
        val view = getNavigator() ?: return
        QRProcessor.processScannedData(qrUrl,
            onSuccess = { qrData ->
                view.onGetDataFromDeeplinkSuccess(qrData)
            },
            onError = {
                view.onInvalidSecretKey()
            }
        )
    }
}