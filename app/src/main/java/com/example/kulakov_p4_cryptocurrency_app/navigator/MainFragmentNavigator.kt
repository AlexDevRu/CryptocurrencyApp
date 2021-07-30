package com.example.kulakov_p4_cryptocurrency_app.navigator

import androidx.navigation.NavController
import com.example.domain.models.Currency
import com.example.kulakov_p4_cryptocurrency_app.parcelable.mappers.CurrencyArgMapper
import com.example.kulakov_p4_cryptocurrency_app.views.fragments.MainFragmentDirections

class MainFragmentNavigator(navController: NavController): BaseNavigator(navController) {
    fun showCurrencyDetail(currency: Currency, extras: androidx.navigation.Navigator.Extras? = null) {
        val arg = CurrencyArgMapper.fromModel(currency)
        val direction = MainFragmentDirections.actionMainFragmentToCurrencyDetailFragment(arg)
        navigate(direction, extras)
    }
}