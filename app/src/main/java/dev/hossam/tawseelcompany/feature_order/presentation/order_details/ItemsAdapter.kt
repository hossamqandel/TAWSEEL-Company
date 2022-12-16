package dev.hossam.tawseelcompany.feature_order.presentation.order_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import dev.hossam.tawseelcompany.databinding.ItemItemsBinding
import dev.hossam.tawseelcompany.feature_order.data.remote.dto.Data
import dev.hossam.tawseelcompany.feature_order.data.remote.dto.Item


class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ItemsVH>() {

    private var items: List<Item> = emptyList()
    private var shippingCost: String? = "0"

    fun setDeliveryCost(shippingCost: String){
        this.shippingCost = shippingCost
    }

    fun setItems(items: List<Item>){
        this.items = items
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsVH {
        val view by lazy { ItemItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false) }
        return ItemsVH(view)
    }

    override fun onBindViewHolder(holder: ItemsVH, position: Int) {
        holder.bind(position = position)
    }

    override fun getItemCount(): Int = items.size

    inner class ItemsVH(val binding: ItemItemsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){ binding.apply {
            val getItemPos by lazy { items[position] }

            itemItemsTvOrderName.text = getItemPos.item
            itemItemsTvOrderCost.text = getItemPos.price

            if (position == items.size - 1){
                itemItemsTvFullCost.isVisible = true
                itemItemsTvDeliveryCost.isVisible = true

                itemItemsTvFullCostValue.isVisible = true
                itemItemsTvFullCostValue.text = getItemPos.total

                tvShippingCostValue.isVisible = true
                tvShippingCostValue.text = shippingCost
                 //TODO change total to delivery cost value
            }









        }}
    }
}