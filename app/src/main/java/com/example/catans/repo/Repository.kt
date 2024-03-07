package com.example.catans.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.catans.model.Airport
import com.example.catans.model.Currency
import com.example.catans.model.DataChild
import com.example.catans.model.DataModel
import com.example.catans.network.ApiService
import com.example.catans.util.EnumCurrencyUtils
import com.example.catans.util.EnumUtils
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

object Repository {

    private const val PAGE_SIZE = 1

    fun getPagingAirport(enumUtils: EnumUtils, airportCallBack: DataModel.AirportCallBack): Flow<PagingData<Airport>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = {
                RepoPagingAirport(ApiService.create(enumUtils), enumUtils, airportCallBack)
            }
        ).flow
    }

    fun getPagingCurrency(list: ArrayList<DataChild>): Flow<PagingData<DataChild>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = {
                RepoPagingData(list)
            }
        ).flow
    }

    fun getCurrency(enumCurrencyUtils: EnumCurrencyUtils): Call<Currency?> {
        val service = ApiService.create(EnumUtils.Currency)
        return when(enumCurrencyUtils) {
            EnumCurrencyUtils.AUD -> service.getCurrencyAUD()
            EnumCurrencyUtils.CNY -> service.getCurrencyCNY()
            EnumCurrencyUtils.EUR -> service.getCurrencyEUR()
            EnumCurrencyUtils.HKD -> service.getCurrencyHKD()
            EnumCurrencyUtils.JPY -> service.getCurrencyJPY()
            EnumCurrencyUtils.USD -> service.getCurrencyUSD()
        }
    }

}