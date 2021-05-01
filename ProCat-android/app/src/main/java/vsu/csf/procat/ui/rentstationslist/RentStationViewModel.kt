package vsu.csf.procat.ui.rentstationslist

import vsu.csf.procat.model.RentStation

class RentStationViewModel(
    val rentStation: RentStation,
) {
    val name = rentStation.name
    val address = rentStation.address
}