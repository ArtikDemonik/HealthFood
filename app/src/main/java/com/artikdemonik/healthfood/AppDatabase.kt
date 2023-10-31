package com.artikdemonik.healthfood

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [
        UserDbEntity::class,
        ProductDbEntity::class,
        DailyConsumption::class
    ]
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getDao(): FoodDao
}