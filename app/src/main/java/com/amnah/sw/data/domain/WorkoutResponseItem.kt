package com.amnah.sw.data.domain

import com.google.gson.annotations.SerializedName

data class WorkoutResponseItem(
    @SerializedName("bodyPart")
    var bodyPart: String?,
    @SerializedName("equipment")
    var equipment: String?,
    @SerializedName("gifUrl")
    var gifUrl: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("target")
    var target: String?
)