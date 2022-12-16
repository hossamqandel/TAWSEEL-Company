package dev.hossam.tawseelcompany.feature_order.presentation.order_details

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.hossam.tawseelcompany.R
import dev.hossam.tawseelcompany.core.*
import dev.hossam.tawseelcompany.databinding.FragmentOrderDetailsBinding
import dev.hossam.tawseelcompany.feature_main.presentation.util.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding>(FragmentOrderDetailsBinding::inflate) {

    private lateinit var mRequestPermissions: ActivityResultLauncher<Array<String>>
    private val viewModel: OrderDetailsViewModel by viewModels()
    private val mItemsAdapter by lazy { ItemsAdapter() }
    private var isPermissionsGranted = false


    private val mCallBack = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRequestPermissions = registerActivityForResult()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClick()
        collectState()
        collectEvent()

    }

    private fun onClick(){ binding.apply {
        OnBackPressedDispatcher().addCallback(mCallBack)
        orderDetailsBtnBack.onClick { findNavController().popBackStack() }
        btnCallClient.onClick { callCustomer() }
    }}

    private fun collectState(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.orderState.combine(viewModel.itemsState.dropWhile { it.items.isEmpty() })
            { orderState, itemsState ->
                setupView(orderState)
                setupItemsRecycler(itemsState)
            }.collect()
        }


    }

    private fun setupView(orderState: OrderState){ binding.apply {
        orderState.apply {
            orderDetailsTempOrderDetails.apply {
                itemCompletedTvOrderNumber.text = getString(R.string.order_number).plus(orderNumber)
                itemCompletedTvOrderStatus.text = orderStatus.asResourceStatusString(requireContext())
                itemCompletedTvTime.text = time
                itemCompletedTvCostAmount.text = cost.plus(" " + getString(R.string.egp))
                itemCompletedTvEndPoint.text = address
            }

            orderDetailsTempCVReason.apply {
                tempRootCancellation.isVisible = isCancelled
                tempTvReason.text = cancellationReason
            }

            orderDetailsTempCustomer.apply {
                tempTvCustomerName.text = customerName
                tempTvCustomerAddress.text = address
                tempTvPaymentType.text = "Cash"
                tempTvCustomerPhone.text = customerPhone
            }
        }}}
    private fun setupItemsRecycler(itemsState: ItemsState){ binding.apply {
        itemsState.shippingCost?.let { mItemsAdapter.setDeliveryCost(shippingCost = it) }
        mItemsAdapter.setItems(items = itemsState.items)
        rvOrderDetail.adapter = mItemsAdapter
    }}



    private fun collectEvent(){binding.apply {
        collectLatestLifecycleFlow(viewModel.uiEvent){ event ->
            when(event){
                is UiEvent.Box -> {}
                is UiEvent.Navigate -> {}
                is UiEvent.ProgressBar -> {}
                is UiEvent.Shimmer -> { shimmerOrderDetails.showHideShimmer(event.isVisible) }
                is UiEvent.ShowSnackBar -> { showSnackBar(event.message) }
                is UiEvent.View -> { orderDetailsView.isVisible = event.isVisible }
            }
        }
    }}

    private fun callCustomer(){
        checkPermissions()
    }

    // Check Permission
    private fun checkPermissions(){
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            mRequestPermissions.launch(arrayOf(
                Manifest.permission.CALL_PHONE))
            return
        } else {
            isPermissionsGranted = true

            val phoneNumber by lazy { binding.orderDetailsTempCustomer.tempTvCustomerPhone.text.toString() }
            Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber")).also { mIntent ->
                startActivity(mIntent)
            }
        }
    }

    private fun registerActivityForResult() = registerForActivityResult(
        ActivityResultContracts
            .RequestMultiplePermissions()){ permissions ->
        permissions.entries.forEach {
            val mPermissionName = it.key
            val isGranted = it.value
            if (isGranted){

            } else {
                requireContext().showToast(getString(R.string.permission_call_phone_denied))
            }
        }
    }
}