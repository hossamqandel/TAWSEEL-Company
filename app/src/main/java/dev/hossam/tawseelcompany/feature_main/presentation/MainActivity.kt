package dev.hossam.tawseelcompany.feature_main.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import dev.hossam.tawseelcompany.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigationView()
        hideBottomWithSomeFragments()
    }


    private fun setupBottomNavigationView(){
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottomNavView)
        val navController = Navigation.findNavController(this, R.id.fragmentContainerView)
        bottomNavView.setupWithNavController(navController)
        NavigationUI.setupWithNavController(bottomNavView, navController)
    }

    private fun hideBottomWithSomeFragments(){
        val navController = Navigation.findNavController(this, R.id.fragmentContainerView)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.splashFragment -> { findViewById<BottomNavigationView>(R.id.bottomNavView).visibility = View.GONE }
                R.id.loginFragment -> { findViewById<BottomNavigationView>(R.id.bottomNavView).visibility = View.GONE }
                R.id.registrationFragment -> { findViewById<BottomNavigationView>(R.id.bottomNavView).visibility = View.GONE }
                R.id.orderDetailsFragment -> { findViewById<BottomNavigationView>(R.id.bottomNavView).visibility = View.GONE }
//                R.id.notificationsFragment -> { findViewById<BottomNavigationView>(R.id.bottomNavView).visibility = View.GONE }
                else -> {
                    findViewById<BottomNavigationView>(R.id.bottomNavView).visibility = View.VISIBLE }
            }
        }
    }
}