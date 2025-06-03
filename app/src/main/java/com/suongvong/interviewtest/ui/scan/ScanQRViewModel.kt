package com.suongvong.interviewtest.ui.scan

import androidx.lifecycle.viewModelScope
import com.suongvong.interviewtest.repository.DBRepository
import com.suongvong.interviewtest.ui.base.BaseViewModel
import com.suongvong.interviewtest.utils.QRProcessor
import kotlinx.coroutines.launch

class ScanQRViewModel(private val repository: DBRepository) : BaseViewModel<ScanQRNavigator>() {

    fun onProcessScannedData(qrUrl: String) {
        val view = getNavigator() ?: return
        QRProcessor.processScannedData(qrUrl,
            onSuccess = { qrData ->
                viewModelScope.launch {
                    repository.insert(qrData) { isInsert ->
                        if (isInsert) {
                            view.onSaveSecretKeySuccess(qrData)
                        } else {
                            view.onUpdateSecretKeySuccess(qrData)
                        }
                    }

                }
            },
            onError = {
                view.onInvalidSecretKey()
            }
        )
    }
}