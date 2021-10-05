package com.example.onlinerapp.ui.main.products

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlinerapp.databinding.ProductItemBinding
import com.example.onlinerapp.entities.product.Product

class ProductsAdapter(private val listener: ProductItemListener) : RecyclerView.Adapter<ProductViewHolder>() {

    interface ProductItemListener {
            fun onClickedProduct(productKey: String)
            fun onLongClickedProduct(link: String)
    }

    private val items = ArrayList<Product>()

    fun setItems(items: ArrayList<Product>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding: ProductItemBinding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, listener)
    }

    override fun getItemCount(): Int{
        return items.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.bind(items[position])
}

class ProductViewHolder(private val itemBinding: ProductItemBinding, private val listener: ProductsAdapter.ProductItemListener) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener, View.OnLongClickListener {

    private lateinit var product: Product

    init {
        itemBinding.root.setOnClickListener(this)
        itemBinding.root.setOnLongClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Product) {
        this.product = item
        itemBinding.productName.text = item.name
        itemBinding.productDescription.text = item.description
        if ((item.prices?.price_min?.amount != null)&&(item.prices.price_min.currency != null)) {
            itemBinding.productPrice.text =
                "От " + item.prices.price_min.amount + " " + item.prices.price_min.currency
        } else itemBinding.productPrice.visibility = View.GONE
        Glide.with(itemBinding.root)
            .load("https:" + product.images.header)
            .into(itemBinding.productImage)
    }

    override fun onClick(v: View?) {
        listener.onClickedProduct(product.key)
    }

    override fun onLongClick(v: View?): Boolean {
        listener.onLongClickedProduct(product.html_url)
        return true
    }
}