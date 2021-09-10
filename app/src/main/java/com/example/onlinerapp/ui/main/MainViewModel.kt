package com.example.onlinerapp.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.onlinerapp.repository.Repository

class MainViewModel @ViewModelInject constructor(
    repository: Repository
): ViewModel() {
    val categories = repository.getCategories()
}