package vsu.csf.procat.repo

import io.reactivex.rxjava3.core.Single
import vsu.csf.procat.model.RentStation
import javax.inject.Inject

class RentStationsRepoImpl @Inject constructor(

) : RentStationsRepo {

    override fun getRentStationsList(): Single<List<RentStation>> {
        TODO("Not yet implemented")
    }

}