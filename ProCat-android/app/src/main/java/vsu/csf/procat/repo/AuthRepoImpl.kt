package vsu.csf.procat.repo

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import vsu.csf.network.api.AuthApi
import vsu.csf.network.model.auth.UserAuthModel
import vsu.csf.procat.utils.AuthHolderImpl
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val authHolder: AuthHolderImpl,
    private val authApi: AuthApi,
) : AuthRepo {

    override fun checkIsRegistered(phoneNumber: String): Single<Boolean> {
        /*return authApi.checkIsRegistered(phoneNumber)
            .subscribeOn(Schedulers.io())*/
        return Single.just(false)
            .delay(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
    }

    override fun submitLogin(phoneNumber: String): Completable {
        /*return authApi.login(UserLoginModel(phoneNumber))
            .flatMapCompletable {
                saveUserAuthModel(it)
                Completable.complete()
            }
            .subscribeOn(Schedulers.io())*/
        return Completable.complete()
            .delay(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
    }

    override fun submitRegistration(
        phoneNumber: String,
        name: String,
        email: String,
    ): Completable {
        /*return authApi.register(UserRegistrationModel(phoneNumber, name, email))
            .flatMapCompletable {
                saveUserAuthModel(it)
                Completable.complete()
            }
            .subscribeOn(Schedulers.io())*/
        return Completable.complete()
            .delay(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
    }

    override fun logout(): Completable = Completable.fromAction {
        authHolder.logout()
    }.subscribeOn(Schedulers.io())

    private fun saveUserAuthModel(userModel: UserAuthModel) {
        authHolder.login(userModel)
    }

}