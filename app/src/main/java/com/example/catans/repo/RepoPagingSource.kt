package com.example.catans.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.catans.model.Article
import com.example.catans.network.NewsService
import java.lang.Exception

class RepoPagingSource(private val newsService: NewsService) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val repoResponse = newsService.getData(page, pageSize)
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (repoResponse.articles?.isEmpty() == true) page + 1 else null
            LoadResult.Page(repoResponse.articles!!, prevKey, nextKey)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? = null

}