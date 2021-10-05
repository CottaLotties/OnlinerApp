package com.example.onlinerapp.ui.main.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.onlinerapp.Resource
import com.example.onlinerapp.entities.categories.Category
import com.example.onlinerapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: Repository
): ViewModel() {
    val categories: LiveData<Resource<List<Category>>> = repository.getCategories()
}