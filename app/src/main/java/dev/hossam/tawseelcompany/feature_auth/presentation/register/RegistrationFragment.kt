package dev.hossam.tawseelcompany.feature_auth.presentation.register

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.hossam.tawseelcompany.R
import dev.hossam.tawseelcompany.core.*
import dev.hossam.tawseelcompany.core.NavDir.REGISTRATION_TO_LOGIN
import dev.hossam.tawseelcompany.databinding.FragmentRegistrationBinding
import dev.hossam.tawseelcompany.feature_main.presentation.util.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::inflate) {

    private lateinit var mRequestPermissions: ActivityResultLauncher<Array<String>>
    private val REQUEST_CODE by lazy { 100 }
    private var isPermissionsGranted = false
    private var isTermsAccepted = false

    private val viewModel: RegistrationViewModel by viewModels()
    private val questionMarkSideBasedOnCurrentLTR = if (Resources.getSystem().configuration.locale.language.equals("en")) "?" else "؟"

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRequestPermissions = registerActivityForResult()
        handlePermissions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        onClicks()
        collectState()
        collectUiEvent()
    }

    private fun initView(){ binding.apply {
        changeAlreadyHaveAccountTextViewFieldStyles()
        registrationEtName.onTextChanged { viewModel.onEvent(RegistrationFormEvent.NameChanged(registrationEtName.text.toString())) }
        registrationEtTaxRegistry.onTextChanged { viewModel.onEvent(RegistrationFormEvent.TaxRegistryNumberChanged(registrationEtTaxRegistry.text.toString())) }
        registrationEtPhoneNum.onTextChanged { viewModel.onEvent(RegistrationFormEvent.PhoneNumberChanged(registrationEtPhoneNum.text.toString())) }
        registrationEtEmail.onTextChanged { viewModel.onEvent(RegistrationFormEvent.EmailChanged(registrationEtEmail.text.toString())) }
        registrationEtPassword.onTextChanged { viewModel.onEvent(RegistrationFormEvent.PasswordChanged(registrationEtPassword.text.toString())) }
        registrationEtRepeatedPassword.onTextChanged { viewModel.onEvent(RegistrationFormEvent.RepeatedPasswordChanged(registrationEtRepeatedPassword.text.toString())) }
        }
    }

    private fun onClicks() { binding.apply {
        registrationChBoxTerms.onClick {
            if (!isTermsAccepted){
                isTermsAccepted = true
                registrationChBoxTerms.setImageResource(R.drawable.ic_checked)
                viewModel.onEvent(RegistrationFormEvent.AcceptTerms(isTermsAccepted))
            } else {
                isTermsAccepted = false
                registrationChBoxTerms.setImageResource(R.drawable.ic_unchecked)
                viewModel.onEvent(RegistrationFormEvent.AcceptTerms(isTermsAccepted))
            }
        }
        registrationBtnLogin.onClick {
            viewModel.onEvent(RegistrationFormEvent.Submit)
            }

        registrationTvLogin.onClick {
            navigate(REGISTRATION_TO_LOGIN)
            }
        }
    }

    private fun collectState(){ binding.apply {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest { errorMessage ->
                withContext(Dispatchers.Main){
                    registrationEtName.error = errorMessage.fullNameError
                    registrationEtTaxRegistry.error = errorMessage.taxRegistryNumberError
                    registrationEtPhoneNum.error = errorMessage.phoneError
                    registrationEtEmail.error = errorMessage.emailError
                    registrationEtPassword.error = errorMessage.passwordError
                    registrationEtRepeatedPassword.error = errorMessage.repeatedPasswordError
                    errorMessage.termsError?.let {
                        registrationChBoxTerms.showSnackBar(it)
                        }
                    }
                }
            }
        }
    }

    private fun collectUiEvent(){
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            combine(viewModel.uiEvent, viewModel.uiText){ event, text ->
                when(event){
                    is UiEvent.ShowSnackBar -> requireView().showSnackBar(text.asString(requireContext()))
                    else -> {}
                }
            }
        }
    }

    private fun changeAlreadyHaveAccountTextViewFieldStyles() {
        val string = getString(R.string.already_have_an_account_log_in)
        val startIdx = string.indexOf(questionMarkSideBasedOnCurrentLTR) + 2
        val endIdx = string.lastIndex + 1
        val mColor = resources.getColor(R.color.orange)
        val sp = changeTextColor(startIdx, endIdx, string, mColor, isBold = true)
        binding.registrationTvLogin.text = sp
    }

    private fun handlePermissions(){
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            registerActivityForResult().launch(arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE))
            return
        } else {
            isPermissionsGranted = true
        }
    }

    private fun registerActivityForResult() = registerForActivityResult(ActivityResultContracts
        .RequestMultiplePermissions()){ permissions ->
        permissions.entries.forEach {
            val mPermissionName = it.key
            val isGranted = it.value
            if (isGranted){

            } else {
                requireContext().showToast(getString(R.string.permission_denied))
                requireActivity().finish()
            }
        }
    }

}