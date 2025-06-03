package com.suongvong.interviewtest.ui.scan

import com.suongvong.interviewtest.model.QRData

interface ScanQRNavigator {
    fun onInvalidSecretKey()
    fun onSaveSecretKeySuccess(qrData: QRData)
    fun onUpdateSecretKeySuccess(qrData: QRData)
}