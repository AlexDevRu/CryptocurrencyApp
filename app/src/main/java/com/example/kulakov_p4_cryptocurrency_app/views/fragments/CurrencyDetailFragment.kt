package com.example.kulakov_p4_cryptocurrency_app.views.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
            currencyPercentChange1h.transitionName = "percentChange1h${args.currencyArg.id}"
        }

        if(savedInstanceState == null) {
            viewModel.init(CurrencyArgMapper.toModel(args.currencyArg))
        }

        binding.handler = object : Handler {
            override fun onBuyCryptoCurrency() {
                val appPackage = "co.mona.android"
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackage")))
                } catch (e: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackage")))
                }
            }

            override fun onLinkClick(links: List<String>) {
                if(links.size == 1) {
                    openLink(links[0])
                    return
                }

                val builder = AlertDialog.Builder(requireContext())
                builder.setItems(links.toTypedArray()) { _, which ->
                    openLink(links[which])
                }
                val dialog = builder.create()
                dialog.show()
            }
        }

        binding.currencyLinks.addListener { _, isExpanded ->
            if(isExpanded) binding.scrollView.smoothScrollTo(
                binding.currencyLinksContainer.x.toInt(),
                binding.currencyLinksContainer.y.toInt(),
                1000
            )
        }
    }

    private fun openLink(link: String) {
        val action = CurrencyDetailFragmentDirections.actionCurrencyDetailFragmentToWebViewFragment(link)
        findNavController().navigate(action)
    }

    interface Handler {
        fun onBuyCryptoCurrency()
        fun onLinkClick(links: List<String>)
    }
}