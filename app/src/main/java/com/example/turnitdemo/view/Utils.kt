package com.example.turnitdemo.view

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun isLandscapeOrientation(): Boolean {
    val configuration = LocalConfiguration.current
    return (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
}