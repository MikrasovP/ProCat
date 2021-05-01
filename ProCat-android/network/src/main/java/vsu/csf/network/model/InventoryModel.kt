package vsu.csf.network.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class InventoryModel(
    @SerializedName("inventory_id")
    val id: Long,
    @SerializedName("inventory_name")
    val name: String,
    // TODO: добавить name, когда будет готова er-диаграмма
    val typeId: Long,
    @SerializedName("path_to_img")
    val imageSrc: String,
    @SerializedName("price_per_hour")
    val pricePerHour: BigDecimal,
    @SerializedName("station_id")
    val stationId: Long,
    // TODO: добавить name, когда будет готова er-диаграмма
    @SerializedName("availability_status")
    val availabilityStatusId: Long,

)
