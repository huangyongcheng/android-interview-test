package com.suongvong.interviewtest

import android.app.Application
import com.suongvong.interviewtest.database.AppDatabase
import com.suongvong.interviewtest.repository.DBRepository

class NewsApplication: Application() {
    private val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { DBRepository(database.qrDataDao()) }
}