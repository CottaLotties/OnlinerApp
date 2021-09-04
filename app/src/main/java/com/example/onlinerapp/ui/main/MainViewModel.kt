package com.example.onlinerapp.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.onlinerapp.repository.CategoryRepository

class MainViewModel @ViewModelInject constructor(
    private val repository: CategoryRepository
): ViewModel() {
    val categories = repository.getCategories()
}