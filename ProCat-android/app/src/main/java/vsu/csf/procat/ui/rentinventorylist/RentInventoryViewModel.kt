package vsu.csf.procat.ui.rentinventorylist

import vsu.csf.procat.model.RentInventory
import java.math.BigDecimal

class RentInventoryViewModel(
    val rentInventory: RentInventory,
) {
    val name = rentInventory.name
    val typeName = rentInventory.typeName
    val pricePerHourString = getPricePerHour(rentInventory.pricePerHour)
    val availabilityStatus = rentInventory.availabilityStatus

    private fun getPricePerHour(price: BigDecimal) =
        (price.intValueExact() * 100).toString() + " в час"
}