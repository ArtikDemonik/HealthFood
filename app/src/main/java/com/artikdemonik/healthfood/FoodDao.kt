package com.artikdemonik.healthfood

import androidx.room.Dao
import androidx.room.Query

@Dao
interface FoodDao{
    @Query("SELECT * FROM USER")
    suspend fun getUsers(): List<UserDbEntity>

    @Query("SELECT * FROM USER WHERE id=:id")
    suspend fun getUser(id: Int): UserDbEntity

    @Query("INSERT INTO USER (name, age, height, weight, needsCalories) VALUES (:name, :age, :height, :weight, :needsCalories)")
    suspend fun addUser(name: String, age: Int, height: Int, weight: Int, needsCalories: Int)
}

