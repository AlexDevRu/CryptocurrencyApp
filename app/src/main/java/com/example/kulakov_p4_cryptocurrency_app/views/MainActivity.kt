package com.example.kulakov_p4_cryptocurrency_app.views

import android.app.SearchManager
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.domain.use_cases.preferences.GetLanguageUseCase
import com.example.domain.use_cases.preferences.GetThemeUseCase
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.binding_adapters.SEARCHABLE_DESTINATION
import com.example.kulakov_p4_cryptocurrency_app.databinding.ActivityMainBinding
import com.example.kulakov_p4_cryptocurrency_app.databinding.LayoutNavViewHeaderBinding
import com.example.kulakov_p4_cryptocurrency_app.utils.SearchDestination
import com.example.kulakov_p4_cryptocurrency_app.view_models.MainActivityVM
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    val mainViewModel: MainActivityVM by viewModels()

    private lateinit var binding: ActivityMainBinding

    private lateinit var headerBinding: LayoutNavViewHeaderBinding

    @Inject lateinit var getThemeUseCase: GetThemeUseCase
    @Inject lateinit var getLanguageUseCase: GetLanguageUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setConfig()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.drawerLayout.setDrawerLockMode(
                when(destination.id) {
                    R.id.signInFragment,
                    R.id.currencyDetailFragment,
                    R.id.currencyChoiceFragment,
                    R.id.webViewFragment -> DrawerLayout.LOCK_MODE_LOCKED_CLOSED
                    else -> DrawerLayout.LOCK_MODE_UNLOCKED
                }
            )
        }

        mainViewModel.openNavigationView.observe(this) {
            binding.drawerLayout.open()
        }

        binding.navView.menu.findItem(R.id.signOutMenuItem).setOnMenuItemClickListener {
            mainViewModel.signOut()
            navController.navigate(R.id.signInFragment)
            true
        }

        headerBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.layout_nav_view_header,
            binding.navView, false
        )

        binding.navView.addHeaderView(headerBinding.root)

        mainViewModel.currentUser.observe(this) {
            headerBinding.userVM = it
        }
    }

    private fun setConfig() {
        val lang = getLanguageUseCase.invoke()
        val theme = getThemeUseCase.invoke()

        Log.d("asd", "from shared preferences: lang $lang, theme $theme")

        val locale = Locale(lang)
        val newConfig = Configuration(resources.configuration)
        Locale.setDefault(locale)
        newConfig.setLocale(locale)
        baseContext.resources.updateConfiguration(newConfig, baseContext.resources.displayMetrics)

        AppCompatDelegate.setDefaultNightMode(
            if(theme == "dark") AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleNewIntent(intent)
    }

    private fun handleNewIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val appSearchData = intent.getBundleExtra(SearchManager.APP_DATA) ?: return
            val destination =
                (appSearchData.getSerializable(SEARCHABLE_DESTINATION) as? SearchDestination)
                    ?: return
            val query = intent.getStringExtra(SearchManager.QUERY) ?: return
            mainViewModel.onNewSearchResultReceived(query, destination)
        }
    }
}