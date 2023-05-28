package com.example.turnitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.example.turnitdemo.ui.theme.TurnitDemoTheme
import com.example.turnitdemo.view.PersonListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TurnitDemoTheme {
                PersonListScreen()
            }
        }
    }
}
