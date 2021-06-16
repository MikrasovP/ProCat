package vsu.csf.procat.model

import java.math.BigDecimal

data class CurrentRentInventoryDto(
    val payAmount: BigDecimal,
    val rentInventory: RentInventory,
)
