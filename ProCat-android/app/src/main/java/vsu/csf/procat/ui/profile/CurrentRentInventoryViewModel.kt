package vsu.csf.procat.ui.profile

import vsu.csf.procat.model.CurrentRentInventoryDto

class CurrentRentInventoryViewModel(
    val currentRent: CurrentRentInventoryDto,
) {
    val name = currentRent.rentInventory.name
    val typeName = currentRent.rentInventory.typeName
    val payAmount = currentRent.payAmount.toString() + " ₽"
}