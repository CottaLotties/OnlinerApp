package com.example.onlinerapp.ui.main.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlinerapp.R
import com.example.onlinerapp.databinding.ProductItemBinding
import com.example.onlinerapp.entities.product.Product

class CartAdapter : RecyclerView.Adapter<CartViewHolder>() {

    interface CartItemListener

    private val items = ArrayList<Product>()

    fun setItems(items: ArrayList<Product>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding: ProductItemBinding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int{
        return items.size
    }

    fun getItem(pos: Int): Product {
        return items[pos]
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) = holder.bind(items[position])
}

class CartViewHolder(private val itemBinding: ProductItemBinding) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

    private lateinit var product: Product

    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: Product) {
        this.product = item
        itemBinding.productName.text = item.name
        itemBinding.productDescription.text = item.description
        if ((item.prices?.price_min?.amount != null)&&(item.prices.price_min.currency != null)) {
            itemBinding.productPrice.text = String.format(itemView.context.getString(R.string.price_from_to),
                item.prices.price_min.amount.toString(), item.prices.price_min.currency)
        } else itemBinding.productPrice.visibility = View.GONE
        Glide.with(itemBinding.root)
                .load("https:"+product.images.header)
                .into(itemBinding.productImage)
    }

    override fun onClick(v: View?) {
    }
}