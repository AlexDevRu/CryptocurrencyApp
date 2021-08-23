package com.example.kulakov_p4_cryptocurrency_app.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.kulakov_p4_cryptocurrency_app.databinding.LayoutToolbarBinding
import com.example.kulakov_p4_cryptocurrency_app.utils.InternetUtil
import com.example.kulakov_p4_cryptocurrency_app.view_models.MainActivityVM
import com.example.kulakov_p4_cryptocurrency_app.views.MainActivity
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<TBinding: ViewDataBinding>(
    @LayoutRes private val layout: Int
): Fragment(layout) {

    protected lateinit var binding: TBinding

    protected lateinit var internetObserver: InternetUtil

    protected val mainActivityVM: MainActivityVM
        get() = (requireActivity() as MainActivity).mainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        internetObserver = InternetUtil(requireContext())
        return binding.root
    }

    protected fun showSnackBar(@StringRes messageRes: Int) {
        Snackbar.make(binding.root, resources.getString(messageRes), Snackbar.LENGTH_SHORT).show()
    }

    protected fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    protected fun initToolbar(toolbarBinding: LayoutToolbarBinding, @StringRes titleRes: Int) {
        toolbarBinding.toolbarTitle.text = resources.getString(titleRes)
        toolbarBinding.menuButton.setOnClickListener {
            mainActivityVM.openDrawer()
        }
        toolbarBinding.searchView.setOnQueryTextFocusChangeListener { _, b ->
            val visibility =  if(b) View.GONE else View.VISIBLE
            toolbarBinding.menuButton.visibility = visibility
            toolbarBinding.toolbarTitle.visibility = visibility
        }
    }
}