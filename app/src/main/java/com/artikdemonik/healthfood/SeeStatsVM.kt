package com.artikdemonik.healthfood

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SeeStatsVM(private val foodRepository: FoodRepository): ViewModel() {
    val user = MutableLiveData<UserDbEntity>()
    val calories = MutableLiveData<Float>()


    fun getCalories(id: Int, date: String){
        viewModelScope.launch {
            calories.value = foodRepository.getDayCalories(id, date)
        }
    }

    fun getUser(id: Int){
        viewModelScope.launch{
            user.value = foodRepository.getUser(id)
        }
    }
}