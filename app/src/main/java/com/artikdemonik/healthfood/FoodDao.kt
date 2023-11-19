package com.artikdemonik.healthfood

import androidx.room.Dao
import androidx.room.Query

@Dao
interface FoodDao{
    @Query("SELECT * FROM USER")
    suspend fun getUsers(): List<UserDbEntity>
    @Query("select * from meal where user=:id AND date=:date")
    suspend fun getMeals(id: Int, date: String): List<Meal>

    @Query("select * from product")
    suspend fun getProducts(): List<ProductDbEntity>

    @Query("select * from product where id=:id")
    suspend fun getProductById(id: Int): ProductDbEntity

    @Query("insert into meal (product, weight, user, date) VALUES (:product, :weight, :user, :date)")
    suspend fun addMeal(date: String, product: Int, weight: Double, user: Int)

    @Query("SELECT * FROM USER WHERE id=:id")
    suspend fun getUser(id: Int): UserDbEntity

    @Query("INSERT INTO USER (name, age, height, weight, needsCalories) VALUES (:name, :age, :height, :weight, :needsCalories)")
    suspend fun addUser(name: String, age: Int, height: Int, weight: Int, needsCalories: Int)
}

