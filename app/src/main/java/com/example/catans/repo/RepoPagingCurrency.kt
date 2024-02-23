package com.example.catans.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.catans.model.Currency
import com.example.catans.model.Data
import com.example.catans.network.ApiService
import java.lang.Exception

class RepoPagingCurrency(private val apiService: ApiService) : PagingSource<Int, Currency>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Currency> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val repoResponse = apiService.getCurrency(page, pageSize)
            LoadResult.Page(listOf(repoResponse ?: Currency(Data())), null, null)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Currency>): Int? = null

}