package com.artikdemonik.healthfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.artikdemonik.healthfood.ui.theme.HealthFoodTheme

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
            Row {
                Text(text = user.name)
                Text(text = user.needCalories.toString())
            }
        }
    }
}
