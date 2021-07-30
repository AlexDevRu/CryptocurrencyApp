package com.example.kulakov_p4_cryptocurrency_app.navigator

import androidx.navigation.NavController

class Navigator private constructor(private val navController: NavController) {

    val signInFragmentNavigator = SignInFragmentNavigator(navController)
    val mainFragmentNavigator = MainFragmentNavigator(navController)

    fun navigateBack() {
        navController.navigateUp()
    }

    companion object {
        private lateinit var INSTANCE: Navigator

        fun getInstance() = INSTANCE

        fun init(navController: NavController) {
            INSTANCE = Navigator(navController)
        }
    }
}