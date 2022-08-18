package com.amnah.sw.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.amnah.sw.data.Status
import com.amnah.sw.data.domain.WorkoutResponse
import com.amnah.sw.data.repository.WorkoutRepository
import com.amnah.sw.databinding.FragmentWorkoutBinding
import com.amnah.sw.ui.adapter.WorkoutAdapter
import com.amnah.sw.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class WorkoutFragment : BaseFragment<FragmentWorkoutBinding>() {

    override val LOG_TAG: String = Constants().tagWorkoutFragment
    private lateinit var _binding: FragmentWorkoutBinding
    private val client = OkHttpClient()
    private lateinit var workoutSWRepository: WorkoutRepository

    override val bindingInflater: (LayoutInflater) -> FragmentWorkoutBinding =
        FragmentWorkoutBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWorkoutBinding.bind(view)

        workoutSWRepository = WorkoutRepository(client)

        makeWorkoutRequest()

    }

    private fun setupRecycler(responseItem: WorkoutResponse) {
        _binding.workoutRecycler.adapter = WorkoutAdapter(responseItem)
    }

    private fun makeWorkoutRequest() {

        lifecycleScope.launch() {
            val workoutFlow = workoutSWRepository.getAllWorkoutData().flowOn(Dispatchers.Default)

            workoutFlow.collect { response ->
                when (response) {
                    is Status.OnError -> onErrorLayout()

                    Status.OnLoading -> onLoadingLayout()

                    is Status.OnSuccess -> onSuccessLayout(response)
                }
            }
        }
    }

    private fun onErrorLayout() {
        _binding.apply {
            errorLottie.visibility = View.VISIBLE
            loadingLottie.visibility = View.GONE
            workoutRecycler.visibility = View.GONE
        }

    }

    private fun onLoadingLayout() {
        _binding.apply {
            loadingLottie.visibility = View.VISIBLE
            errorLottie.visibility = View.GONE
            workoutRecycler.visibility = View.GONE
        }
    }

    private fun onSuccessLayout(response: Status.OnSuccess<WorkoutResponse>) {
        _binding.apply {
            errorLottie.visibility = View.GONE
            loadingLottie.visibility = View.GONE
            workoutRecycler.visibility = View.VISIBLE
        }

        response.data?.let { setupRecycler(it) }
    }

}