package com.example.catans.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.catans.model.DataChild
import java.lang.Exception
class RepoPagingData(private val listData: ArrayList<DataChild>) : PagingSource<Int, DataChild>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataChild> {
        return try {
            LoadResult.Page(listData, null, null)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataChild>): Int? = null

}