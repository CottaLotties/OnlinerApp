package com.example.onlinerapp.ui.main.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinerapp.SwipeToDeleteCallback
import com.example.onlinerapp.autoCleared
import com.example.onlinerapp.databinding.CartFragmentBinding
import com.example.onlinerapp.entities.Product
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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

        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                deleteFromCart(adapter.getItem(pos))
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.cartProductsList)
    }

    private fun deleteFromCart(product: Product) = runBlocking {
        launch{
            viewModel.deleteById(product.id)
        }
    }

    private fun setupObservers() {
        viewModel.selectedProducts.observe(viewLifecycleOwner, {
                    if (!it.isNullOrEmpty()) adapter.setItems(ArrayList(it))
        })
    }
}