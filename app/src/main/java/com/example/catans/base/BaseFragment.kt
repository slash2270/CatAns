package com.example.catans.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.catans.databinding.FragmentBaseBinding
import com.example.catans.util.EnumUtils

abstract class BaseFragment : Fragment() {
    abstract fun initView()

    lateinit var enumUtils: EnumUtils
    private var _binding: FragmentBaseBinding? = null
    private val binding get() = _binding!!
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
                viewModel.dataAirport(this, enumUtils)
                viewModel.adapterAirport(this, binding)
                context?.let { viewModel.recyclerAirport(it, binding) }
            }
            EnumUtils.Currency -> {
                viewModel.bottomSheet(this)
                viewModel.adapterData(this, binding)
                viewModel.dataCurrency(this, binding)
                viewModel.listData.observe(viewLifecycleOwner) {
                    viewModel.recyclerCurrency(binding, this)
                }
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