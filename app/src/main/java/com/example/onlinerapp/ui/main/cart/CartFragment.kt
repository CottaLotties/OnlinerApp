package com.example.onlinerapp.ui.main.cart

import android.os.Bundle
import android.view.*
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinerapp.SwipeToDeleteCallback
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
        setHasOptionsMenu(true)
        binding = CartFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    fun removeAllFromCart() {
            viewModel.removeAllFromCart()
            NotificationManagerCompat.from(requireContext()).cancelAll()
    }

    private fun setupRecyclerView() {
        adapter = CartAdapter()
        binding.cartProductsList.layoutManager = LinearLayoutManager(requireContext())
        binding.cartProductsList.adapter = adapter

        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                viewModel.deleteById(adapter.getItem(pos).id)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.cartProductsList)
    }

    private fun setupObservers() {
        viewModel.selectedProducts.observe(viewLifecycleOwner, {
            adapter.setItems(ArrayList(it))
        })
    }
}