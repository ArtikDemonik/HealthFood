package com.artikdemonik.healthfood

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SeeStatsVM(private val foodRepository: FoodRepository): ViewModel() {
    val user = MutableLiveData<UserDbEntity>()

    fun getUser(id: Int){
        viewModelScope.launch{
            user.value = foodRepository.getUser(id)
        }
    }
}