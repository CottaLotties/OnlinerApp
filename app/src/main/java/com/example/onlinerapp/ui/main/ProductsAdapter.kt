package com.example.onlinerapp.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinerapp.databinding.CategoryItemBinding
import com.example.onlinerapp.entities.Category
import com.example.onlinerapp.entities.Product

class ProductsAdapter(private val listener: ProductItemListener) : RecyclerView.Adapter<ProductViewHolder>() {

    interface ProductItemListener {
    }

    private val items = ArrayList<Product>()

    fun setItems(items: ArrayList<Product>) {
        Log.d("HERE", items.size.toString())
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding: CategoryItemBinding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, listener)
    }

    override fun getItemCount(): Int{
        return items.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.bind(items[position])
}

class ProductViewHolder(private val itemBinding: CategoryItemBinding, private val listener: ProductsAdapter.ProductItemListener) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

    private lateinit var product: Product

    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: Product) {
        this.product = item
        itemBinding.categoryName.text = item.name
    }

    override fun onClick(v: View?) {
    }
}