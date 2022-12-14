package com.mrntlu.jetpackcomposeuicomponents.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.mrntlu.jetpackcomposeuicomponents.repository.NewsRepository
import com.mrntlu.jetpackcomposeuicomponents.service.NewsApiService
import com.mrntlu.jetpackcomposeuicomponents.viewmodels.NewsViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun AddScreen() {
    val viewModel: NewsViewModel = viewModel()

    val users = viewModel.getBreakingNews().collectAsLazyPagingItems()

    /*
    https://betterprogramming.pub/jetpack-compose-pagination-287ea6e782e3
    https://www.simplifiedcoding.net/pagination-in-jetpack-compose/
    https://medium.com/simform-engineering/list-view-with-pagination-using-jetpack-compose-e131174eac8e
    https://www.howtodoandroid.com/jetpack-compose-retrofit-recyclerview/
    https://www.google.com/search?q=jetpack+compose+pagin3+loads+multiple+pags&sxsrf=ALiCzsaMXjtqhdOQZw7YDaSpDpcDsn3wXQ%3A1671034405637&ei=JfaZY7q7Js-Rxc8PusmJ8A8&ved=0ahUKEwj64Z6hwPn7AhXPSPEDHbpkAv4Q4dUDCA8&uact=5&oq=jetpack+compose+pagin3+loads+multiple+pags&gs_lcp=Cgxnd3Mtd2l6LXNlcnAQAzIKCCEQwwQQChCgAToKCAAQRxDWBBCwAzoFCAAQogQ6BAghEApKBAhBGABKBAhGGABQgQZYuxFgjxJoAXABeACAAcwBiAHTEZIBBjAuMTMuMZgBAKABAcgBCMABAQ&sclient=gws-wiz-serp

     */

    LazyColumn {
        items (
            count = users.itemCount,
            key = { index ->
                users[index]?.url
                    ?: index
            }
        ) { index ->

            Text(
                modifier = Modifier.height(200.dp),
                text = users[index]?.title ?: ""
            )
        }

        when (val state = users.loadState.prepend) {
            is LoadState.Loading -> {
//                item {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(Color.White),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                    ) {
//                        CircularProgressIndicator(
//                            color = Color.Black
//                        )
//                    }
//                }
                Log.d("Test", "Prepend Loading")
            }
            is LoadState.Error -> {
                Log.d("Test", "Prepend Error")
            }
            else -> {}
        }

        when (val state = users.loadState.refresh) {
            is LoadState.Error -> {
                Log.d("Test", "Refresh Loading")
            }
            is LoadState.Loading -> {
                Log.d("Test", "Refresh Loading")
            }
            else -> {}
        }

        when (val state = users.loadState.append) {
            is LoadState.Error -> {
                Log.d("Test", "Append Loading")
            }
            is LoadState.Loading -> {
                Log.d("Test", "Append Loading")
            }
            else -> {}
        }
    }
}