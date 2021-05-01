package vsu.csf.procat.repo


import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import vsu.csf.network.api.DictionaryApi
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

    override fun updateAllDictionaries(): Completable =
        Completable.complete()
        /*Completable.mergeArray(
            updateAvailabilityStatuses(),
            updateInventoryTypes(),
            updateRentStatuses(),
        ).subscribeOn(Schedulers.io())*/

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