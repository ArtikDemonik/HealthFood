package com.artikdemonik.healthfood

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainVM(private val foodRepository: FoodRepository): ViewModel() {

    var users = MutableLiveData<List<UserDbEntity>>()

    fun getUsers(){
        viewModelScope.launch {
            users.value = foodRepository.getUsers()
        }
    }
}