package com.suongvong.interviewtest.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "items")
data class QRData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val secretKey: String,
    val appName: String,
    val userName: String
)