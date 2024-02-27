package com.example.catans.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.catans.model.Airport
import com.example.catans.model.DataModel
import com.example.catans.network.ApiService
import com.example.catans.util.EnumUtils
import java.lang.Exception
class RepoPagingAirport(private val apiService: ApiService, private val enumUtils: EnumUtils, private val airportCallBack: DataModel.AirportCallBack) : PagingSource<Int, Airport>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Airport> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val repoResponse = when(enumUtils) {
                EnumUtils.Departure -> apiService.getAirportDeparture(page, pageSize)
                EnumUtils.Inbound -> apiService.getAirportInbound(page, pageSize)
                else -> apiService.getAirportDeparture(page, pageSize)
            }
            airportCallBack.getData(repoResponse)
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