package vsu.csf.procat.model

import java.math.BigDecimal

data class RentPauseDto(
    val amountToPay: BigDecimal,
    val rentId: Long,
)
