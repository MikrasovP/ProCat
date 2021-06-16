package vsu.csf.network.api

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import vsu.csf.network.model.rent.CurrentRentInventoryModel
import vsu.csf.network.model.rent.RentDtoModel
import vsu.csf.network.model.rent.RentPaymentModel
import vsu.csf.network.model.rent.RentStopModel

interface RentApi {

    @POST("/rent/start")
    fun startRent(@Body rentDtoModel: RentDtoModel): Completable

    @POST("/rent/stop")
    fun stopRent(@Body rentDtoModel: RentDtoModel): Single<RentStopModel>

    @POST("/rent/pay")
    fun payForRent(@Body rentPaymentModel: RentPaymentModel): Completable

    @GET("/rent/user/currentRent")
    fun getCurrentRentItems(): Single<List<CurrentRentInventoryModel>>

}