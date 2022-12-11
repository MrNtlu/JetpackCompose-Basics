package com.mrntlu.jetpackcomposeuicomponents.ui

import android.widget.Toast
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.mrntlu.jetpackcomposeuicomponents.viewmodels.SharedViewModel

@Composable
fun SettingsScreen(
    sharedViewModel: SharedViewModel,
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        sharedViewModel.fabOnClick.value = {
            Toast.makeText(context, "Settings FAB Clicked", Toast.LENGTH_SHORT).show()
        }
    }

    Text(text = "Settings")
}