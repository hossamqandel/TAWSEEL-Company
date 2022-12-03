package dev.hossam.tawseelcompany.feature_main.presentation

import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.hossam.tawseelcompany.R
import dev.hossam.tawseelcompany.core.NavDir.SPLASH_TO_REGISTRATION
import dev.hossam.tawseelcompany.core.SharedPref
import dev.hossam.tawseelcompany.core.navigate
import dev.hossam.tawseelcompany.databinding.FragmentSplashBinding
import dev.hossam.tawseelcompany.feature_main.presentation.util.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override fun onStart() {
        super.onStart()
        authState()
    }

    private fun authState() {
        lifecycleScope.launchWhenStarted {
            if (SharedPref.getUserToken().isNullOrBlank()) {
                delay(2000L)
                withContext(Dispatchers.Main) {
                    navigate(SPLASH_TO_REGISTRATION)
                }
            }
//            else navigate(SPLASH_TO_HOME)
        }

    }
}