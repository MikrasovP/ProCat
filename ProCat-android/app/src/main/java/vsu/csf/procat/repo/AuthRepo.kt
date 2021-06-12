package vsu.csf.procat.repo

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface AuthRepo {

    fun checkIsRegistered(phoneNumber: String): Single<Boolean>

    fun submitLogin(phoneNumber: String): Completable

    fun submitRegistration(
        phoneNumber: String,
        name: String,
        email: String,
    ): Completable

    fun logout(): Completable

}