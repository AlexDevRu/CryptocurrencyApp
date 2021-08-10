package com.example.kulakov_p4_cryptocurrency_app.views

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.data.repositories.local.PreferencesStorage
import com.example.domain.use_cases.preferences.GetLanguageUseCase
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        /*if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkTheme)
        else
            setTheme(R.style.LightTheme)*/

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigationView.visibility = when(destination.id) {
                R.id.signInFragment,
                R.id.currencyDetailFragment,
                R.id.webViewFragment -> View.GONE
                else -> View.VISIBLE
            }
        }

        /*val settings: SharedPreferences = getSharedPreferences(PreferencesStorage.STORAGE_NAME, Context.MODE_PRIVATE)
        val theme = settings.getString(
            PreferencesStorage.THEME,
            PreferencesStorage.THEME_DEFAULT
        ).toString()

        if(theme == "dark") AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)*/
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(applySelectedAppLanguage(base))
    }

    private fun applySelectedAppLanguage(context: Context): Context {
        val settings: SharedPreferences = context.getSharedPreferences(PreferencesStorage.STORAGE_NAME, Context.MODE_PRIVATE)
        val lang = settings.getString(
            PreferencesStorage.LANGUAGE,
            PreferencesStorage.LANGUAGE_DEFAULT
        ).toString()
        val locale = Locale(lang)
        val newConfig = Configuration(context.resources.configuration)
        Locale.setDefault(locale)
        newConfig.setLocale(locale)
        return context.createConfigurationContext(newConfig)
    }
}