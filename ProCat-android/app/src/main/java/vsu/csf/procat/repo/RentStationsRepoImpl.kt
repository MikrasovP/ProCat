package vsu.csf.procat.repo

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import vsu.csf.network.api.RentStationApi
import vsu.csf.procat.model.RentStation
import vsu.csf.procat.model.toDto
import javax.inject.Inject

class RentStationsRepoImpl @Inject constructor(
    private val rentStationApi: RentStationApi,
    private val dictionaryRepo: DictionariesRepo,
) : RentStationsRepo {

    override fun getRentStationsList(): Single<List<RentStation>> {
        return dictionaryRepo.updateAllDictionaries()
            .to {
                rentStationApi.getRentStationsList()
                    .map { modelList ->
                        return@map modelList.map { it.toDto() }
                    }
            }
            .subscribeOn(Schedulers.io())
    }

}