package vsu.csf.procat.repo

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import vsu.csf.network.model.rent.RentStopModel

interface RentRepo {

    fun startRent(itemUuid: String): Completable

    fun pauseRent(itemUuid: String): Single<RentStopModel>

    fun payForRent(rentId: Long): Single<Boolean>

}