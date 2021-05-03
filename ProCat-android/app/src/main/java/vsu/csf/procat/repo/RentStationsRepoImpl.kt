package vsu.csf.procat.repo

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import vsu.csf.network.api.RentStationApi
import vsu.csf.network.model.InventoryModel
import vsu.csf.procat.model.RentInventory
import vsu.csf.procat.model.RentStation
import vsu.csf.procat.model.toDto
import java.math.BigDecimal
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
        // return rentStationApi.getStationInventory(rentStationId)
        Single.just(
            listOf(
                InventoryModel(
                    id = 1L,
                    "Giant P5000",
                    1L,
                    "https://hightech.fm/wp-content/uploads/2020/07/SuperStrata_Studio_White_SideView_2370.0-1316x877.jpg",
                    BigDecimal.valueOf(10000),
                    1L,
                    2L,
                ),
                InventoryModel(
                    id = 2L,
                    "Giant S300",
                    1L,
                    "https://contents.mediadecathlon.com/p1849701/k2683e85d6811d43d2fe50afef22cb8d6/1849701_default.jpg?format=auto&quality=60&f=800x0",
                    BigDecimal.valueOf(5000),
                    1L,
                    2L,
                ),
                InventoryModel(
                    id = 3L,
                    "Burton YEASAYER FW19",
                    3L,
                    "https://www.sibalt.ru/images/shoria/info/kak_vibrat_snoubord_02.jpg",
                    BigDecimal.valueOf(20000),
                    1L,
                    2L,
                ),
            )
        )
            .map { inventoryList ->
                inventoryList.map {
                    RentInventory(
                        id = it.id,
                        name = it.name,
                        typeName = dictionaryRepo.getInventoryTypeById(it.typeId).blockingGet(),
                        pathToImage = it.imageSrc,
                        pricePerHour = it.pricePerHour,
                        availabilityStatus =
                        dictionaryRepo.getAvailabilityStatus(it.availabilityStatusId).blockingGet(),
                    )
                }
            }.subscribeOn(Schedulers.io())
}