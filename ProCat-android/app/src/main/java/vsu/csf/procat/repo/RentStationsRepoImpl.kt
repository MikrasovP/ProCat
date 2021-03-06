package vsu.csf.procat.repo

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import vsu.csf.network.api.RentStationApi
import vsu.csf.network.model.InventoryModel
import vsu.csf.procat.model.RentInventory
import vsu.csf.procat.model.RentStation
import vsu.csf.procat.model.toDto
import javax.inject.Inject

class RentStationsRepoImpl @Inject constructor(
    private val rentStationApi: RentStationApi,
    private val dictionaryRepo: DictionariesRepo,
) : RentStationsRepo {

    override fun getRentStationsList(): Single<List<RentStation>> {
        return dictionaryRepo.updateAllDictionaries()
            .andThen(
                rentStationApi.getRentStationsList()
                    .map { modelList ->
                        return@map modelList.map { it.toDto() }
                    }
            )
            .subscribeOn(Schedulers.io())
    }

    override fun getInventoryList(rentStationId: Long): Single<List<RentInventory>> =
        rentStationApi.getStationInventory(rentStationId)
            .map { inventoryList ->
                inventoryList.map {
                    dictionaryRepo.resolveRentInventory(it).blockingGet()
                }
            }.subscribeOn(Schedulers.io())

    override fun getRentInventoryByUuid(itemUuid: String): Single<InventoryModel> =
        rentStationApi.getRentInventoryByUuid(itemUuid)
            .subscribeOn(Schedulers.io())
}