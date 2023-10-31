package com.artikdemonik.healthfood

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainVM(private val foodRepository: FoodRepository): ViewModel() {

    var name: MutableLiveData<String> = MutableLiveData<String>()

    fun getNameByID(id: Int){
        viewModelScope.launch {
             name.value = foodRepository.getNameByID(id)
        }
    }
}