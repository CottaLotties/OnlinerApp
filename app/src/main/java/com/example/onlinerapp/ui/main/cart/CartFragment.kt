package com.example.onlinerapp.ui.main.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlinerapp.autoCleared
import com.example.onlinerapp.databinding.CartFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(), CartAdapter.CartItemListener {

    companion object {
        fun newInstance() = CartFragment()
    }

    private var binding: CartFragmentBinding by autoCleared()
    private val viewModel: CartViewModel by viewModels()
    private lateinit var adapter: CartAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = CartFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = CartAdapter(this)
        binding.cartProductsList.layoutManager = LinearLayoutManager(requireContext())
        binding.cartProductsList.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.selectedProducts.observe(viewLifecycleOwner, {
                    if (!it.isNullOrEmpty()) adapter.setItems(ArrayList(it))
        })
    }
}