package com.example.onlinerapp.ui.main.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import com.example.onlinerapp.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlinerapp.R
import com.example.onlinerapp.Resource
import com.example.onlinerapp.autoCleared
import androidx.navigation.fragment.findNavController

@AndroidEntryPoint
class MainFragment : Fragment(), CategoriesAdapter.CategoryItemListener {

    private val categoryKeyTag = "key"
    private var binding: MainFragmentBinding by autoCleared()
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setup recycleView and observer
        setupRecyclerView()
        setupObservers()
        binding.categoriesSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
    }

    private fun setupRecyclerView() {
        adapter = CategoriesAdapter(this)
        binding.categoriesList.layoutManager = LinearLayoutManager(requireContext())
        binding.categoriesList.adapter = adapter
    }

    private fun setupObservers() {

        viewModel.categories.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) {
                        adapter.setItems(ArrayList(it.data))
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onClickedCategory(categoryKey: String) {
        binding.categoriesSearch.setQuery("",false)
        findNavController().navigate(
                R.id.action_mainFragment_to_productsFragment,
                bundleOf(categoryKeyTag to categoryKey) // saving categoryKey
        )
    }
}