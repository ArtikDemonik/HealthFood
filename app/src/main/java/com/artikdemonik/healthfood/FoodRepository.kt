package com.artikdemonik.healthfood

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.job
import kotlinx.coroutines.withContext

class FoodRepository(private val foodDao: FoodDao){

    suspend fun getUsers(): List<UserDbEntity>{
        return withContext(Dispatchers.IO) {
            return@withContext foodDao.getUsers()
        }
    }

    suspend fun getProducts(): List<ProductDbEntity>{
        return withContext(Dispatchers.IO){
            return@withContext foodDao.getProducts()
        }
    }

    suspend fun getMeals(id: Int, date: String): List<Meal>{
        return withContext(Dispatchers.IO){
            return@withContext foodDao.getMeals(id, date)
        }
    }

    suspend fun getDayCalories(id: Int, date: String): Float{
        return withContext(Dispatchers.IO) {
            var calories = 0.0f
            var meals = foodDao.getMeals(id, date)
            if (meals.isEmpty()) {
                meals = listOf(Meal(-1, 24, 0.0, 0, date))
            }
            meals.forEach {
                println(it.product)
                val productCalories: Float =
                    foodDao.getProductById(it.product).calories * it.weight.toFloat()
                calories = calories.plus(productCalories)
            }
            return@withContext calories
        }
    }
    suspend fun getProductById(id: Int): ProductDbEntity{
        return withContext(Dispatchers.IO){
            return@withContext foodDao.getProductById(id)
        }
    }

    suspend fun addMeal(date: String, product: Int, weight: Double, user: Int){
        withContext(Dispatchers.IO){
            foodDao.addMeal(date, product, weight, user)
        }
    }

    suspend fun getUser(id: Int): UserDbEntity{
        return withContext(Dispatchers.IO){
            return@withContext foodDao.getUser(id)
        }
    }
    suspend fun addUser(user: UserTuple){
        withContext(Dispatchers.IO){
            foodDao.addUser(user.name, user.age, user.height, user.weight, user.needCalories)
        }
    }
}