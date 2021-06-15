package vsu.csf.procat.repo


import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import vsu.csf.network.api.DictionaryApi
import vsu.csf.network.model.InventoryModel
import vsu.csf.procat.database.dao.AvailabilityStatusDao
import vsu.csf.procat.database.dao.InventoryTypeDao
import vsu.csf.procat.database.dao.RentStatusDao
import vsu.csf.procat.model.RentInventory
import vsu.csf.procat.model.toEntity
import javax.inject.Inject

class DictionariesRepoImpl @Inject constructor(
    private val dictionaryApi: DictionaryApi,
    private val availabilityStatusDao: AvailabilityStatusDao,
    private val inventoryTypeDao: InventoryTypeDao,
    private val rentStatusDao: RentStatusDao,
) : DictionariesRepo {

    companion object {
        private const val INVENTORY_TYPE_PLACEHOLDER = "Неизвестный тип"
        private const val AVAILABILITY_STATUS_PLACEHOLDER = "Неизвестный статус"
        private const val RENT_STATUS_PLACEHOLDER = "Неизвестный статус"
    }

    override fun updateAllDictionaries(): Completable =
        Completable.mergeArray(
            updateAvailabilityStatuses(),
            updateInventoryTypes(),
            updateRentStatuses(),
        ).subscribeOn(Schedulers.io())

    override fun getInventoryTypeById(typeId: Long): Single<String> =
        inventoryTypeDao.getNameById(typeId)
            .defaultIfEmpty(INVENTORY_TYPE_PLACEHOLDER)
            .subscribeOn(Schedulers.io())

    override fun getAvailabilityStatus(statusId: Long): Single<String> =
        availabilityStatusDao.getNameById(statusId)
            .defaultIfEmpty(AVAILABILITY_STATUS_PLACEHOLDER)
            .subscribeOn(Schedulers.io())

    override fun getAvailabilityStatusesList(): Single<List<String>> =
        availabilityStatusDao.getAvailabilityStatusesNames()
            .subscribeOn(Schedulers.io())

    override fun getRentStatus(statusId: Long): Single<String> =
        rentStatusDao.getNameById(statusId)
            .defaultIfEmpty(RENT_STATUS_PLACEHOLDER)
            .subscribeOn(Schedulers.io())

    override fun getRentStatusesList(): Single<List<String>> =
        rentStatusDao.getRentStatusesNames()
            .subscribeOn(Schedulers.io())

    override fun resolveRentInventory(inventoryModel: InventoryModel): Single<RentInventory> =
        Single.fromCallable {
            val typeName = getInventoryTypeById(inventoryModel.typeId)
                .blockingGet()
            val availabilityStatus = getAvailabilityStatus(inventoryModel.availabilityStatusId)
                .blockingGet()
            RentInventory(
                uuid = inventoryModel.uuid,
                name = inventoryModel.name,
                typeName = typeName,
                pathToImage = inventoryModel.imageSrc,
                pricePerHour = inventoryModel.pricePerHour,
                availabilityStatus = availabilityStatus,
            )
        }.subscribeOn(Schedulers.io())

    private fun updateAvailabilityStatuses(): Completable =
        dictionaryApi.getAvailabilityStatuses()
            .concatMapCompletable { list ->
                availabilityStatusDao.saveList(list.map { it.toEntity() })
            }

    private fun updateInventoryTypes(): Completable =
        dictionaryApi.getInventoryTypes()
            .concatMapCompletable { list ->
                inventoryTypeDao.saveList(list.map { it.toEntity() })
            }

    private fun updateRentStatuses(): Completable =
        dictionaryApi.getRentStatuses()
            .concatMapCompletable { list ->
                rentStatusDao.saveList(list.map { it.toEntity() })
            }
}