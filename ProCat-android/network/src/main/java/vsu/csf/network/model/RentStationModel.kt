package vsu.csf.network.model

import com.google.gson.annotations.SerializedName

data class RentStationModel(
    @SerializedName("stationId")
    val id: Long,
    @SerializedName("stationName")
    val name: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
)
