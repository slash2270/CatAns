package com.example.catans.base

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.cachedIn
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catans.adapter.FooterAdapter
import com.example.catans.adapter.RepoAdapterAirport
import com.example.catans.adapter.RepoAdapterData
import com.example.catans.databinding.FragmentBaseBinding
import com.example.catans.model.Airport
import com.example.catans.model.DataChild
import com.example.catans.model.DataModel
import com.example.catans.repo.Repository
import com.example.catans.util.EnumUtils
import com.example.catans.util.Utils
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseViewModel: ViewModel() {

    private lateinit var repoAdapterAirport: RepoAdapterAirport
    private lateinit var repoAdapterData: RepoAdapterData
    private val listAirport: MutableLiveData<List<Airport>?> = MutableLiveData<List<Airport>?>()
    private val listData: MutableLiveData<ArrayList<DataChild>> = MutableLiveData<ArrayList<DataChild>>()
    private var handler: Handler = Handler(Looper.getMainLooper())
    private var runnableTime: Runnable? = null

//    fun fab(binding: FragmentBaseBinding, fragment: Fragment) {
//        binding.fab.hide()
//        binding.fab.backgroundTintList = ResourcesCompat.getColorStateList(fragment.resources, R.color.purple_200, fragment.activity?.theme)
//        binding.fab.setOnClickListener { view ->
//            if (showFab) binding.fab.hide() else binding.fab.show()
//            showFab = !showFab
//            snackBar(binding)
//        }
//    }

    private suspend fun dataUpdate(getData: () -> Unit) {
        delay(Utils.TIME_REPEAT)
        getData()
    }
    fun dataAirport(fragment: Fragment, enumUtils: EnumUtils, binding: FragmentBaseBinding) {
        repoAdapterAirport = RepoAdapterAirport(fragment)
        repoAdapterAirport = DataModel().getDataAirport(viewModelScope, repoAdapterAirport, enumUtils, object : DataModel.AirportCallBack {
            override fun getData(getList: List<Airport>?) {
                if (getList != null) {
                    Log.d("BaseViewModel airport","${getList.size}")
                    listAirport.value = getList
                    listAirport.observe(fragment) { airports ->
                        viewModelScope.launch {
                            dataUpdate {
                                repoAdapterAirport.refresh()
                            }
                        }
                        Log.d("observe airports","${airports?.size}")
                    }
                }
            }
        })
    }

    fun dataCurrency(fragment: Fragment, binding: FragmentBaseBinding) {
        val dataModel = DataModel()
        repoAdapterData = RepoAdapterData(fragment)
        dataModel.getDataCurrency(viewModelScope, binding, object : DataModel.ChildCallBack {
            override fun getData(callBackList: ArrayList<DataChild>) {
                viewModelScope.launch {
                    val deferred = async {
                        callBackList
                    }
                    val getList = deferred.await()
                    Repository.getPagingCurrency(getList).cachedIn(viewModelScope).collect { pagingData ->
                        repoAdapterData.submitData(pagingData)
                    }
                    Log.d("BaseViewModel currency","${getList.size}")
                    listData.postValue(getList)
                    listData.observe(fragment) { currency ->
//                        dataUpdate {
//                            binding.progressCircular.visibility = View.VISIBLE
//                            binding.recyclerView.visibility = View.GONE
//                            dataCurrency(fragment, binding)
//                        }
                        Log.d("observe currency","${currency.size}")
                    }
                }
            }
        })
    }

    fun recyclerCurrency(binding: FragmentBaseBinding, fragment: Fragment) {
        fragment.context?.let {
            binding.recyclerView.setPadding(Utils.dpToPixel(it,12), Utils.dpToPixel(it,16), Utils.dpToPixel(it,12), Utils.dpToPixel(it,16))
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(fragment.context)
        binding.recyclerView.adapter = repoAdapterData.withLoadStateFooter(FooterAdapter {
            repoAdapterData.retry()
        })
    }

    fun recyclerAirport(context: Context, binding: FragmentBaseBinding) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = repoAdapterAirport.withLoadStateFooter(FooterAdapter {
            repoAdapterAirport.retry()
        })
    }

    fun adapterAirport(fragment: Fragment, binding: FragmentBaseBinding) {
        repoAdapterAirport.addLoadStateListener {
            loadState(fragment, it, binding)
        }
    }

    fun adapterData(fragment: Fragment, binding: FragmentBaseBinding) {
        repoAdapterData.addLoadStateListener {
            loadState(fragment, it, binding)
        }
    }

    private fun loadState(fragment: Fragment, state : CombinedLoadStates, binding: FragmentBaseBinding) {
        when (state.refresh) {
            is LoadState.NotLoading -> {
                binding.progressCircular.visibility = View.INVISIBLE
                binding.recyclerView.visibility = View.VISIBLE
            }
            is LoadState.Loading -> {
                binding.progressCircular.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.INVISIBLE
            }
            is LoadState.Error -> {
                binding.progressCircular.visibility = View.INVISIBLE
                error(fragment, binding)
            }
        }
    }

    fun error(fragment: Fragment, binding: FragmentBaseBinding) {
        binding.error.retryButton.setOnClickListener {
            dataCurrency(fragment, binding)
        }
    }

    fun destroy() {
        if (runnableTime != null) {
            handler.removeCallbacks(runnableTime!!)
        }
    }

}