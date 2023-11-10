package com.artikdemonik.healthfood

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AddUserVM(private val foodRepository: FoodRepository): ViewModel() {
    fun addUser(user: UserTuple){
        viewModelScope.launch {
            foodRepository.addUser(user)
        }
    }
}