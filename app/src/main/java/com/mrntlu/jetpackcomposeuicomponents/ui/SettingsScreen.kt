package com.mrntlu.jetpackcomposeuicomponents.ui

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mrntlu.jetpackcomposeuicomponents.viewmodels.SharedViewModel

@Composable
fun SettingsScreen(
    sharedViewModel: SharedViewModel,
) {
    val context = LocalContext.current
    val listState = rememberLazyListState()
    val expandedFabState = remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0
        }
    }

    LaunchedEffect(key1 = expandedFabState.value) {
        sharedViewModel.expandedFab.value = expandedFabState.value
    }

    LaunchedEffect(key1 = Unit) {
        sharedViewModel.fabOnClick.value = {
            Toast.makeText(context, "Settings FAB Clicked", Toast.LENGTH_SHORT).show()
        }

        sharedViewModel.smallFabOnClick.value = {
            Toast.makeText(context, "Settings Small FAB Clicked", Toast.LENGTH_SHORT).show()
        }
    }

    LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
        for (index in 0 until 100) {
            item {
                Text(
                    text = "List item - $index",
                    modifier = Modifier.padding(24.dp)
                )
            }
        }
    }
}