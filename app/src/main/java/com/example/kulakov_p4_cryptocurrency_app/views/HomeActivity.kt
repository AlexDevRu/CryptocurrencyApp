package com.example.kulakov_p4_cryptocurrency_app.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.bottomNavigationView.visibility = when(destination.id) {
                R.id.signInFragment,
                R.id.currencyDetailFragment,
                R.id.webViewFragment -> View.GONE
                else -> View.VISIBLE
            }
        }
    }
}