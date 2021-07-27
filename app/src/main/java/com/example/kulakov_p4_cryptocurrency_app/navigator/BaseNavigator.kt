package com.example.kulakov_p4_cryptocurrency_app.navigator

import androidx.navigation.NavController
import androidx.navigation.NavDirections

abstract class BaseNavigator(private val navController: NavController) {
    protected fun navigate(directions: NavDirections, extras: androidx.navigation.Navigator.Extras? = null) {
        if(extras == null)
            navController.navigate(directions)
        else
            navController.navigate(directions, extras)
    }
}