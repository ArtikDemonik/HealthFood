package com.artikdemonik.healthfood

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 3,
    entities = [
        UserDbEntity::class,
        ProductDbEntity::class,
        DailyConsumption::class,
        Meal::class
    ]
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getDao(): FoodDao
}