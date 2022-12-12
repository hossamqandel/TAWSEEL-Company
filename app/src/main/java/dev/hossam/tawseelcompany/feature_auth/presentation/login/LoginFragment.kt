package dev.hossam.tawseelcompany.feature_auth.presentation.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.hossam.tawseelcompany.core.*
import dev.hossam.tawseelcompany.databinding.FragmentLoginBinding
import dev.hossam.tawseelcompany.feature_main.presentation.util.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()
//    private val mCallBack by lazy { object : OnBackPressedCallback(true){
//            override fun handleOnBackPressed() {
//                findNavController().popBackStack()
//            }
//        } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        onClick()
        collectEvents()
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


    }

    private fun collectState() { binding.apply {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest {
                withContext(Dispatchers.Main) {
                    loginEtPhoneNum.error = it.phoneError
                    loginEtPass.error = it.passwordError
                    }
               }
            }
        }
    }

    private fun collectEvents(){ binding.apply {
        collectLatestLifecycleFlow(viewModel.uiEvent){ event ->
            when(event){
                is UiEvent.Navigate -> navigate(event.destination)
                is UiEvent.ShowSnackBar -> showSnackBar(event.message)
                else -> {}
                }
            }
        }
    }
}