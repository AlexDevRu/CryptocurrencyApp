package com.example.kulakov_p4_cryptocurrency_app.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.databinding.FragmentSettingsBinding
import com.example.kulakov_p4_cryptocurrency_app.utils.LocaleManager
import com.example.kulakov_p4_cryptocurrency_app.view_models.SettingsVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment: BaseFragment<FragmentSettingsBinding>
    (R.layout.fragment_settings)  {

    private val viewModel by viewModels<SettingsVM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        viewModel.setCurrentLanguage()

        viewModel.setLanguage.observe(viewLifecycleOwner, {
            Log.w("asd", "locale observer $it")
            if(it != null) {
                viewModel.saveLanguage(it)
                requireActivity().recreate()
                //LocaleManager.setLocale(requireActivity(), it)
            }
        })

        if(viewModel.getTheme() == "dark")
            viewModel.selectedThemePosition.set(1)

        viewModel.setTheme.observe(viewLifecycleOwner, {
            if(it != null) {
                Log.w("asd", "theme observer $it")
                if(it == "dark") AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                viewModel.saveTheme(it)
            }
        })
    }
}