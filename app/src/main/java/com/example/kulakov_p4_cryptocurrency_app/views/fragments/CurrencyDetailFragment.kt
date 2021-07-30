package com.example.kulakov_p4_cryptocurrency_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.databinding.FragmentCurrencyDetailBinding
import com.example.kulakov_p4_cryptocurrency_app.parcelable.mappers.CurrencyArgMapper
import com.example.kulakov_p4_cryptocurrency_app.view_models.CurrencyDetailVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyDetailFragment: BaseFragment<FragmentCurrencyDetailBinding>
    (R.layout.fragment_currency_detail) {

    private val viewModel: CurrencyDetailVM by viewModels()

    private val args: CurrencyDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.apply {
            currencyImageView.transitionName = "currencyImageView${args.currencyArg.id}"
            currencyTitle.transitionName = "currencyTitle${args.currencyArg.id}"
            currencyPrice.transitionName = "currencyPrice${args.currencyArg.id}"
        }

        if(savedInstanceState == null) {
            viewModel.init(CurrencyArgMapper.toModel(args.currencyArg))
        }
    }
}