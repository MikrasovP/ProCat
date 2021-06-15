package vsu.csf.network.model.rent

import com.google.gson.annotations.SerializedName

data class RentPaymentModel(
    @SerializedName("rentId")
    val rentId: Long,
)
