package dev.hossam.tawseelcompany.feature_main.presentation

import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.hossam.tawseelcompany.R
import dev.hossam.tawseelcompany.core.NavDir.SPLASH_TO_HOME
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

    companion object {
        private var isFirstVisit = true
    }

    override fun onStart() {
        super.onStart()
        authState()
    }

    override fun onStop() {
        super.onStop()
        isFirstVisit = false
    }

    private fun authState() {
        lifecycleScope.launchWhenStarted {
            if (SharedPref.getUserToken().isNullOrBlank()) {
                delay(2000L)
                withContext(Dispatchers.Main) {
                    navigate(SPLASH_TO_REGISTRATION)
                }
            }
            else if (SharedPref.getUserToken().isNotBlank()){
                if (isFirstVisit){
                    withContext(Dispatchers.Main){
                        delay(2000L)
                        navigate(SPLASH_TO_HOME)
                    }
                } else navigate(SPLASH_TO_HOME)
            }
        }

    }
}