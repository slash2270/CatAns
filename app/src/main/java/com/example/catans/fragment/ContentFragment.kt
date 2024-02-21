package com.example.catans.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.catans.databinding.FragmentContentBinding
import com.example.catans.viewmodel.ContentViewModel

class ContentFragment : Fragment() {

    private var _binding: FragmentContentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ContentViewModel by lazy { ViewModelProvider(this)[ContentViewModel()::class.java] }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init(binding, this)
        binding.model = viewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}