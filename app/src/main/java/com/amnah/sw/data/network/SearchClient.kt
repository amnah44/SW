package com.amnah.sw.data.network

import com.amnah.sw.data.Status
import com.amnah.sw.data.domain.WorkoutResponse
import com.amnah.sw.utils.Constants
import com.google.gson.Gson
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class SearchClient(private val client: OkHttpClient, private val value: String) {

    fun getSearchData(): Status<WorkoutResponse> {
        val request = getRequest()
        val response = makeCall(request)
        return checkResponseState(response)
    }

    private fun getRequest() =
        Request.Builder().url(getSearchUrl(value))
            .addHeader(Constants().x_rapid_api_key, Constants().key)
            .addHeader(Constants().x_rapid_api_host, Constants().host)
            .build()


    private fun makeCall(request: Request) = client.newCall(request).execute()


    private fun checkResponseState(response: Response) = if (response.isSuccessful) {
        val result = Gson().fromJson(response.body?.string(), WorkoutResponse::class.java)
        Status.OnSuccess(result)
    } else {
        Status.OnError(response.message)
    }

    private fun getSearchUrl(value: String) =
        HttpUrl.Builder()
            .scheme(Constants().schema)
            .host(Constants().host)
            .addPathSegment(Constants().segment1)
            .addPathSegment(Constants().segment2)
            .addPathSegment(value)
            .build()

}