package com.example.onlinerapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.onlinerapp.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlinerapp.Resource
import com.example.onlinerapp.autoCleared

@AndroidEntryPoint
class MainFragment : Fragment(), CategoriesAdapter.CategoryItemListener {

    private var binding: MainFragmentBinding by autoCleared()
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: CategoriesAdapter

    companion object {
        fun newInstance() = MainFragment()
    }

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
    }

    private fun setupRecyclerView() {
        adapter = CategoriesAdapter(this)
        binding.categoriesList.layoutManager = LinearLayoutManager(requireContext())
        binding.categoriesList.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.categories.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClickedCategory(categoryKey: String) {
        TODO("Not yet implemented")
    }

}