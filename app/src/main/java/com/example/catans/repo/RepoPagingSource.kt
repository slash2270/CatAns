package com.example.catans.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.catans.model.Airport
import com.example.catans.network.ApiService
import com.example.catans.util.EnumAirport
import java.lang.Exception

class RepoPagingSource(private val apiService: ApiService, private val enum : EnumAirport) : PagingSource<Int, Airport>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Airport> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val repoResponse = when(enum) {
                EnumAirport.Departure -> apiService.getAirportDeparture(page, pageSize)
                EnumAirport.Inbound -> apiService.getAirportInbound(page, pageSize)
            }
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (repoResponse?.isNotEmpty() == true) page + 1 else null
            LoadResult.Page(repoResponse!!, prevKey, nextKey)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Airport>): Int? = null

}