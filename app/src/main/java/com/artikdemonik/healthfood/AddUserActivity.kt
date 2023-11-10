package com.artikdemonik.healthfood

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class AddUserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = AddUserVM(Dependencies.foodRepository)
        setContent {

            var name by remember {
                mutableStateOf("")
            }
            var age by remember {
                mutableStateOf("")
            }
            var height by remember {
                mutableStateOf("")
            }
            var weight by remember {
                mutableStateOf("")
            }
            var sex by remember {
                mutableStateOf(false)
            }
            var color_man by remember {
                mutableStateOf(Color.Red)
            }
            var color_woman by remember {
                mutableStateOf(Color.Green)
            }
            val activityCoeffs = listOf(1.2, 1.4, 1.7, 2.0)
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(activityCoeffs[0]) }
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)){

                Text("Введите имя:")
                TextField(value = name,
                    onValueChange = {
                        name = it
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Divider()
                Text("Введите возраст:")
                TextField(value = age,
                    onValueChange = {
                        age = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                )

                Divider()
                Text("Введите рост:")
                TextField(value = height,
                    onValueChange = {
                        height = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                )
                Divider()
                Text("Введите вес:")
                TextField(value = weight,
                    onValueChange = {weight = it},
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                )
                Divider()
                Text("Выберите пол:")
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                    Card(colors = CardDefaults.cardColors(containerColor = color_woman)) {
                        Text("Женщина",
                            fontSize = 15.sp,
                            modifier = Modifier.padding(5.dp))
                    }
                    Switch(checked = sex, onCheckedChange = {
                        sex = it
                        if(sex){
                            color_man = Color.Green
                            color_woman = Color.Red
                        }
                        else{
                            color_man = Color.Red
                            color_woman = Color.Green
                        }

                    })
                    Card(colors = CardDefaults.cardColors(containerColor = color_man)) {
                        Text("Мужчина",
                            fontSize = 15.sp,
                            modifier = Modifier.padding(5.dp))
                    }
                }
                Divider()
                Row {
                    Column(modifier = Modifier.selectableGroup()) {
                        activityCoeffs.forEach { text ->
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = { onOptionSelected(text) }
                            )

                        }
                    }
                    Column() {
                        Text("- Малоподвижный образ жизни",
                            fontSize = 15.sp, modifier = Modifier.padding(15.dp)
                        )
                        Text("- Cредняя активность (7-9 тыс. шагов + 2-3 тренировки в неделю)",
                            fontSize = 15.sp, modifier = Modifier.padding(10.dp)
                        )
                        Text("- Высокая активность (10-15 тыс. шагов + 3-4 тренировки в неделю)",
                            fontSize = 15.sp, modifier = Modifier.padding(5.dp)
                        )
                        Text("- Несколько тренировок в день (Подготовка к соревнованиям)",
                            fontSize = 15.sp, modifier = Modifier.padding(5.dp)
                        )
                    }
                }
                Divider()
                Box(modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    Button(onClick = {
                        val needCalories: Double = when (sex){
                            true -> (10 * weight.toInt()) + (6.25 * height.toInt()) - (5 * age.toInt()) + 5
                            false -> (10 * weight.toInt()) + (6.25 * height.toInt()) - (5 * age.toInt()) - 161
                        }
                        val newUser = UserTuple(name, age.toInt(), height.toInt(), weight.toInt(), (needCalories * selectedOption).toInt())
                        viewModel.addUser(newUser)
                        val intent = Intent(this@AddUserActivity, MainActivity::class.java)
                        startActivity(intent)
                    }){
                        Text("Сохранить нового пользователя")
                    }
                }
            }
        }
    }
}
