package com.example.onlinerapp.ui.main.productdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.onlinerapp.autoCleared
import com.example.onlinerapp.databinding.ProductDetailFragmentBinding
import com.example.onlinerapp.entities.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private var binding: ProductDetailFragmentBinding by autoCleared()
    private val viewModel: ProductDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("key")?.let { viewModel.start(it) }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.product.observe(viewLifecycleOwner, Observer {
                    bindProduct(it)
        })
    }

    private fun bindProduct(product: Product) {
        binding.productName.text = product.name
        binding.productDescription.text = product.description
    }
}