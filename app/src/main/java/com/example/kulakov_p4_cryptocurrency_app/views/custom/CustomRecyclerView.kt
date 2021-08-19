package com.example.kulakov_p4_cryptocurrency_app.views.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.kulakov_p4_cryptocurrency_app.databinding.LayoutRecyclerviewBinding
import com.example.kulakov_p4_cryptocurrency_app.view_models.RecyclerViewVM

class CustomRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutRecyclerviewBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = LayoutRecyclerviewBinding.inflate(inflater, this, true)
    }

    var viewModel: RecyclerViewVM = RecyclerViewVM()
        set(value) {
            field = value
            binding.viewModel = field
        }

    var retryHandler: () -> Unit = {}
        set(value) {
            field = value
            binding.retryHandler = field
        }

    var adapter: RecyclerView.Adapter<*>? = null
        set(value) {
            field = value
            binding.recyclerView.adapter = field
        }

    fun prepareToSharedTransition(fragment: Fragment) {
        binding.recyclerView.apply {
            fragment.postponeEnterTransition()
            viewTreeObserver
                .addOnPreDrawListener {
                    fragment.startPostponedEnterTransition()
                    true
                }
        }
    }

    fun scrollToTop() {
        binding.recyclerView.scrollToPosition(0)
    }
}