package vsu.csf.procat.repo

import io.reactivex.rxjava3.core.Single
import vsu.csf.procat.model.RentStation

interface RentStationsRepo {

    fun getRentStationsList(): Single<List<RentStation>>

}