package vsu.csf.procat.repo

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import vsu.csf.procat.model.CurrentRentInventoryDto
import vsu.csf.procat.model.RentPauseDto

interface RentRepo {

    fun startRent(itemUuid: String): Completable

    fun pauseRent(itemUuid: String): Single<RentPauseDto>

    fun payForRent(rentId: Long): Single<Boolean>

    fun getCurrentRentItems(): Single<List<CurrentRentInventoryDto>>

}