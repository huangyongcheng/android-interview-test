package com.suongvong.interviewtest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.suongvong.interviewtest.database.QRDataDao
import com.suongvong.interviewtest.model.QRData

class DBRepository(private val qrDataDao: QRDataDao):ViewModel() {

    val allQRData: LiveData<List<QRData>> = qrDataDao.getAll()

    suspend fun insert(qrData: QRData, isInsert:(Boolean)->Unit) {

        val existingItem = qrDataDao.getItemByAppAndUser(qrData.appName, qrData.userName)
        if (existingItem != null) {
            val updatedQRData = existingItem.copy(secretKey = qrData.secretKey)
            qrDataDao.updateItem(updatedQRData)
            isInsert.invoke(false)
        } else {
            qrDataDao.insert(qrData)
            isInsert.invoke(true)
        }
    }

    suspend fun delete(qrData: QRData) {
        qrDataDao.delete(qrData)
    }
}