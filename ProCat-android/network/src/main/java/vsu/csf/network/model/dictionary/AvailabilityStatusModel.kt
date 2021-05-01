package vsu.csf.network.model.dictionary

import com.google.gson.annotations.SerializedName

data class AvailabilityStatusModel(
    @SerializedName("status_id")
    val id: Long,
    @SerializedName("status_name")
    val name: String,
)
