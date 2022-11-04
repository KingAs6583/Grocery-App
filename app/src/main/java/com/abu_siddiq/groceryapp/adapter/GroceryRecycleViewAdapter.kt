package com.abu_siddiq.groceryapp.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abu_siddiq.groceryapp.R
import com.abu_siddiq.groceryapp.database.GroceryItems
import java.util.*

class GroceryRecycleViewAdapter(
    var list: List<GroceryItems>,
    private val groceryItemClickInterface: GroceryItemClickInterface
) : RecyclerView.Adapter<GroceryRecycleViewAdapter.GroceryViewHolder>() {

    inner class GroceryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName = itemView.findViewById<TextView>(R.id.item_name)!!
        val itemQuantity = itemView.findViewById<TextView>(R.id.item_quantity)!!
        val itemRate = itemView.findViewById<TextView>(R.id.item_rate)!!
        val total = itemView.findViewById<TextView>(R.id.item_total_cost_amount)!!
        val deleteItem = itemView.findViewById<ImageView>(R.id.item_delete_icon)!!
    }


    interface GroceryItemClickInterface {
        fun onItemClick(groceryItems: GroceryItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.grocery_recycle_view_item, parent, false)
        return GroceryViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        holder.itemName.text = list[position].itemName
        holder.itemQuantity.text = list[position].itemQuantity.toString()
        holder.itemRate.text = "₹" + list[position].itemPrice.toString()
        val itemTotal: Double = list[position].itemQuantity * list[position].itemPrice
        holder.total.text = "₹$itemTotal"
//        holder.total.text = NumberFormat.getCurrencyInstance().format(itemTotal)
        Log.e("mainActivity",list[position].itemPrice.toString())
        holder.deleteItem.setOnClickListener {
            groceryItemClickInterface.onItemClick(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}