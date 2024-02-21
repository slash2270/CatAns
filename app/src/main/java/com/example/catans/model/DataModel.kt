package com.example.catans.model

import androidx.paging.cachedIn
import com.example.catans.adapter.RepoAdapter
import com.example.catans.repo.Repository
import com.example.catans.util.EnumAirport
import kotlinx.coroutines.*

class DataModel {

    private lateinit var deferredArticles: Deferred<List<Airport?>?>

    suspend fun getData(scope: CoroutineScope, repoAdapter: RepoAdapter, enum : EnumAirport): List<Airport?>? {
        Repository.getPagingData(enum).cachedIn(scope).collect { pagingData ->
            repoAdapter.submitData(pagingData)
        }
        deferredArticles = scope.async(Dispatchers.IO) {
            repoAdapter.snapshot().items
        }
        return  deferredArticles.await()
    }

}