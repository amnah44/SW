package com.amnah.sw.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.amnah.sw.data.Status
import com.amnah.sw.data.domain.WorkoutResponse
import com.amnah.sw.data.repository.WorkoutRepository
import com.amnah.sw.databinding.FragmentSearchBinding
import com.amnah.sw.ui.adapter.WorkoutAdapter
import com.amnah.sw.utils.Constants
import com.amnah.sw.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private lateinit var _binding: FragmentSearchBinding
    private val client = OkHttpClient()
    private lateinit var workoutSWRepository: WorkoutRepository

    override val LOG_TAG: String = Constants().tagSearchFragment
    override val bindingInflater: (LayoutInflater) -> FragmentSearchBinding =
        FragmentSearchBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        workoutSWRepository = WorkoutRepository(client)

        _binding.searchBtn.setOnClickListener {
            getSearchData()
            _binding.searchText.setText("")
        }
    }

    private fun getSearchData() {
        val value = _binding.searchText.text

        if (value.toString() != "") {

            lifecycleScope.launch {

                val workoutFlow = workoutSWRepository.getWorkoutSearch(value.toString())
                    .flowOn(Dispatchers.Default)

                workoutFlow.collect { response ->
                    when (response) {
                        is Status.OnError -> onErrorLayout()

                        Status.OnLoading -> onLoadingLayout()

                        is Status.OnSuccess -> onSuccessLayout(response)
                    }

                }
            }
        } else {
            context?.toast(Constants().enterText)
        }

    }

    private fun setupRecycler(responseItem: WorkoutResponse) {
        _binding.searchRecycler.adapter = WorkoutAdapter(responseItem)
    }

    private fun onErrorLayout() {
        _binding.apply {
            searchLottie.visibility = View.GONE
            errorLottie.visibility = View.VISIBLE
            loadingLottie.visibility = View.GONE
            searchRecycler.visibility = View.GONE
        }

    }

    private fun onLoadingLayout() {
        _binding.apply {
            searchLottie.visibility = View.GONE
            loadingLottie.visibility = View.VISIBLE
            errorLottie.visibility = View.GONE
            searchRecycler.visibility = View.GONE
        }
    }

    private fun onSuccessLayout(response: Status.OnSuccess<WorkoutResponse>) {
        _binding.apply {
            searchLottie.visibility = View.GONE
            errorLottie.visibility = View.GONE
            loadingLottie.visibility = View.GONE
            searchRecycler.visibility = View.VISIBLE
        }

        response.data?.let { setupRecycler(it) }
    }


}