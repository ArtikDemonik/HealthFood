package com.artikdemonik.healthfood

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodRepository(private val foodDao: FoodDao){

    suspend fun getUsers(): List<UserDbEntity>{
        return withContext(Dispatchers.IO) {
            return@withContext foodDao.getUsers()
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