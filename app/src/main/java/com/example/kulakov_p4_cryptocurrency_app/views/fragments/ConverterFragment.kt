package com.example.kulakov_p4_cryptocurrency_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.databinding.FragmentConverterBinding
import com.example.kulakov_p4_cryptocurrency_app.view_models.ConverterVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConverterFragment: BaseFragment<FragmentConverterBinding>
    (R.layout.fragment_converter) {

    private val viewModel: ConverterVM by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.fromNumber.setOnFocusChangeListener { _, b ->
            if(b) {
                binding.fromNumber.setTextColor(resources.getColor(R.color.blue))
                viewModel.setFirstSelected()
            }
            else {
                binding.fromNumber.setTextColor(resources.getColor(R.color.black))
            }
        }

        binding.toNumber.setOnFocusChangeListener { _, b ->
            if(b) {
                binding.toNumber.setTextColor(resources.getColor(R.color.blue))
                viewModel.setSecondSelected()
            }
            else
                binding.toNumber.setTextColor(resources.getColor(R.color.black))
        }
    }
}