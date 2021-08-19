package com.example.kulakov_p4_cryptocurrency_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.databinding.FragmentConverterBinding
import com.example.kulakov_p4_cryptocurrency_app.view_models.ConverterVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConverterFragment: BaseFragment<FragmentConverterBinding>
    (R.layout.fragment_converter) {

    private val viewModel: ConverterVM by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.fromNumber.setOnFocusChangeListener { _, b ->
            viewModel.firstSelected = b
        }

        binding.toNumber.setOnFocusChangeListener { _, b ->
            viewModel.secondSelected = b
        }

        binding.handler = object : Handler {
            override fun onFirstItemClick() {
                viewModel.setFromCurrency = true
                showChoice()
            }

            override fun onSecondItemClick() {
                viewModel.setFromCurrency = false
                showChoice()
            }
        }

        observe()
    }

    private fun observe() {
        viewModel.initialCurrencyError.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()) showSnackBar(it)
        }
    }

    private fun showChoice() {
        val action = ConverterFragmentDirections.actionConverterFragmentToCurrencyChoiceFragment()
        findNavController().navigate(action)
    }

    interface Handler {
        fun onFirstItemClick()
        fun onSecondItemClick()
    }
}