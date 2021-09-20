package com.example.onlinerapp.ui.main.products

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlinerapp.R
import com.example.onlinerapp.Resource
import com.example.onlinerapp.autoCleared
import com.example.onlinerapp.databinding.ProductsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment(), ProductsAdapter.ProductItemListener {

    private var binding: ProductsFragmentBinding by autoCleared()
    private val viewModel: ProductsViewModel by viewModels()
    private lateinit var adapter: ProductsAdapter

    /*companion object {
        fun newInstance() = ProductsFragment()
    }*/

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = ProductsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("key")?.let { viewModel.start(it) }
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = ProductsAdapter(this)
        binding.productsList.layoutManager = LinearLayoutManager(requireContext())
        binding.productsList.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.products.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onClickedProduct(productKey: String) {
        findNavController().navigate(
            R.id.action_productsFragment_to_productDetailFragment,
            bundleOf("key" to productKey)
        )
    }

    override fun onLongClickedProduct(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW)
        browserIntent.data = Uri.parse(link)
        activity?.startActivity(browserIntent)
    }
}