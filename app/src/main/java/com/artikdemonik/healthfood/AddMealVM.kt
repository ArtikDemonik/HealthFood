package com.artikdemonik.healthfood

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AddMealVM(private val foodRepository: FoodRepository): ViewModel() {
    val products = MutableLiveData<List<ProductDbEntity>>()

    fun addMeal(date: String, product: Int, weight: Double, userID: Int){
        viewModelScope.launch {
            foodRepository.addMeal(date, product, weight, userID)
        }
    }

    fun getProducts(){
        viewModelScope.launch {
            products.value = foodRepository.getProducts()
        }
    }
}