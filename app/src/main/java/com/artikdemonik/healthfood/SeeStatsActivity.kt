package com.artikdemonik.healthfood

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.sql.*
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

class SeeStatsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = SeeStatsVM(Dependencies.foodRepository)
        val userID = intent.getIntExtra("id", -1)
        viewModel.getUser(userID)
        setContent {
            var user by remember{
                mutableStateOf(UserDbEntity(-1, "Loading...", 18, 174, 64, 10))
            }

            viewModel.user.observe(this){
                user = it
            }
            MyScreen(user = user, viewModel)
        }
    }
    
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyScreen(user: UserDbEntity, viewModel: SeeStatsVM){
        val dateState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
        Column {
            TopBar(name = user.name)
            DateCard(user.id, dateState, viewModel)
            Body(user, dateState, viewModel)
        }

    }

    @Composable
    fun TopBar(name: String){

        OutlinedCard(modifier = Modifier.fillMaxWidth()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ){
                Text(text = name, fontSize = 25.sp)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DateCard(user: Int, date: DatePickerState, viewModel: SeeStatsVM){
        val dialog = remember{
            mutableStateOf(false)
        }
        val dtf = SimpleDateFormat("yyyy/MM/dd", Locale.ROOT)
        viewModel.getCalories(user, dtf.format(Date(date.selectedDateMillis!!)))
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                dialog.value = true
            }
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ){
                Text(text = dtf.format(Date(date.selectedDateMillis!!)), fontSize = 12.sp)
            }
            DateDialog(dateState = date, visible = dialog, viewModel)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DateDialog(dateState: DatePickerState, visible: MutableState<Boolean>, viewModel: SeeStatsVM){
        val dtf = SimpleDateFormat("yyyy/MM/dd", Locale.ROOT)
        if(visible.value) {
            DatePickerDialog(onDismissRequest = {
                visible.value = false

            }, confirmButton = {
                OutlinedButton(onClick = {
                    visible.value = false
                    viewModel.getCalories(viewModel.user.value!!.id, dtf.format(Date(dateState.selectedDateMillis!!)))
                }) {
                    Text("Выбрать дату")
                }
            }) {
                DatePicker(state = dateState)
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Body(user: UserDbEntity, dateState: DatePickerState, viewModel: SeeStatsVM){
        var calories by remember {
            mutableStateOf(0.0f)
        }

        viewModel.calories.observe(this){
            calories = it
        }

        OutlinedCard(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                CaloriesInfo(user.needCalories.toFloat(), calories)
                DiaryOpen(user = user, dateState = dateState)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DiaryOpen(user: UserDbEntity, dateState: DatePickerState){
        val dtf = SimpleDateFormat("yyyy/MM/dd", Locale.ROOT)
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                intent = Intent(this@SeeStatsActivity, DiaryActivity::class.java)
                intent.putExtra("user_id", user.id)
                intent.putExtra("date", dtf.format(Date(dateState.selectedDateMillis!!)))
                startActivity(intent)
            }
        ){
            Box(modifier = Modifier.fillMaxWidth().padding(10.dp), contentAlignment = Alignment.Center){
                Row(){
                    Text("Открыть дневник")
                    Icon(Icons.Outlined.Info, "diary")
                }
            }
        }
    }

    @Composable
    fun CaloriesInfo(need: Float, have: Float){
        OutlinedCard(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text("Норма калорий:")
                ProgressCalories(a = have, b = need)
            }
        }
    }

    @Composable
    fun ProgressCalories(a: Float, b: Float){
        var progress = a/b
        if(progress > 1f){
            progress = 1f
        }
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .height(30.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                    progress = progress
                )
                Text("$a/$b")
            }
    }
    
}
