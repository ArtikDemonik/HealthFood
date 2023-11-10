package com.artikdemonik.healthfood

import android.content.Context
import androidx.room.Room

object Dependencies {

    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }

    private val appDatabase: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db")
            .createFromAsset("health.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    val foodRepository: FoodRepository by lazy { FoodRepository(appDatabase.getDao()) }
}