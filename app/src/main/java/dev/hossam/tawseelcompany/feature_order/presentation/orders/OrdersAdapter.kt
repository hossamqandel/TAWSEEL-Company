package dev.hossam.tawseelcompany.feature_order.presentation.orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import androidx.viewbinding.ViewBindings
import dev.hossam.tawseelcompany.R
import dev.hossam.tawseelcompany.core.Const
import dev.hossam.tawseelcompany.core.onClick
import dev.hossam.tawseelcompany.databinding.ItemCanceledOrderBinding
import dev.hossam.tawseelcompany.databinding.ItemCompletedOrderBinding
import dev.hossam.tawseelcompany.databinding.ItemStartedOrderBinding
import dev.hossam.tawseelcompany.feature_order.data.remote.dto.Data

class OrdersAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_COMPLETED = 1
        private const val VIEW_TYPE_CANCELLED = 2
        private const val VIEW_TYPE_STARTED = 3


        private fun customNavigation(view: View, id: Int) {
            val direction by lazy {
                OrdersFragmentDirections.actionOrdersFragmentToOrderDetailsFragment(orderId = id.toString())
            }
            Navigation.findNavController(view).navigate(directions = direction)
        }
    }

    var onItemClick: ((Int)-> Unit)? = null

    private var orders = emptyList<Data>()

    fun setOrders(orders: List<Data>){
        this.orders = orders.sortedByDescending { it.id }
        notifyDataSetChanged()
    }

    @Throws(RuntimeException::class)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val completedOrdersVH by lazy { ItemCompletedOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false) }
        val canceledOrdersVH by lazy { ItemCanceledOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false) }
        val startedOrdersVH by lazy { ItemStartedOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false) }

        return when(viewType){
            VIEW_TYPE_COMPLETED -> CompletedOrdersVH(completedOrdersVH)
            VIEW_TYPE_CANCELLED -> CanceledOrdersVH(canceledOrdersVH)
            VIEW_TYPE_STARTED -> StartedOrdersVH(startedOrdersVH)
            else -> throw RuntimeException("Can't decide the order view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is StartedOrdersVH -> { holder.bind(position) }
            is CompletedOrdersVH -> { holder.bind(position) }
            is CanceledOrdersVH -> { holder.bind(position) }
        }
    }

    override fun getItemCount(): Int = orders.size


    override fun getItemViewType(position: Int): Int {
        val mType = orders[position].status.lowercase()
        return when (mType) {
            Const.STARTED -> VIEW_TYPE_STARTED
            Const.COMPLETED -> VIEW_TYPE_COMPLETED
            else -> VIEW_TYPE_CANCELLED
        }
    }

    private inner class StartedOrdersVH(private val binding: ItemStartedOrderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            val getItemPos by lazy { orders[position] }
            binding.apply {
                itemStartedTvOrderNumber.text = getStringResourcePlusValue(binding, R.string.order_number, getItemPos.id.toString())
                itemStartedTvEndPoint.text = getItemPos.address
                itemStartedTvTime.text = getItemPos.created_at
                itemStartedTvCostAmount.text = binding.root.resources.getString(R.string.egp).plus(" ").plus(getItemPos.total)
                itemStartedBtnShowDetails.onClick {
                    customNavigation(binding.root, getItemPos.id)
                }
            }
        }
    }

    private inner class CompletedOrdersVH(private val binding: ItemCompletedOrderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            val getItemPos by lazy { orders[position] }
            binding.apply {
                itemCompletedTvOrderNumber.text = binding.root.resources.getString(R.string.order_number).plus(getItemPos.id.toString())
                itemCompletedTvEndPoint.text = getItemPos.address
                itemCompletedTvTime.text = getItemPos.created_at
                itemCompletedTvCostAmount.text = binding.root.resources.getString(R.string.egp).plus(" ").plus(getItemPos.total)
            }
        }
    }

    private inner class CanceledOrdersVH(private val binding: ItemCanceledOrderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            val getItemPos by lazy { orders[position] }
            binding.apply {
                itemCanceledTvOrderNumber.text = binding.root.resources.getString(R.string.order_number).plus(getItemPos.id.toString())
                itemCanceledTvEndPoint.text = getItemPos.address
                itemCanceledTvTime.text = getItemPos.created_at
                itemCanceledTvCostAmount.text = binding.root.resources.getString(R.string.egp).plus(" ").plus(getItemPos.total)
                itemCanceledBtnShowDetails.onClick { customNavigation(binding.root, getItemPos.id) }
            }
        }
    }

   }

    private fun getStringResourcePlusValue(binding: ViewBinding, resId: Int, value: String): String{
        return binding.root.resources.getString(resId).plus(value)
    }
