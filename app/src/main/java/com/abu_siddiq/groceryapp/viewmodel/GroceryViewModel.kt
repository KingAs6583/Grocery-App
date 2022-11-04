package com.abu_siddiq.groceryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abu_siddiq.groceryapp.database.GroceryItems
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GroceryViewModel(private val repository: GroceryRepository) : ViewModel() {
    @OptIn(DelicateCoroutinesApi::class)
    fun insert(items: GroceryItems) = GlobalScope.launch {
        repository.insert(items)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun delete(items: GroceryItems) = GlobalScope.launch {
        repository.delete(items)
    }

    fun getAllGroceryItems() = repository.getAllItems()
}

/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class GroceryViewModelFactory(private val repository: GroceryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GroceryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GroceryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}