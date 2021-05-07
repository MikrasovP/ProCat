package vsu.csf.network.model.dictionary

import com.google.gson.annotations.SerializedName

data class AvailabilityStatusModel(
    @SerializedName("statusId")
    val id: Long,
    @SerializedName("statusName")
    val name: String,
)
