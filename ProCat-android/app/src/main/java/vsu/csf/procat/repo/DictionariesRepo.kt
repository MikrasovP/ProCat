package vsu.csf.procat.repo

import io.reactivex.rxjava3.core.Completable

interface DictionariesRepo {

    fun updateAllDictionaries(): Completable

}