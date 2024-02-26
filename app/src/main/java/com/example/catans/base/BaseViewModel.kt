package com.example.catans.base

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catans.adapter.FooterAdapter
import com.example.catans.adapter.RepoAdapterAirport
import com.example.catans.adapter.RepoAdapterData
import com.example.catans.databinding.FragmentBaseBinding
import com.example.catans.model.Airport
import com.example.catans.model.DataChild
import com.example.catans.model.DataModel
import com.example.catans.util.EnumUtils
import com.example.catans.util.Utils

open class BaseViewModel: ViewModel() {

    private lateinit var repoAdapterAirport: RepoAdapterAirport
    private lateinit var repoAdapterData: RepoAdapterData
    private val listAirport: MutableLiveData<List<Airport?>?> = MutableLiveData<List<Airport?>?>()
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

    private fun dataUpdate(getData: () -> Unit, binding: FragmentBaseBinding) {
        runnableTime = Runnable {
            binding.progressCircular.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
            getData()
        }
        handler.postDelayed(runnableTime!!, Utils.TIME_REPEAT)
    }

    fun dataAirport(fragment: Fragment, enumUtils: EnumUtils, binding: FragmentBaseBinding) {
        repoAdapterAirport = RepoAdapterAirport(fragment)
        DataModel().getDataAirport(viewModelScope, repoAdapterAirport, enumUtils, object : DataModel.AirportInterface{
            override fun getData(list: List<Airport>) {
                if (list.isNotEmpty()) {
                    binding.recyclerView.visibility = View.VISIBLE
                    dataUpdate({
                        repoAdapterAirport.refresh()
                    }, binding)
                    listAirport.value = list.toSet().toList()
                    listAirport.observe(fragment) {  airports ->
                        repoAdapterAirport.notifyDataSetChanged()
                        Log.d("BaseViewModel airports","${airports?.size}")
                    }
                }
            }
        })
    }

    fun dataCurrency(fragment: Fragment, binding: FragmentBaseBinding) {
        DataModel().getDataCurrency(viewModelScope, binding, object : DataModel.ChildInterface{
            override fun getData(arrayList: ArrayList<DataChild>) {
                fragment.context?.let { recyclerCurrency(it, binding, arrayList, fragment) }
                listData.value = arrayList
                if (arrayList.isNotEmpty()) {
                    binding.recyclerView.visibility = View.VISIBLE
                    dataUpdate({
                        dataCurrency(fragment, binding)
                    }, binding)
                    listData.observe(fragment) { currency ->
                        repoAdapterData = RepoAdapterData(currency, fragment)
                        repoAdapterData.notifyDataSetChanged()
                        Log.d("BaseViewModel currency","${arrayList.size}")
                    }
                }
            }
        })
    }

    fun recyclerCurrency(context: Context, binding: FragmentBaseBinding, arrayList: ArrayList<DataChild>, fragment: Fragment) {
        repoAdapterData = RepoAdapterData(arrayList, fragment)
        fragment.context?.let {
            binding.recyclerView.setPadding(Utils.dpToPixel(it,12), Utils.dpToPixel(it,16), Utils.dpToPixel(it,12), Utils.dpToPixel(it,16))
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = repoAdapterData
    }

    fun recyclerAirport(context: Context, binding: FragmentBaseBinding) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = repoAdapterAirport.withLoadStateFooter(FooterAdapter {
            repoAdapterAirport.retry()
        })
    }

    fun adapterAirport(context: Context, binding: FragmentBaseBinding) {
        repoAdapterAirport.addLoadStateListener {
            loadState(context, it, binding)
        }
    }

    private fun loadState(context: Context, state : CombinedLoadStates, binding: FragmentBaseBinding) {
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
                val state = state.refresh as LoadState.Error
                binding.progressCircular.visibility = View.INVISIBLE
                Toast.makeText(context, "Load Error: ${state.error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun destroy() {
        if (runnableTime != null) {
            handler.removeCallbacks(runnableTime!!)
        }
//        handler?.removeCallbacksAndMessages(handlerCallBack)
    }

}