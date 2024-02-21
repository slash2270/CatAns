package com.example.catans.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catans.adapter.FooterAdapter
import com.example.catans.adapter.RepoAdapter
import com.example.catans.databinding.FragmentAirportBinding
import com.example.catans.model.Airport
import com.example.catans.model.DataModel
import com.example.catans.util.EnumAirport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AirportViewModel: ViewModel() {

    private lateinit var repoAdapter: RepoAdapter
    private val listData: MutableLiveData<List<Airport?>?> = MutableLiveData<List<Airport?>?>()

    fun getData(fragment: Fragment, enum : EnumAirport) {
        repoAdapter = RepoAdapter()
        viewModelScope.launch(Dispatchers.Main) {
            DataModel().getData(viewModelScope, repoAdapter, enum).let { list ->
                if (list?.isNotEmpty() == true) {
                    listData.value = list.toSet().filterNotNull()
                    listData.observe(fragment) { articles ->
                        Log.d("MainViewModel articles","${articles?.size}")
                    }
                }
            }
        }
    }

    fun recycler(context: Context, binding: FragmentAirportBinding) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = repoAdapter.withLoadStateFooter(FooterAdapter {
            repoAdapter.retry()
        })
    }

    fun adapter(context: Context, binding: FragmentAirportBinding) {
        repoAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    binding.progressCircular.visibility = View.INVISIBLE
                    binding.recyclerView.visibility = View.VISIBLE
                }
                is LoadState.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.INVISIBLE
                }
                is LoadState.Error -> {
                    val state = it.refresh as LoadState.Error
                    binding.progressCircular.visibility = View.INVISIBLE
                    Toast.makeText(context, "Load Error: ${state.error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}