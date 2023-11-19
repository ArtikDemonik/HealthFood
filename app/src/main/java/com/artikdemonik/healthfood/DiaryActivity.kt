package com.artikdemonik.healthfood

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artikdemonik.healthfood.ui.theme.HealthFoodTheme

class DiaryActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val date = intent.getStringExtra("date")!!
        val viewModel = DiaryVM(Dependencies.foodRepository)
        val user_id = intent.getIntExtra("user_id", -1)
        viewModel.getMeals(user_id, date)

        setContent {
            var meals by remember{
                mutableStateOf(listOf(MealNote(0, "Здесь ничего нет", 0.0, 0, 0, 0, 0.0f)))
            }

            viewModel.userMeals.observe(this){
                meals = it!!
            }

            LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues.Absolute(4.dp, 4.dp, 4.dp, 2.dp), verticalArrangement = Arrangement.Center){
                items(meals){meal->
                    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                        Column (modifier = Modifier.padding(10.dp)){
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = meal.product)
                                Text(text = meal.calories.toString())
                            }
                            Divider()
                            Text(text = "Пищевая ценность:")
                            OutlinedCard(modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)) {
                                Column(modifier = Modifier.padding(3.dp)) {
                                    Text(text = "Белки: ${meal.proteins}")
                                    Text(text = "Жиры: ${meal.fats}")
                                    Text(text = "Углеводы: ${meal.carbohydrates}")
                                }
                            }

                        }
                    }
                    Spacer(modifier = Modifier.size(5.dp))
                }
                item {
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            val intent = Intent(this@DiaryActivity, AddMealActivity::class.java)
                            intent.putExtra("user", user_id)
                            intent.putExtra("date", date)
                            startActivity(intent)
                        }
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()){
                            Box(modifier = Modifier
                                .clip(CircleShape)
                                .border(1.dp, Color.DarkGray, CircleShape)){
                                Icon(Icons.TwoTone.Add, contentDescription = "add new")
                            }
                        }
                    }
                }
            }
        }
    }
}

