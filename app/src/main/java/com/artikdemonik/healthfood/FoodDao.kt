package com.artikdemonik.healthfood

import androidx.room.Dao
import androidx.room.Query

@Dao
interface FoodDao{
    @Query("SELECT NAME FROM USER WHERE ID = :id")
    suspend fun getNameByID(id: Int): String
}

