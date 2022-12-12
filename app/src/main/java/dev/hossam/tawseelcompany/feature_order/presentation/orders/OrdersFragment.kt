package dev.hossam.tawseelcompany.feature_order.presentation.orders

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.hossam.tawseelcompany.core.collectLatestLifecycleFlow
import dev.hossam.tawseelcompany.core.collectLifecycleFlow
import dev.hossam.tawseelcompany.databinding.FragmentOrdersBinding
import dev.hossam.tawseelcompany.feature_main.presentation.util.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding>(FragmentOrdersBinding::inflate) {

    private val TAG by lazy { OrdersFragment::class.java.simpleName }
    private val viewModel: OrdersViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectState()
    }
    
    private fun onClicks(){binding.apply {  }}


    private fun collectState(){
        collectLatestLifecycleFlow(viewModel.state.takeWhile { it.orders.isNotEmpty() }) { state ->
            Log.i(TAG, "collectState: ${state.orders}")
        }
    }
}