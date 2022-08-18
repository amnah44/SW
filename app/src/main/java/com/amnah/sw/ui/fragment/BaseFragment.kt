package com.amnah.sw.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.amnah.sw.utils.Constants

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    abstract val LOG_TAG: String?

    abstract val bindingInflater: (LayoutInflater) -> VB

    private var _binding: VB? = null
    private val binding: VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = bindingInflater.invoke(inflater)
        if (_binding == null) {
            throw IllegalArgumentException(Constants().baseFragmentException)
        }
        return binding.root
    }


    protected fun log(value: String?) {
        value?.let { Log.i(LOG_TAG, it) }
    }
}