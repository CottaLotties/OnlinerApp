package com.example.onlinerapp.ui.main.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinerapp.databinding.CategoryItemBinding
import com.example.onlinerapp.entities.categories.Category

class CategoriesAdapter(private val listener: CategoryItemListener) : RecyclerView.Adapter<CategoryViewHolder>() {

    interface CategoryItemListener {
        fun onClickedCategory(categoryKey: String)
    }

    private val items = ArrayList<Category>()

    fun setItems(items: ArrayList<Category>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding: CategoryItemBinding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding, listener)
    }

    override fun getItemCount(): Int{
        return items.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) = holder.bind(items[position])
}

class CategoryViewHolder(private val itemBinding: CategoryItemBinding, private val listener: CategoriesAdapter.CategoryItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var category: Category

    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: Category) {
        this.category = item
        itemBinding.categoryName.text = item.name
    }

    override fun onClick(v: View?) {
        listener.onClickedCategory(category.key)
    }
}