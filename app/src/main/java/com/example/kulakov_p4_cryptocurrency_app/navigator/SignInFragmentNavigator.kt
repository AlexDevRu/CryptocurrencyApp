package com.example.kulakov_p4_cryptocurrency_app.navigator

import androidx.navigation.NavController
import com.example.kulakov_p4_cryptocurrency_app.views.fragments.SignInFragmentDirections

class SignInFragmentNavigator(navController: NavController): BaseNavigator(navController) {
    fun showMain(extras: androidx.navigation.Navigator.Extras? = null) {
        val direction = SignInFragmentDirections.actionSignInFragmentToMainFragment()
        navigate(direction, extras)
    }
}