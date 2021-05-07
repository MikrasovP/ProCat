package vsu.csf.network.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class InventoryModel(
    @SerializedName("inventoryId")
    val id: Long,
    @SerializedName("inventoryName")
    val name: String,
    @SerializedName("typeId")
    val typeId: Long,
    @SerializedName("pathToImg")
    val imageSrc: String,
    @SerializedName("pricePerHour")
    val pricePerHour: BigDecimal,
    @SerializedName("stationId")
    val stationId: Long,
    @SerializedName("statusId")
    val availabilityStatusId: Long,
)
