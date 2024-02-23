package com.example.catans.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.catans.model.Airport
import com.example.catans.model.Currency
import com.example.catans.network.ApiService
import com.example.catans.util.EnumUtils
import kotlinx.coroutines.flow.Flow

object Repository {

    private const val PAGE_SIZE = 1

    fun getPagingAirport(enumUtils: EnumUtils): Flow<PagingData<Airport>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = {
                RepoPagingAirport(ApiService.create(enumUtils), enumUtils)
            }
        ).flow
    }

    fun getPagingCurrency(enumUtils: EnumUtils): Flow<PagingData<Currency>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = {
                RepoPagingCurrency(ApiService.create(enumUtils))
            }
        ).flow
    }

}