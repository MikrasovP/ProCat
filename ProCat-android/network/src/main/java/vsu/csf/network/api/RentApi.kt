package vsu.csf.network.api

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.POST
import vsu.csf.network.model.rent.RentDtoModel
import vsu.csf.network.model.rent.RentPaymentModel
import vsu.csf.network.model.rent.RentStopModel

interface RentApi {

    @POST("/rent/start")
    fun startRent(rentDtoModel: RentDtoModel): Completable

    @POST("/rent/stop")
    fun stopRent(rentDtoModel: RentDtoModel): Single<RentStopModel>

    @POST("/rent/pay")
    fun payForRent(rentPaymentModel: RentPaymentModel): Completable

}