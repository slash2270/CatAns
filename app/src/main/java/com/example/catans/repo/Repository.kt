package com.example.catans.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.catans.model.Airport
import com.example.catans.network.ApiService
import com.example.catans.util.EnumAirport
import kotlinx.coroutines.flow.Flow

object Repository {

    private const val PAGE_SIZE = 1

    private val apiService = ApiService.createAirport()

    fun getPagingData(enum : EnumAirport): Flow<PagingData<Airport>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = {
                RepoPagingSource(apiService, enum)
            }
        ).flow
    }

}