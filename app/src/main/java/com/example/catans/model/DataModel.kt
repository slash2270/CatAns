package com.example.catans.model

import androidx.paging.cachedIn
import com.example.catans.adapter.RepoAdapterAirport
import com.example.catans.adapter.RepoAdapterCurrency
import com.example.catans.repo.Repository
import com.example.catans.util.EnumUtils
import kotlinx.coroutines.*

class DataModel {

    private lateinit var deferredAirport: Deferred<List<Airport?>?>
    private lateinit var deferredCurrency: Deferred<List<Currency?>?>

    suspend fun getDataAirport(scope: CoroutineScope, repoAdapter: RepoAdapterAirport?, enumUtils: EnumUtils): List<Airport?>? {
        Repository.getPagingAirport(enumUtils).cachedIn(scope).collect { pagingData ->
            repoAdapter?.submitData(pagingData)
        }
        deferredAirport = scope.async(Dispatchers.IO) {
            repoAdapter?.snapshot()?.items
        }
        return deferredAirport.await()
    }

    suspend fun getDataCurrency(scope: CoroutineScope, repoAdapter: RepoAdapterCurrency?, enumUtils: EnumUtils): List<Currency?>? {
        Repository.getPagingCurrency(enumUtils).cachedIn(scope).collect { pagingData ->
            repoAdapter?.submitData(pagingData)
        }
        deferredCurrency = scope.async(Dispatchers.IO) {
            repoAdapter?.snapshot()?.items
        }
        return deferredCurrency.await()
    }

    fun getDataChild(data: Data?): List<DataChild> {
        val list = arrayListOf<DataChild>()
        data?.toMap()?.map {
            list.add(DataChild(it.key, (it.value ?: 0.0).toString()))
        }
        return list.toSet().toList()
    }

}