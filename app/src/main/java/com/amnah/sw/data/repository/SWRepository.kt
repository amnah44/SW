package com.amnah.sw.data.repository

import com.amnah.sw.data.Status
import com.amnah.sw.data.domain.WorkoutResponse
import com.amnah.sw.data.network.ApiClient
import com.amnah.sw.data.network.SearchClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient


class WorkoutRepository(private val client: OkHttpClient) {

    suspend fun getAllWorkoutData(): Flow<Status<WorkoutResponse>> {
        return flow {
            emit(Status.OnLoading)
            emit(ApiClient(client = client).getWorkoutData())
        }
    }

    suspend fun getWorkoutSearch(value: String): Flow<Status<WorkoutResponse>>{
        return flow {
            emit(Status.OnLoading)
            emit(SearchClient(client = client, value).getSearchData())
        }
    }

}