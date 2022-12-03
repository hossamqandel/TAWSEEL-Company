package dev.hossam.tawseelcompany.feature_auth.presentation.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.hossam.tawseelcompany.R
import dev.hossam.tawseelcompany.core.*
import dev.hossam.tawseelcompany.databinding.FragmentLoginBinding
import dev.hossam.tawseelcompany.feature_main.presentation.util.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()
    private val mCallBack by lazy { object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        onClick()
        collectUiEvent()
    }


    private fun initView() { binding.apply {
        loginEtPhoneNum.onTextChanged { viewModel.onEvent(LoginFormEvent.PhoneChanged(loginEtPhoneNum.text.toString())) }
        loginEtPass.onTextChanged { viewModel.onEvent(LoginFormEvent.PasswordChanged(loginEtPass.text.toString())) }
        }
    }

    private fun onClick() {
        binding.btnLogin.onClick {
            collectState()
            viewModel.onEvent(LoginFormEvent.Submit)
        }

        requireActivity().onBackPressedDispatcher.addCallback(mCallBack)

    }

    private fun collectState() { binding.apply {
        lifecycleScope.launchWhenStarted{
            viewModel.state.collectLatest {
                withContext(Dispatchers.Main){
                    loginEtPhoneNum.error = it.phoneError
                    loginEtPass.error = it.passwordError
                }
               }
            }
        }
    }

    private fun collectUiEvent() { binding.apply {
        collectLatestLifecycleFlow(viewModel.uiEvent){ uiEvent ->
            when(uiEvent){
                is UiEvent.Navigate -> {}
                is UiEvent.ProgressBar -> {}
                is UiEvent.Shimmer -> {}
                is UiEvent.ShowSnackBar -> requireView().showSnackBar(uiEvent.message)
            }
        }
    }
    }
}