package com.mrntlu.jetpackcomposeuicomponents.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mrntlu.jetpackcomposeuicomponents.models.Article
import com.mrntlu.jetpackcomposeuicomponents.repository.NewsRepository
import com.mrntlu.jetpackcomposeuicomponents.service.NewsApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsViewModel: ViewModel() {
    private val apiService = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsApiService::class.java)

    private val repository = NewsRepository(apiService)

    fun getBreakingNews(): Flow<PagingData<Article>> = repository.getUsers().cachedIn(viewModelScope)
}