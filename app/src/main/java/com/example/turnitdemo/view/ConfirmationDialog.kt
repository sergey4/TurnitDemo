package com.example.turnitdemo.view

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.turnitdemo.R

@Composable
fun ConfirmationDialog(
    title: String,
    text: String,
    onDismissClick: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissClick,
        confirmButton = {
            TextButton(onClick = onConfirmClick) {
                Text(stringResource(R.string.button_confirm))
            }

        },
        dismissButton = {
            TextButton(onClick = onDismissClick) {
                Text(stringResource(R.string.button_cancel))
            }
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text)
        },
    )
}
