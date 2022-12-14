package dev.hossam.tawseelcompany.feature_order.presentation.orders

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.hossam.tawseelcompany.R
import dev.hossam.tawseelcompany.core.onClick
import dev.hossam.tawseelcompany.databinding.ItemOrdersFilterTypesBinding

class OrdersFilterAdapter constructor(
    private val appContext: Context
) : RecyclerView.Adapter<OrdersFilterAdapter.OrdersFilterVH>() {

    private val TAG by lazy { OrdersFilterAdapter::class.java.simpleName }

    private val ALL_ORDERS by lazy { appContext.getString(R.string.all_orders) }
    private val COMPLETED_ORDERS by lazy { appContext.getString(R.string.completed) }
    private val CURRENT_ORDERS by lazy { appContext.getString(R.string.current_orders) }
    private val EXPIRED_ORDERS by lazy { appContext.getString(R.string.expired_orders) }

    private val titlesFilter by lazy { listOf(
        ALL_ORDERS,
        COMPLETED_ORDERS,
        CURRENT_ORDERS,
        EXPIRED_ORDERS)
    }



    private val DEFAULT_FILTER_TYPE_INDEX = titlesFilter.indexOf(ALL_ORDERS)
    private var rowIndex = DEFAULT_FILTER_TYPE_INDEX
    var onFilterEventClick: ((OrdersFilterEvent)-> Unit) ?= null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersFilterVH {
        val ordersVH by lazy { ItemOrdersFilterTypesBinding.inflate(LayoutInflater.from(parent.context), parent, false) }
        return OrdersFilterVH(ordersVH)
    }

    override fun onBindViewHolder(holder: OrdersFilterVH, position: Int) {
        holder.bind(position)
    }


    override fun getItemCount(): Int = titlesFilter.size

    inner class OrdersFilterVH(private val binding: ItemOrdersFilterTypesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){ binding.apply {
            tvFilterTitle.text = titlesFilter[position]

            cvFilter.onClick {
                rowIndex = layoutPosition
                notifyDataSetChanged()
                sendClickedItemEvent(position)
            }

            if (rowIndex == layoutPosition){
                cvFilter.setCardBackgroundColor(Color.parseColor("#FF6600"))
                tvFilterTitle.setTextColor(Color.WHITE)
            }
            else {
                cvFilter.setCardBackgroundColor(Color.parseColor("#FFFFFFFF"))
                tvFilterTitle.setTextColor(ColorStateList.valueOf(cvFilter.context.resources.getColor(R.color.black)))
            }
        }}
    }

    private fun sendClickedItemEvent(position: Int){
        when(titlesFilter[position]){
            ALL_ORDERS -> { onFilterEventClick?.invoke(OrdersFilterEvent.All) }
            CURRENT_ORDERS -> { onFilterEventClick?.invoke(OrdersFilterEvent.Started) }
            COMPLETED_ORDERS -> { onFilterEventClick?.invoke(OrdersFilterEvent.Completed) }
            EXPIRED_ORDERS -> { onFilterEventClick?.invoke(OrdersFilterEvent.Canceled) }
        }
    }




}