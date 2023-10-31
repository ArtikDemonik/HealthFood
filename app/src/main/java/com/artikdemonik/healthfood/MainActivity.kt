package com.artikdemonik.healthfood

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dependencies.init(applicationContext)
        val viewModel: MainVM = MainVM(Dependencies.foodRepository)
        viewModel.getNameByID(0)

        setContent {
            var name by remember {
                mutableStateOf("loading...")
            }

            viewModel.name.observe(this){ it ->
                name = it
                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
            }
            Text(text = name)
        }
    }
}

