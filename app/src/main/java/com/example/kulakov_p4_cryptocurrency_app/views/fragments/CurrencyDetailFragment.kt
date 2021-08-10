package com.example.kulakov_p4_cryptocurrency_app.views.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Selection
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.text.style.ClickableSpan
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
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

        /*binding.description.movementMethod = object : MovementMethod {
            override fun initialize(widget: TextView, text: Spannable) {}
            override fun onKeyDown(
                widget: TextView?,
                text: Spannable?,
                keyCode: Int,
                event: KeyEvent?
            ): Boolean {
                return false
            }

            override fun onKeyUp(
                widget: TextView?,
                text: Spannable?,
                keyCode: Int,
                event: KeyEvent?
            ): Boolean {
                return false
            }

            override fun onKeyOther(view: TextView?, text: Spannable?, event: KeyEvent?): Boolean {
                return false
            }

            override fun onTakeFocus(widget: TextView, text: Spannable, direction: Int) {}
            override fun onTrackballEvent(
                widget: TextView,
                text: Spannable,
                event: MotionEvent
            ): Boolean {
                return false
            }

            /**
             * Borrowed code for detecting and selecting link from
             * [LinkMovementMethod.onTouchEvent]
             */
            override fun onTouchEvent(
                widget: TextView,
                buffer: Spannable,
                event: MotionEvent
            ): Boolean {
                val action = event.action
                if (action == MotionEvent.ACTION_UP ||
                    action == MotionEvent.ACTION_DOWN
                ) {
                    var x = event.x
                    var y = event.y.toInt()
                    x -= widget.totalPaddingLeft
                    y -= widget.totalPaddingTop
                    x += widget.scrollX
                    y += widget.scrollY
                    val layout = widget.layout
                    val line = layout.getLineForVertical(y)
                    val off = layout.getOffsetForHorizontal(line, x)
                    val link = buffer.getSpans(
                        off, off,
                        ClickableSpan::class.java
                    )
                    if (link.size != 0) {
                        if (action == MotionEvent.ACTION_UP) {
                            // do whatever else you want here on link being clicked
                            link.forEach {
                                it.onClick()
                                val linkAction = CurrencyDetailFragmentDirections.actionCurrencyDetailFragmentToWebViewFragment(link.first().toString())
                                findNavController().navigate(linkAction)
                            }

                            Selection.removeSelection(buffer)
                        } else if (action == MotionEvent.ACTION_DOWN) {
                            Selection.setSelection(
                                buffer,
                                buffer.getSpanStart(link[0]),
                                buffer.getSpanEnd(link[0])
                            )
                        }
                        return true
                    } else {
                        Selection.removeSelection(buffer)
                    }
                }
                return false
            }

            override fun onGenericMotionEvent(
                widget: TextView,
                text: Spannable,
                event: MotionEvent
            ): Boolean {
                return false
            }

            override fun canSelectArbitrarily(): Boolean {
                return false
            }
        }*/
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