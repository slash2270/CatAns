package com.example.catans.base

import android.content.Context
import android.graphics.Paint
import android.media.Image
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.support.annotation.NonNull
import android.util.Log
import android.view.View
import android.widget.GridView
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.cachedIn
import androidx.paging.log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catans.R
import com.example.catans.adapter.FooterAdapter
import com.example.catans.adapter.RepoAdapterAirport
import com.example.catans.adapter.RepoAdapterCalculator
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
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseViewModel: ViewModel() {

    private lateinit var repoAdapterAirport: RepoAdapterAirport
    private lateinit var repoAdapterData: RepoAdapterData
    private val listAirport: MutableLiveData<List<Airport>?> = MutableLiveData<List<Airport>?>()
    var listData: MutableLiveData<ArrayList<DataChild>> = MutableLiveData<ArrayList<DataChild>>()
    private val listGrid: MutableLiveData<Array<String>> = MutableLiveData<Array<String>>()
    private var dialog: BottomSheetDialog? = null
    private lateinit var behavior: BottomSheetBehavior<View>
    private var handler: Handler = Handler(Looper.getMainLooper())
    private var runnableTime: Runnable? = null

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
            bottomSheet(fragment)
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

    private fun bottomSheet(fragment: Fragment) {
        val view = View.inflate(fragment.context, R.layout.dialog_bottom_sheet, null)
        dialog = fragment.context?.let { BottomSheetDialog(it) }
        dialog?.setContentView(view)
        behavior = BottomSheetBehavior.from(view.parent as View)
        val list = fragment.resources.getStringArray(R.array.calculator)
        val gridView = view.findViewById<GridView>(R.id.gridView)
        val editText = view.findViewById<TextInputEditText>(R.id.tvEditCalculator)
        val ivRemove = view.findViewById<ImageView>(R.id.ivRemove)
        var text = editText.text.toString()
        ivRemove.setOnClickListener {
            text.removeRange(text.length - 1 , text.length)
            editText.setText(text)
        }
        fragment.context?.let {context ->
            Utils.closeKeyboard(context, editText)
        }
        listGrid.value = list
        listGrid.observe(fragment) {array ->
            if (array.isNotEmpty()) {
                val repoAdapterCalculator = RepoAdapterCalculator(fragment, array) { index ->
                    if (index == 0 )  {
                        text = ""
                    }
                    if (index == 19) {
                        val listOpenBracket = text.split("(")
                        val listBackBracket = text.split(")")
                        when {
                            listOpenBracket.size != listBackBracket.size -> text = "請刪除多餘括號"
                            listOpenBracket.size == listBackBracket.size -> {
                                Log.d("ListBackBracket", listBackBracket.toString())
                                Utils.regOperatorBetweenBracket(text)
                            }
                            else -> ""
                        }
                    }
                    text += when(index) {
                        1 -> when {
                            text.isEmpty() || Utils.regCalculatorPrevious(text) -> "("
                            text.indexOf("(") == text.length - 1 || text.indexOf(")") == text.length - 1 -> ""
                            Utils.regNumberNext(text,text.indexOf("(") + 1) && Utils.regNumberPrevious(text) -> ")"
                            Utils.regNumberNext(text,text.indexOf(")") + 1) && Utils.regCalculatorPrevious(text) -> "("
                            else -> ""
                        }
                        2 -> if (!Utils.regNumberPreviousBackBracket(text)) "" else "%"
                        3 -> if (!Utils.regNumberPreviousBackBracket(text)) "" else "/"
                        4 -> "7"
                        5 -> "8"
                        6 -> "9"
                        7 -> if (!Utils.regNumberPreviousBackBracket(text)) "" else "*"
                        8 -> "4"
                        9 -> "5"
                        10 -> "6"
                        11 -> if (!Utils.regNumberPreviousBackBracket(text)) "" else "-"
                        12 -> "1"
                        13 -> "2"
                        14 -> "3"
                        15 -> if (!Utils.regNumberPreviousBackBracket(text)) "" else "+"
                        16 -> if (!Utils.regNumberPreviousBackBracket(text)) "" else "-"
                        17 -> "0"
                        18 -> if (!Utils.regNumberPreviousBackBracket(text)) "" else "."
                        else -> ""
                    }
                    editText.setText(text)
                }
                repoAdapterCalculator.notifyDataSetChanged()
                gridView.adapter = repoAdapterCalculator
            }
        }
    }

    private fun bottomSheetClick(fragment: Fragment, dialog: BottomSheetDialog, behavior: BottomSheetBehavior<View>) {
//        behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        fragment.context?.let {
            behavior.peekHeight = Utils.dpToPixel(it, 1600)
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

    //    fun fab(binding: FragmentBaseBinding, fragment: Fragment) {
//        binding.fab.visibility = View.VISIBLE
//        binding.fab.backgroundTintList = ResourcesCompat.getColorStateList(fragment.resources, R.color.purple_200, fragment.activity?.theme)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            binding.recyclerView.setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->
//
//            }
//        }
//    }

}