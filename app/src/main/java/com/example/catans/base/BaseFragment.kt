package com.example.catans.base

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.catans.R
import com.example.catans.databinding.FragmentAirportBinding
import com.example.catans.util.EnumAirport
import com.example.catans.viewmodel.AirportViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment : Fragment() {
    abstract fun initView()

    lateinit var enumAirport: EnumAirport
    private var _binding: FragmentAirportBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy { AirportViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initView()
        _binding = FragmentAirportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            viewModel.getData(this, enumAirport)
            viewModel.adapter(it, binding)
            viewModel.recycler(it, binding)
        }
        binding.model = viewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}