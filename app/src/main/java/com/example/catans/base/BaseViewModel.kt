package com.example.catans.base

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.support.annotation.NonNull
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.cachedIn
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.catans.R
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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseViewModel: ViewModel() {

    private lateinit var repoAdapterAirport: RepoAdapterAirport
    private lateinit var repoAdapterData: RepoAdapterData
    private val listAirport: MutableLiveData<List<Airport>?> = MutableLiveData<List<Airport>?>()
    var listData: MutableLiveData<ArrayList<DataChild>> = MutableLiveData<ArrayList<DataChild>>()
    private var dialog: BottomSheetDialog? = null
    private lateinit var behavior: BottomSheetBehavior<View>
    private var handler: Handler = Handler(Looper.getMainLooper())
    private var runnableTime: Runnable? = null

//    fun fab(binding: FragmentBaseBinding, fragment: Fragment) {
//        binding.fab.visibility = View.VISIBLE
//        binding.fab.backgroundTintList = ResourcesCompat.getColorStateList(fragment.resources, R.color.purple_200, fragment.activity?.theme)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            binding.recyclerView.setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->
//
//            }
//        }
//    }

    private suspend fun dataUpdate(getData: () -> Unit) {
        delay(Utils.TIME_REPEAT)
        getData()
    }
    fun dataAirport(fragment: Fragment, enumUtils: EnumUtils) {
        repoAdapterAirport = RepoAdapterAirport(fragment)
        repoAdapterAirport = DataModel().getDataAirport(viewModelScope, repoAdapterAirport, enumUtils, object : DataModel.AirportCallBack {
            override fun getData(getList: List<Airport>?) {
                Log.d("BaseViewModel airport","${getList?.size}")
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
        })
    }

    fun dataCurrency(fragment: Fragment, binding: FragmentBaseBinding) {
        viewModelScope.launch {
            listData = DataModel().getDataCurrency(viewModelScope, binding, listData).await()
            Log.d("BaseViewModel currency","${listData.value?.size}")
            listData.observe(fragment) { currency ->
                viewModelScope.launch(Dispatchers.Main) {
                    Repository.getPagingCurrency(currency).cachedIn(viewModelScope).collect { pagingData ->
                        repoAdapterData.submitData(pagingData)
                    }
                    Log.d("Observe currency","${currency.size}")
                }
            }
        }
    }

    fun recyclerCurrency(binding: FragmentBaseBinding, fragment: Fragment) {
        fragment.context?.let {
            binding.recyclerView.setPadding(Utils.dpToPixel(it,12), Utils.dpToPixel(it,16), Utils.dpToPixel(it,12), Utils.dpToPixel(it,16))
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(fragment.context)
        binding.recyclerView.adapter = repoAdapterData
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
        repoAdapterData = RepoAdapterData(fragment, listData) {
            dialog?.let { bottomSheetClick(fragment, it, behavior) }
        }
        repoAdapterData.withLoadStateFooter(FooterAdapter {
            repoAdapterData.retry()
        })
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

    private fun error(fragment: Fragment, binding: FragmentBaseBinding) {
        binding.error.retryButton.setOnClickListener {
            dataCurrency(fragment, binding)
        }
    }

    fun bottomSheet(fragment: Fragment) {
        val view = View.inflate(fragment.context, R.layout.dialog_bottom_sheet, null)
        dialog = fragment.context?.let { BottomSheetDialog(it) }
        dialog?.setContentView(view)
        behavior = BottomSheetBehavior.from(view.parent as View)
    }

    private fun bottomSheetClick(fragment: Fragment, dialog: BottomSheetDialog, behavior: BottomSheetBehavior<View>) {
        // behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        fragment.context?.let {
            behavior.peekHeight = Utils.dpToPixel(it, 600)
        }
        dialog.show()
//        behavior.state = when(behavior.state) {
//            BottomSheetBehavior.STATE_HIDDEN -> BottomSheetBehavior.STATE_COLLAPSED
//            else -> BottomSheetBehavior.STATE_HIDDEN
//        }
    }

    fun destroy() {
        if (runnableTime != null) {
            handler.removeCallbacks(runnableTime!!)
        }
    }

}