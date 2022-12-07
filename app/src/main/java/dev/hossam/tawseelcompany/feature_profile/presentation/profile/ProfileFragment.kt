package dev.hossam.tawseelcompany.feature_profile.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.hossam.tawseelcompany.core.*
import dev.hossam.tawseelcompany.databinding.FragmentProfileBinding
import dev.hossam.tawseelcompany.feature_main.presentation.util.BaseFragment
import dev.hossam.tawseelcompany.feature_profile.domain.model.UpdateProfile
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        onClicks()
        collectEvents()
        collectState()
    }


    private fun onClicks() { binding.apply {
            profileBtnChangePass.onClick { viewModel.onEvent(ProfileEvent.ChangePassword) }
            profileBtnSaveChanges.onClick { saveNewChanges() }
        }
    }

    private fun saveNewChanges() { binding.apply {
            val avatar by lazy { "" }
            val name by lazy { profileEtFullName.text.toString().trim() }
            val phone by lazy { profileEtPhoneNum.text.toString().trim() }
            val email by lazy { profileEtEmail.text.toString().trim() }
            val address by lazy { profileEtAddress.text.toString().trim() }
            val newProfileInfo by lazy { UpdateProfile(name, phone, email, address) }
            viewModel.onEvent(ProfileEvent.SaveProfileChanges(newProfileInfo))
        }
    }

    private fun collectState(){ binding.apply {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest { profile ->
                initView(profile)
                }
            }
        }
    }

    private fun initView(profile: ProfileState){ binding.apply {
        profileEtFullName.setText(profile.name)
        profileEtPhoneNum.setText(profile.phoneNumber)
        profileEtEmail.setText(profile.email)
        profileEtAddress.setText(profile.address)
        }
    }
    private fun collectEvents() { binding.apply {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.uiEvent.collectLatest { event ->
                    when (event) {
                        is UiEvent.ProgressBar -> {}
                        is UiEvent.Box -> {}
                        is UiEvent.View -> {
                            btnChangeAvatar.isVisible = event.isVisible
                            ivDriverAvatar.isVisible = event.isVisible
                            profileConstraints.isVisible = event.isVisible
                        }
                        is UiEvent.Shimmer -> shimmerProfile.visibilityState(event.isVisible)
                        is UiEvent.ShowSnackBar -> requireView().showSnackBar(event.message)
                        is UiEvent.Navigate -> navigate(event.destination)
                    }
                }
            }
        }
    }

}