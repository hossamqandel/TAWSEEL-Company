package dev.hossam.tawseelcompany.feature_profile.presentation.change_password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.hossam.tawseelcompany.R
import dev.hossam.tawseelcompany.core.*
import dev.hossam.tawseelcompany.databinding.FragmentChangePasswordBinding
import dev.hossam.tawseelcompany.feature_auth.presentation.register.RegistrationFormEvent
import dev.hossam.tawseelcompany.feature_main.presentation.util.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding>(FragmentChangePasswordBinding::inflate) {

    private val viewModel: ChangePasswordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClicks()
        collectState()
        collectUiEvents()
        sendLiveValues()

    }

    private fun sendLiveValues(){ binding.apply {
        etNewPass.onTextChanged { viewModel.onEvent(RegistrationFormEvent.PasswordChanged(it)) }
        etNewPassConfirm.onTextChanged { viewModel.onEvent(RegistrationFormEvent.RepeatedPasswordChanged(it)) }
    }}

    private fun collectState(){
        collectLatestLifecycleFlow(viewModel.state){ updateProfileState ->
            setupViews(updateProfileState)
        }
    }

    private fun collectUiEvents(){ binding.apply {
        collectLatestLifecycleFlow(viewModel.uiEvent){ event ->
            when(event){
                is UiEvent.ShowSnackBar -> showSnackBar(event.message)
                else -> {}
                }
            }
        }
    }


    private fun setupViews(changePasswordState: UpdatePasswordState){ binding.apply {
        textInputLayout3.error = changePasswordState.passwordError
        textInputLayout4.error = changePasswordState.repeatedPasswordError
        }
    }

    private fun onClicks(){ binding.apply {
        btnSaveChanges.onClick { viewModel.onEvent(RegistrationFormEvent.Submit) }
        changePassBtnBack.onClick { findNavController().popBackStack() }
        }
    }

}