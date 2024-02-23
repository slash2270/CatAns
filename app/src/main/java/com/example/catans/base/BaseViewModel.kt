package com.example.catans.base

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catans.R
import com.example.catans.adapter.FooterAdapter
import com.example.catans.adapter.RepoAdapterAirport
import com.example.catans.adapter.RepoAdapterCurrency
import com.example.catans.adapter.RepoAdapterData
import com.example.catans.component.CustomSnackBar
import com.example.catans.databinding.FragmentBaseBinding
import com.example.catans.model.Airport
import com.example.catans.model.Currency
import com.example.catans.model.Data
import com.example.catans.model.DataChild
import com.example.catans.model.DataModel
import com.example.catans.util.EnumUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class BaseViewModel: ViewModel() {

    private lateinit var repoAdapterAirport: RepoAdapterAirport
    private lateinit var repoAdapterCurrency: RepoAdapterCurrency
    private lateinit var repoAdapter: RepoAdapterData
    private val listAirport: MutableLiveData<List<Airport?>?> = MutableLiveData<List<Airport?>?>()
    private val listCurrency: MutableLiveData<List<Currency?>?> = MutableLiveData<List<Currency?>?>()
    private val listData: MutableLiveData<List<DataChild>> = MutableLiveData<List<DataChild>>()
    private var showFab = true

    fun fab(binding: FragmentBaseBinding, fragment: Fragment) {
        binding.fab.hide()
        binding.fab.backgroundTintList = ResourcesCompat.getColorStateList(fragment.resources, R.color.purple_200, fragment.activity?.theme)
        binding.fab.setOnClickListener { view ->
            if (showFab) binding.fab.hide() else binding.fab.show()
            showFab = !showFab
            snackBar(binding)
        }
    }

    private fun snackBar(binding: FragmentBaseBinding) {
        CustomSnackBar.make(binding.root as ViewGroup).show()
    }

    fun dataAirport(fragment: Fragment, enumUtils: EnumUtils) {
        repoAdapterAirport = RepoAdapterAirport(fragment)
        var list: List<Airport?>? = null
        viewModelScope.launch(Dispatchers.IO) {
            list = DataModel().getDataAirport(viewModelScope, repoAdapterAirport, enumUtils)
        }
        if (list?.isNotEmpty() == true) {
            listAirport.value = list?.toSet()?.filterNotNull()
            listAirport.observe(fragment) {  airports ->
                Log.d("BaseViewModel airports","${airports?.size}")
            }
        }
    }

    fun dataCurrency(fragment: Fragment, enumUtils: EnumUtils) {
        repoAdapterAirport = RepoAdapterAirport(fragment)
        var list: List<Currency?>? = null
        viewModelScope.launch(Dispatchers.IO) {
            list = DataModel().getDataCurrency(viewModelScope, repoAdapterCurrency, enumUtils)
        }
        if (list?.isNotEmpty() == true) {
            dataChild(list?.first()?.data, fragment)
            listCurrency.value = list
            listCurrency.observe(fragment) {  currency ->
                Log.d("BaseViewModel currency","${currency?.size}")
            }
        }
    }

    private fun dataChild(data: Data?, fragment: Fragment) {
        val list = arrayListOf<DataChild>()
        repoAdapter = RepoAdapterData(list, fragment)
        viewModelScope.launch(Dispatchers.IO) {
            list.addAll(DataModel().getDataChild(data))
        }
        if (list.isNotEmpty()) {
            listData.value = list
            listData.observe(fragment) { listData ->
                Log.d("DataViewModel","${listData.size}")
            }
        }
    }

    fun recyclerAirport(context: Context, binding: FragmentBaseBinding) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = repoAdapterAirport.withLoadStateFooter(FooterAdapter {
            repoAdapterAirport.retry()
        })
    }

    fun recyclerCurrency(context: Context, binding: FragmentBaseBinding) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = repoAdapterCurrency.withLoadStateFooter(FooterAdapter {
            repoAdapterCurrency.retry()
        })
    }

    fun adapterAirport(context: Context, binding: FragmentBaseBinding) {
        repoAdapterAirport.addLoadStateListener {
            loadState(context, it, binding)
        }
    }

    fun adapterCurrency(context: Context, binding: FragmentBaseBinding) {
        repoAdapterCurrency.addLoadStateListener {
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

}