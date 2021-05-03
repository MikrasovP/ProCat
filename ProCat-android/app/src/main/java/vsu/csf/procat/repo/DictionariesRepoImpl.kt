package vsu.csf.procat.repo


import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import vsu.csf.network.api.DictionaryApi
import vsu.csf.network.model.dictionary.AvailabilityStatusModel
import vsu.csf.network.model.dictionary.InventoryTypeModel
import vsu.csf.network.model.dictionary.RentStatusModel
import vsu.csf.procat.database.dao.AvailabilityStatusDao
import vsu.csf.procat.database.dao.InventoryTypeDao
import vsu.csf.procat.database.dao.RentStatusDao
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

    private fun updateAvailabilityStatuses(): Completable =
        // dictionaryApi.getAvailabilityStatuses()
        Single.just(listOf(
            AvailabilityStatusModel(id = 1L, name = "Недоступно"),
            AvailabilityStatusModel(id = 2L, name = "Свободно"),
            AvailabilityStatusModel(id = 3L, name = "В ремонте"),
        ))
            .concatMapCompletable { list ->
                availabilityStatusDao.saveList(list.map { it.toEntity() })
            }

    private fun updateInventoryTypes(): Completable =
        // dictionaryApi.getInventoryTypes()
        Single.just(listOf(
            InventoryTypeModel(id = 1L, name = "Велосипед"),
            InventoryTypeModel(id = 2L, name = "Скейтборд"),
            InventoryTypeModel(id = 3L, name = "Сноуборд"),
        ))
            .concatMapCompletable { list ->
                inventoryTypeDao.saveList(list.map { it.toEntity() })
            }

    private fun updateRentStatuses(): Completable =
        // dictionaryApi.getRentStatuses()
        Single.just(listOf(
            RentStatusModel(id = 1L, name = "В аренде"),
            RentStatusModel(id = 2L, name = "Аренда на паузе"),
            RentStatusModel(id = 3L, name = "Аренда завершена"),
        ))
            .concatMapCompletable { list ->
                rentStatusDao.saveList(list.map { it.toEntity() })
            }
}