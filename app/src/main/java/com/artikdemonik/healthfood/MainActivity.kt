package com.artikdemonik.healthfood

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dependencies.init(applicationContext)
        val viewModel = MainVM(Dependencies.foodRepository)
        viewModel.getUsers()

        setContent {
            var users by remember{
                mutableStateOf(listOf(UserDbEntity(-1, "Костя Дудоров", 18, 174, 64, 10)))
            }
            viewModel.users.observe(this){
                users = it
                Toast.makeText(this, users.size.toString(), Toast.LENGTH_SHORT).show()
            }
            Box(){
                MainScreen(users)
            }
        }
    }

    @Composable
    fun MainScreen(users: List<UserDbEntity>){
        Column{
            Box {
                OutlinedCard(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),

                        ) {
                        Text("Выберите пользователя", fontSize = 30.sp)
                    }
                }
            }
            OutlinedCard(modifier = Modifier.fillMaxSize()) {
                LazyColumn(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()){
                    items(users){
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(3.dp),
                        ) {
                            ListItem(user = it)
                        }
                        Spacer(modifier = Modifier.size(3.dp))
                    }
                    item{
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Button(onClick = {
                                val intent = Intent(this@MainActivity, AddUserActivity::class.java)
                                startActivity(intent)
                                finish()
                            }, border = BorderStroke(0.5.dp, Color.DarkGray)) {
                                Text("Добавить пользователя")
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun ListItem(user: UserDbEntity){
        Card(
            backgroundColor = Color.DarkGray,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 7.dp)
                .offset(x = 6.dp, y = 6.dp),
            onClick = {
                val intent = Intent(this, SeeStatsActivity::class.java)
                intent.putExtra("id", user.id)
                startActivity(intent)
            }
        ){
            Text(user.name, fontSize = 20.sp, color = Color.DarkGray)
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 7.dp),
            backgroundColor = Color.Blue,
            shape = RoundedCornerShape(10.dp),
            onClick = {
                val intent = Intent(this, SeeStatsActivity::class.java)
                intent.putExtra("id", user.id)
                startActivity(intent)
            }
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(3.dp),
            ) {
                Text(user.name, fontSize = 20.sp)
            }
        }
    }
}