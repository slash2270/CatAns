package com.example.catans.model

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.paging.cachedIn
import com.example.catans.adapter.RepoAdapterAirport
import com.example.catans.databinding.FragmentBaseBinding
import com.example.catans.repo.Repository
import com.example.catans.util.EnumUtils
import kotlinx.coroutines.*

class DataModel {

    interface ChildCallBack {
        fun getData(callBackList: ArrayList<DataChild>)
    }

    interface AirportCallBack {

        fun getData(getList: List<Airport>?)

    }

    fun getDataAirport(scope: CoroutineScope, repoAdapter: RepoAdapterAirport, enumUtils: EnumUtils, airportCallBack: AirportCallBack): RepoAdapterAirport {
        scope.launch(Dispatchers.IO) {
            Repository.getPagingAirport(enumUtils, airportCallBack).cachedIn(scope).collect { pagingData ->
                repoAdapter.submitData(pagingData)
            }
        }
        return repoAdapter
    }

    fun getDataCurrency(scope: CoroutineScope, binding: FragmentBaseBinding, getList: MutableLiveData<ArrayList<DataChild>>): Deferred<MutableLiveData<ArrayList<DataChild>>> {
        val list = arrayListOf<DataChild>()
        Repository.getCurrency().enqueue(object : retrofit2.Callback<Currency?> {
            override fun onResponse(call: retrofit2.Call<Currency?>, response: retrofit2.Response<Currency?>) {
                val currency = response.body()
                if (response.isSuccessful && currency != null && currency.data != null) {
                    currency.data.toMap().entries.map {
                        list.add(DataChild(it.key, (it.value ?: 0.0).toString()))
                    }
                }
                getList.postValue(list)
                binding.progressCircular.visibility = View.INVISIBLE
                binding.noData.root.visibility = if (list.isEmpty()) View.VISIBLE else View.INVISIBLE
                Log.d("getDataCurrency:", list.size.toString())

            }
            override fun onFailure(call: retrofit2.Call<Currency?>, throwable: Throwable) {
                binding.progressCircular.visibility = View.INVISIBLE
                binding.error.root.visibility = View.VISIBLE
                Log.d("getDataCurrency" , throwable.toString().trim { it <= ' ' })
            }
        })
        return scope.async {
            getList
        }
    }

}