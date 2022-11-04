package com.abu_siddiq.groceryapp

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abu_siddiq.groceryapp.adapter.GroceryRecycleViewAdapter
import com.abu_siddiq.groceryapp.adapter.GroceryRecycleViewAdapter.GroceryItemClickInterface
import com.abu_siddiq.groceryapp.database.GroceryDatabase
import com.abu_siddiq.groceryapp.database.GroceryItems
import com.abu_siddiq.groceryapp.viewmodel.GroceryRepository
import com.abu_siddiq.groceryapp.viewmodel.GroceryViewModel
import com.abu_siddiq.groceryapp.viewmodel.GroceryViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), GroceryItemClickInterface {
    private lateinit var itemRecycleView: RecyclerView
    private lateinit var addFAB: FloatingActionButton
    private lateinit var list: List<GroceryItems>
    private lateinit var groceryRecycleViewAdapter: GroceryRecycleViewAdapter
    private lateinit var groceryViewModel: GroceryViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        itemRecycleView = findViewById(R.id.item_recycle_view)
        addFAB = findViewById(R.id.fabAdd)
        list = ArrayList()
        groceryRecycleViewAdapter = GroceryRecycleViewAdapter(list, this)
        itemRecycleView.layoutManager = LinearLayoutManager(this)
        itemRecycleView.adapter = groceryRecycleViewAdapter
        val groceryRepository = GroceryRepository(GroceryDatabase(this))
        val factory = GroceryViewModelFactory(groceryRepository)
        groceryViewModel = ViewModelProvider(this, factory)[GroceryViewModel::class.java]
        groceryViewModel.getAllGroceryItems().observe(this) {
            groceryRecycleViewAdapter.list = it
            groceryRecycleViewAdapter.notifyDataSetChanged()
        }
        addFAB.setOnClickListener {
            openDialog()
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun openDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.grocery_add_dialog)
        val cancelButton = dialog.findViewById<AppCompatButton>(R.id.item_cancel)
        val addButton = dialog.findViewById<AppCompatButton>(R.id.item_add)
        val itemEdt = dialog.findViewById<EditText>(R.id.add_item)
        val itemPriceEdt = dialog.findViewById<EditText>(R.id.add_item_price)
        val itemQuantityEdt = dialog.findViewById<EditText>(R.id.add_item_quantity)
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        addButton.setOnClickListener {
            val itemName: String = itemEdt.text.toString()
            val itemPrice: String = itemPriceEdt.text.toString()
            val itemQuantity: String = itemQuantityEdt.text.toString()
            val qty: Double = itemQuantity.toDouble()
            val pr: Double = itemPrice.toDouble()
            if (itemName.isNotEmpty() && itemPrice.isNotEmpty() && itemQuantity.isNotEmpty()) {
                val items = GroceryItems(itemName, qty, pr)
                groceryViewModel.insert(items)
                Toast.makeText(applicationContext, getString(R.string.add_item), Toast.LENGTH_SHORT).show()
                groceryRecycleViewAdapter.notifyDataSetChanged()
                dialog.dismiss()
            } else {
                Toast.makeText(applicationContext, getString(R.string.warning), Toast.LENGTH_SHORT)
                    .show()
            }

        }
        dialog.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick(groceryItems: GroceryItems) {
        groceryViewModel.delete(groceryItems)
        groceryRecycleViewAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext, getString(R.string.delete_toast), Toast.LENGTH_SHORT).show()
    }
}