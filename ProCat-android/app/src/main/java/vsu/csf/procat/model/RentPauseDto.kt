package vsu.csf.procat.model

import vsu.csf.network.model.rent.RentStopModel
import java.math.BigDecimal

data class RentPauseDto(
    val amountToPay: BigDecimal,
    val rentId: Long,
) {
    companion object {
        fun fromModel(rentStopModel: RentStopModel) = RentPauseDto(
            rentStopModel.amountToPay,
            rentStopModel.rentId,
        )
    }
}
