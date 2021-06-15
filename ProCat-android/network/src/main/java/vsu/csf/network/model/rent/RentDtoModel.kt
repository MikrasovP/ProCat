package vsu.csf.network.model.rent

import com.google.gson.annotations.SerializedName

data class RentDtoModel(
    @SerializedName("inventoryId")
    val inventoryUuid: String,
)
