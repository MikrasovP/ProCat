package vsu.csf.procat.model

import vsu.csf.network.model.RentStationModel
import vsu.csf.network.model.dictionary.AvailabilityStatusModel
import vsu.csf.network.model.dictionary.InventoryTypeModel
import vsu.csf.network.model.dictionary.RentStatusModel
import vsu.csf.network.model.rent.RentStopModel
import vsu.csf.procat.database.entity.AvailabilityStatus
import vsu.csf.procat.database.entity.InventoryType
import vsu.csf.procat.database.entity.RentStatus

fun AvailabilityStatusModel.toEntity() = AvailabilityStatus(
    id = id,
    name = name,
)

fun InventoryTypeModel.toEntity() = InventoryType(
    id = id,
    name = name,
)

fun RentStatusModel.toEntity() = RentStatus(
    id = id,
    name = name,
)

fun RentStationModel.toDto() = RentStation(
    id = id,
    name = name,
    address = address,
    latitude = latitude,
    longitude = longitude,
)

fun RentStopModel.toDto() = RentPauseDto(
    amountToPay,
    rentId,
)
