package dev.hossam.tawseelcompany.feature_home.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import dev.hossam.tawseelcompany.R
import dev.hossam.tawseelcompany.core.*
import dev.hossam.tawseelcompany.core.NavDir.HOME_TO_CREATE_ORDER
import dev.hossam.tawseelcompany.databinding.FragmentHomeBinding
import dev.hossam.tawseelcompany.feature_main.presentation.util.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val TAG by lazy { HomeFragment::class.java.simpleName }

    @Inject lateinit var mGlide: RequestManager
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClicks()
        collectState()
        collectUiEvents()
    }

    private fun initView(data: HomeState) {
        binding.apply {
            data.apply {
                homeTvCost.text = "$cost ${getString(R.string.egp)}"
                homeTvTime.text = time
                homeTvCompanyAddress.text = address
                mGlide.load(photoUrl).into(homeIvCompany)
                homeTvCompanyName.text = name
                homeTvCurrentLocation.text = location
                homeTvOrderStatus.text = requireContext().getOrderStringIdByStatus(orderStatus)
                homeTvOrderNumber.append(orderNumber)
            }
        }
    }

    private fun onClicks() { binding.apply {
            homeBtnShowDetails.onClick { viewModel.onEvent(HomeEvent.ShowDetails) }
            homeBtnOrderNow.onClick { navigate(HOME_TO_CREATE_ORDER) }
        }
    }


    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect {
                    initView(it)
                }
            }
        }
    }

    private fun collectUiEvents() {
        collectLifecycleFlow(viewModel.uiEvent) { event ->
            binding.apply {
                when (event) {
                    is UiEvent.Navigate -> findNavController().navigate(event.direction!!)
                    is UiEvent.ProgressBar -> {}
                    is UiEvent.Shimmer -> homeShimmer.visibilityState(event.isVisible)
                    is UiEvent.ShowSnackBar -> { requireView().showSnackBar(event.message) }
                    is UiEvent.View -> cvOrderBoard.isVisible = event.isVisible
                    is UiEvent.Box -> homeEmptyBox.root.isVisible = event.isVisible
                }
            }
        }
    }
}