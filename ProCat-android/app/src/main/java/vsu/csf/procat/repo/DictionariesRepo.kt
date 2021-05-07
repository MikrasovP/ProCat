package vsu.csf.procat.repo

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface DictionariesRepo {

    fun updateAllDictionaries(): Completable

    fun getInventoryTypeById(typeId: Long): Single<String>

    fun getAvailabilityStatus(statusId: Long): Single<String>

    fun getAvailabilityStatusesList(): Single<List<String>>

    fun getRentStatus(statusId: Long): Single<String>

    fun getRentStatusesList(): Single<List<String>>

}