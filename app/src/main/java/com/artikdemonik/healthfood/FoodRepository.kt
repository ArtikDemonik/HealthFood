package com.artikdemonik.healthfood

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodRepository(private val foodDao: FoodDao){

    suspend fun getNameByID(id: Int): String{
        return withContext(Dispatchers.IO) {
            return@withContext foodDao.getNameByID(id)
        }
    }
}