package com.example.catans.model

import android.util.Log
import android.view.View
import androidx.paging.cachedIn
import com.example.catans.adapter.RepoAdapterAirport
import com.example.catans.databinding.FragmentBaseBinding
import com.example.catans.repo.Repository
import com.example.catans.util.EnumUtils
import kotlinx.coroutines.*

class DataModel {
    interface AirportInterface {
        fun getData(list: List<Airport>)
    }

    interface ChildInterface {
        fun getData(arrayList: ArrayList<DataChild>)
    }

    fun getDataAirport(scope: CoroutineScope, repoAdapter: RepoAdapterAirport, enumUtils: EnumUtils, airport: AirportInterface) {
        scope.launch(Dispatchers.IO) {
            Repository.getPagingAirport(enumUtils).cachedIn(scope).collect { pagingData ->
                repoAdapter.submitData(pagingData)
            }
            airport.getData(repoAdapter.snapshot().items)
        }
    }

    fun getDataCurrency(scope: CoroutineScope, binding: FragmentBaseBinding, child: ChildInterface): ArrayList<DataChild> {
        val list = arrayListOf<DataChild>()
        scope.launch(Dispatchers.IO) {
            Repository.getCurrency().enqueue(object : retrofit2.Callback<Currency?> {
                override fun onResponse(call: retrofit2.Call<Currency?>, response: retrofit2.Response<Currency?>) {
                    val currency = response.body()
                    if (response.isSuccessful && currency != null && currency.data != null) {
                        currency.data.toMap().map {
                            list.add(DataChild(it.key, (it.value ?: 0.0).toString()))
                        }
                    }
                    binding.progressCircular.visibility = View.GONE
                    binding.noData.root.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
                    child.getData(list)
                    Log.d("getDataCurrency:", list.size.toString())

                }
                override fun onFailure(call: retrofit2.Call<Currency?>, throwable: Throwable) {
                    binding.progressCircular.visibility = View.GONE
                    binding.error.root.visibility = View.VISIBLE
                    Log.d("getDataCurrency" , throwable.toString().trim { it <= ' ' })
                }
            })
        }
        return list
    }

}