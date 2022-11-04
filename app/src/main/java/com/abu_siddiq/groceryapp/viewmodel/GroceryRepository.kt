package com.abu_siddiq.groceryapp.viewmodel

import com.abu_siddiq.groceryapp.database.GroceryDatabase
import com.abu_siddiq.groceryapp.database.GroceryItems

class GroceryRepository(private val db: GroceryDatabase) {
    fun insert(items: GroceryItems) = db.getGroceryDao().insert(items)
    fun delete(items: GroceryItems) = db.getGroceryDao().delete(items)

    fun getAllItems() = db.getGroceryDao().getAllGroceryItems()
}
