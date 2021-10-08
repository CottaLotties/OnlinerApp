package com.example.onlinerapp.ui.main.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinerapp.databinding.CategoryItemBinding
import com.example.onlinerapp.entities.categories.Category
import java.util.*
import kotlin.collections.ArrayList

class CategoriesAdapter(private val listener: CategoryItemListener) : RecyclerView.Adapter<CategoryViewHolder>(), Filterable {

    interface CategoryItemListener {
        fun onClickedCategory(categoryKey: String)
    }

    private var original = ArrayList<Category>() // original categories array before filtration
    private var items = ArrayList<Category>()

    fun setItems(items: ArrayList<Category>) {
        this.items.clear()
        this.items.addAll(items)
        this.original.addAll(items)
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
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var resultList = ArrayList<Category>()
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    resultList = original
                } else {
                    for (row in original) {
                        if (row.name.toUpperCase(Locale.ROOT).contains(charSearch.toUpperCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = resultList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                items = results?.values as ArrayList<Category>
                notifyDataSetChanged()
            }

        }
    }
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