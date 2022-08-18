package com.amnah.sw.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.amnah.sw.databinding.FragmentProfileBinding
import com.amnah.sw.utils.Constants

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private lateinit var _binding: FragmentProfileBinding

    override val LOG_TAG: String = Constants().tagProfileFragment
    override val bindingInflater: (LayoutInflater) -> FragmentProfileBinding =
        FragmentProfileBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)
    }

}