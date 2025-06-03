package com.suongvong.interviewtest.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.suongvong.interviewtest.model.QRData

@Dao
interface QRDataDao {

    @Delete
    suspend fun delete(qrData: QRData)

    @Insert
    suspend fun insert(qrData: QRData)

    @Update
    suspend fun updateItem(qrData: QRData)

    @Query("SELECT * FROM items ORDER BY id DESC")
    fun getAll(): LiveData<List<QRData>>

    @Query("SELECT * FROM items WHERE appName = :appName AND userName = :userName LIMIT 1")
    suspend fun getItemByAppAndUser(appName: String, userName: String): QRData?
}