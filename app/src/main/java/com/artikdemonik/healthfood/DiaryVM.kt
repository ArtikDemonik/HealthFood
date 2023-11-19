package com.artikdemonik.healthfood

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

data class MealNote(
    val id: Int,
    val product: String,
    val weight: Double,
    val fats: Int,
    val carbohydrates: Int,
    val proteins: Int,
    val calories: Float
)

class DiaryVM(private val foodRepository: FoodRepository): ViewModel() {
    val userMeals = MutableLiveData<List<MealNote>>()


    fun getMeals(id: Int, date: String){
        viewModelScope.launch {
            val meals = foodRepository.getMeals(id, date)

            if (meals.isEmpty()){
                userMeals.value = listOf(MealNote(0, "Здесь ничего нет", 0.0, 0, 0, 0, 0.0f))
            }
            else {
                val updatedUserMeals = meals.map {
                    val prod = foodRepository.getProductById(it.product)
                    MealNote(
                        it.id.toInt(),
                        prod.name,
                        it.weight,
                        prod.fats,
                        prod.carbohydrates,
                        prod.proteins,
                        prod.calories * it.weight.toFloat()
                    )
                }
                userMeals.value = userMeals.value.orEmpty() + updatedUserMeals
            }
        }
    }



}