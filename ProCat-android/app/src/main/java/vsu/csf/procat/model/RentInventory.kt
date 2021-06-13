package vsu.csf.procat.model

import java.math.BigDecimal

data class RentInventory(
    val uuid: String,
    val name: String,
    val typeName: String,
    val pathToImage: String,
    val pricePerHour: BigDecimal,
    val availabilityStatus: String,
)
