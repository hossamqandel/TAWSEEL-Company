package dev.hossam.tawseelcompany.feature_order.presentation.orders

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.hossam.tawseelcompany.core.*
import dev.hossam.tawseelcompany.databinding.FragmentOrdersBinding
import dev.hossam.tawseelcompany.feature_main.presentation.util.BaseFragment
import dev.hossam.tawseelcompany.feature_order.data.remote.dto.Data
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding>(FragmentOrdersBinding::inflate) {

    private val TAG by lazy { OrdersFragment::class.java.simpleName }
//    @Inject lateinit var adapterOrdersFilterTypes: OrdersFilterAdapter
    private val adapterOrders by lazy { OrdersAdapter() }
    private val adapterOrdersFilterTypes by lazy { OrdersFilterAdapter(requireContext()) }
    private val viewModel: OrdersViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOrdersFilterTypesRecycler()
        onClicks()
        collectState()
        collectEvent()
    }
    
    private fun onClicks(){binding.apply {
        adapterOrdersFilterTypes.onFilterEventClick = { filterEvent ->
            viewModel.onEvent(filterEvent)
        }

//        adapterOrders.onItemClick = { orderId ->
//            Log.i(TAG, "onClicks: $orderId")
//        }
    }}


    private fun collectState(){
        collectLatestLifecycleFlow(viewModel.state) { state ->
            setupOrdersRecycler(state.orders)
        }
    }

    private fun collectEvent(){ binding.apply {
        collectLatestLifecycleFlow(viewModel.uiEvent) { event ->
            when(event){
                is UiEvent.ShowSnackBar -> showSnackBar(event.message)
                is UiEvent.ProgressBar -> {}
                is UiEvent.Navigate -> {}
                is UiEvent.Shimmer -> shimmerOrders.showHideShimmer(event.isVisible)
                is UiEvent.View -> ordersRvOrders.isVisible = event.isVisible
                is UiEvent.Box -> {}
            }
        }
    }}


    private fun setupOrdersRecycler(data: List<Data>){ binding.apply {
        adapterOrders.setOrders(data)
        ordersRvOrders.adapter = adapterOrders
    }}

    private fun setupOrdersFilterTypesRecycler(){ binding.apply {
        ordersRvFilterTypes.adapter = adapterOrdersFilterTypes
    }}
}