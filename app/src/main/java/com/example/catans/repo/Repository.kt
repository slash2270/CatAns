package com.example.catans.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rooans.network.NewsService
import com.example.rooans.model.Article
import kotlinx.coroutines.flow.Flow

object Repository {

    private const val PAGE_SIZE = 1

    private val newsService = NewsService.create()

    fun getPagingData(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = {
                RepoPagingSource(newsService)
            }
        ).flow
    }

}