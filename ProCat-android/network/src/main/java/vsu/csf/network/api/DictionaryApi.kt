package vsu.csf.network.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import vsu.csf.network.model.dictionary.AvailabilityStatusModel
import vsu.csf.network.model.dictionary.InventoryTypeModel
import vsu.csf.network.model.dictionary.RentStatusModel

interface DictionaryApi {

    @GET("/dictionary/type/inventory")
    fun getInventoryTypes(): Single<List<InventoryTypeModel>>

    @GET("/dictionary/status/availability")
    fun getAvailabilityStatuses(): Single<List<AvailabilityStatusModel>>

    @GET("/dictionary/status/rent")
    fun getRentStatuses(): Single<List<RentStatusModel>>

}