package com.suongvong.interviewtest.ui.main

import com.suongvong.interviewtest.model.QRData

interface MainNavigator {
    fun onInvalidSecretKey()
    fun onSaveSecretKeySuccess(qrData: QRData)
    fun onUpdateSecretKeySuccess(qrData: QRData)
    fun onGetDataFromDeeplinkSuccess(qrData: QRData)
}