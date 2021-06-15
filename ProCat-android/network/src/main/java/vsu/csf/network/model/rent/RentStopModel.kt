package vsu.csf.network.model.rent

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class RentStopModel(
    @SerializedName("amountToPay")
    val amountToPay: BigDecimal,
    @SerializedName("rentId")
    val rentId: Long,
)
