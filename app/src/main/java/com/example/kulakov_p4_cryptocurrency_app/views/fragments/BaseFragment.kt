package com.example.kulakov_p4_cryptocurrency_app.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.kulakov_p4_cryptocurrency_app.utils.InternetUtil

abstract class BaseFragment<TBinding: ViewDataBinding>(
    @LayoutRes private val layout: Int
): Fragment(layout) {

    protected lateinit var binding: TBinding

    protected lateinit var internetObserver: InternetUtil

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
}