package vsu.csf.network.model.rent

import com.google.gson.annotations.SerializedName
import vsu.csf.network.model.InventoryModel
import java.math.BigDecimal

data class CurrentRentInventoryModel(
    @SerializedName("payAmount")
    val payAmount: BigDecimal,
    @SerializedName("rentInventoryDTO")
    val rentInventoryModel: InventoryModel,
)
