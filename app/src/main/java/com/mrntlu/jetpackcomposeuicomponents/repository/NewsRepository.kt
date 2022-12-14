package com.mrntlu.jetpackcomposeuicomponents.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mrntlu.jetpackcomposeuicomponents.pagination.NewsPagingSource
import com.mrntlu.jetpackcomposeuicomponents.service.NewsApiService

class NewsRepository(
    private val newsApiService: NewsApiService,
) {

    fun getUsers() = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            NewsPagingSource(newsApiService)
        }
    ).flow
}