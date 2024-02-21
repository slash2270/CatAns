//package com.example.catans.viewmodel
//
//import android.content.Context
//import android.util.Log
//import android.view.View
//import android.widget.Toast
//import androidx.lifecycle.*
//import androidx.paging.LoadState
//import androidx.recyclerview.widget.LinearLayoutManager
//import coil.Coil
//import coil.ImageLoader
//import coil.request.CachePolicy
//import coil.util.CoilUtils
//import com.example.catans.MainActivity
//import com.example.catans.R
//import com.example.catans.adapter.FooterAdapter
//import com.example.catans.adapter.RepoAdapter
//import com.example.catans.databinding.FragmentFirstBinding
//import com.example.catans.fragment.FirstFragment
//import com.example.catans.model.Article
//import com.example.catans.model.DataModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import okhttp3.OkHttpClient
//
//class CurrencyViewModel: ViewModel() {
//
//    private lateinit var repoAdapter: RepoAdapter
//    private val listData: MutableLiveData<List<Article?>?> = MutableLiveData<List<Article?>?>()
//
//    fun getData(fragment: FirstFragment) {
//        repoAdapter = RepoAdapter(viewModelScope)
//        viewModelScope.launch(Dispatchers.Main) {
//            DataModel().getData(viewModelScope, repoAdapter).let { list ->
//                if (list?.isNotEmpty() == true) {
//                    listData.value = list.toSet().filterNotNull()
//                    listData.observe(fragment) { articles ->
//                        Log.d("MainViewModel articles","${articles?.size}")
//                    }
//                }
//            }
//        }
//    }
//
//    fun recycler(binding: FragmentFirstBinding, context: Context) {
//        binding.recyclerView.layoutManager = LinearLayoutManager(context)
//        binding.recyclerView.adapter = repoAdapter.withLoadStateFooter(FooterAdapter {
//            repoAdapter.retry()
//        })
//    }
//
//    fun adapter(binding: FragmentFirstBinding, context: Context) {
//        repoAdapter.addLoadStateListener {
//            when (it.refresh) {
//                is LoadState.NotLoading -> {
//                    binding.progressCircular.visibility = View.INVISIBLE
//                    binding.recyclerView.visibility = View.VISIBLE
//                }
//                is LoadState.Loading -> {
//                    binding.progressCircular.visibility = View.VISIBLE
//                    binding.recyclerView.visibility = View.INVISIBLE
//                }
//                is LoadState.Error -> {
//                    val state = it.refresh as LoadState.Error
//                    binding.progressCircular.visibility = View.INVISIBLE
//                    Toast.makeText(context, "Load Error: ${state.error.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//    fun coil(context: Context) {
//        val okHttpClient = OkHttpClient.Builder()
//            .cache(CoilUtils.createDefaultCache(context))
//            .build()
//        val imageLoader = ImageLoader.Builder(context)
//            .availableMemoryPercentage(0.2)
//            .diskCachePolicy(CachePolicy.ENABLED)
//            .placeholder(R.drawable.ic_launcher_foreground)
//            .error(R.drawable.ic_launcher_background)
//            .crossfade(true)
//            .crossfade(3000)
//            .okHttpClient { okHttpClient }
//            .build()
//        Coil.setImageLoader(imageLoader)
//    }
//
//}