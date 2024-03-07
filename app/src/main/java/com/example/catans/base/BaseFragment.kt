package com.example.catans.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.catans.databinding.FragmentBaseBinding
import com.example.catans.util.EnumCurrencyUtils
import com.example.catans.util.EnumUtils

abstract class BaseFragment : Fragment() {
    abstract fun initView()

    lateinit var enumUtils: EnumUtils
    private var _binding: FragmentBaseBinding? = null
    val binding get() = _binding!!
    private val viewModel by lazy { BaseViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initView()
        _binding = FragmentBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (enumUtils) {
            EnumUtils.Departure, EnumUtils.Inbound -> {
                binding.itemCurrency.root.visibility = View.GONE
                viewModel.dataAirport(this, enumUtils)
                viewModel.adapterAirport(this, binding, enumUtils)
                context?.let { viewModel.recyclerAirport(it, binding) }
            }
            EnumUtils.Currency -> {
                binding.itemCurrency.root.visibility = View.VISIBLE
                viewModel.initCurrency(this, binding)
                viewModel.dataCurrency(this, binding, EnumCurrencyUtils.USD)
                viewModel.adapterData(this, binding, EnumCurrencyUtils.USD)
                viewModel.recyclerCurrency(this, binding)
            }
        }
        binding.model = viewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.destroy()
        _binding = null
    }
}