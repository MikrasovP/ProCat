package vsu.csf.network.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import vsu.csf.network.model.InventoryModel
import vsu.csf.network.model.RentStationModel

interface RentStationApi {

    @GET("/station/list")
    fun getRentStationsList(): Single<List<RentStationModel>>

    @GET("/station/{id}/inventory")
    fun getStationInventory(@Path("id") stationId: Long): Single<List<InventoryModel>>

}