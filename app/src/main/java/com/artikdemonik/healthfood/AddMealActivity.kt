package com.artikdemonik.healthfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SearchBar
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import com.artikdemonik.healthfood.ui.theme.HealthFoodTheme

class AddMealActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val date = intent.getStringExtra("date")!!
        val user_id = intent.getIntExtra("user", -1)
        val viewModel = AddMealVM(Dependencies.foodRepository)
        viewModel.getProducts()
        setContent {
            var fullList by remember{
                mutableStateOf(listOf(ProductDbEntity(-1, "Загрузка", 0, 0 ,0 ,0)))
            }
            viewModel.products.observe(this){
                fullList = it
            }
            val weight = remember{
                mutableStateOf("")
            }
            var filteredList by remember {
                mutableStateOf(emptyList<ProductDbEntity>())
            }
            filteredList = fullList
            val current = remember {
                mutableStateOf(0)
            }

            val dialog = remember{ mutableStateOf(false) }
            if(dialog.value){
                WeightDialog(dialog, weight, current, viewModel, user_id, date)
            }
            LazyColumn(){
                item{
                    SearchBar(items = fullList, onSearch = {
                        filteredList = it
                    })
                }
                items(filteredList){
                    OutlinedCard(
                        modifier = Modifier
                            .padding(3.dp)
                            .fillMaxWidth(),
                        onClick = {
                            dialog.value = !dialog.value
                            current.value = it.id
                        }) {
                            Row(
                                Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                              Text(it.name)
                              Text(it.calories.toString())
                        }
                    }
                }
            }

        }
    }

    @Composable
    fun WeightDialog(
        state: MutableState<Boolean>,
        text: MutableState<String>,
        current: MutableState<Int>,
        viewModel: AddMealVM,
        user_id: Int,
        date: String
    ){

        Dialog(onDismissRequest = {
            state.value = !state.value
        }) {
            Column() {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                    TextField(
                        value = text.value,
                        onValueChange = {
                            text.value = it
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }
                Row{
                    Button(onClick = {state.value = !state.value}) {
                        Text("Отмена")
                    }
                    Button(onClick = {
                        viewModel.addMeal(date, current.value, text.value.toDouble(), user_id)
                        finish()
                    }) {
                        Text("Добавить")
                    }
                }
            }
        }
    }


    fun filterListByName(items: List<ProductDbEntity>, query: String): List<ProductDbEntity> {
        return items.filter { it.name.contains(query, ignoreCase = true) }
    }

    @Composable
    fun SearchBar(
        items: List<ProductDbEntity>,
        onSearch: (List<ProductDbEntity>) -> Unit
    ) {
        var searchText by remember { mutableStateOf("") }

        Column {
            Box(modifier = Modifier.border(0.7.dp, Color.DarkGray))
            {
                BasicTextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                        onSearch(filterListByName(items, it))
                    },
                    textStyle = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .border(0.7.dp, Color.DarkGray)
                )
            }
        }
    }



}
