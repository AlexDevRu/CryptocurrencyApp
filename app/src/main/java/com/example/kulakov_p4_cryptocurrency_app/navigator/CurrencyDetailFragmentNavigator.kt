package com.example.kulakov_p4_cryptocurrency_app.navigator

import androidx.navigation.NavController
import com.example.kulakov_p4_cryptocurrency_app.views.fragments.CurrencyDetailFragmentDirections

class CurrencyDetailFragmentNavigator(navController: NavController): BaseNavigator(navController) {
    fun openLink(url: String, extras: androidx.navigation.Navigator.Extras? = null) {
        val direction = CurrencyDetailFragmentDirections.actionCurrencyDetailFragmentToWebViewFragment(url)
        navigate(direction, extras)
    }
}