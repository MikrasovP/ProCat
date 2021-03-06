package vsu.csf.procat.repo

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import vsu.csf.network.api.AuthApi
import vsu.csf.network.model.auth.UserAuthModel
import vsu.csf.network.model.auth.UserLoginModel
import vsu.csf.network.model.auth.UserRegistrationModel
import vsu.csf.procat.utils.AuthHolderImpl
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val authHolder: AuthHolderImpl,
    private val authApi: AuthApi,
) : AuthRepo {

    override fun checkIsRegistered(phoneNumber: String): Single<Boolean> {
        return authApi.checkIsRegistered(phoneNumber)
            .subscribeOn(Schedulers.io())
    }

    override fun submitLogin(phoneNumber: String): Completable {
        return authApi.login(UserLoginModel(phoneNumber))
            .flatMapCompletable {
                return@flatMapCompletable saveUserAuthModel(it)
            }
            .subscribeOn(Schedulers.io())
    }

    override fun submitRegistration(
        phoneNumber: String,
        name: String,
        email: String,
    ): Completable {
        return authApi.register(UserRegistrationModel(phoneNumber, name, email))
            .flatMapCompletable {
                return@flatMapCompletable saveUserAuthModel(it)
            }
            .subscribeOn(Schedulers.io())
    }

    override fun logout(): Completable =
        authHolder.logout()
            .subscribeOn(Schedulers.io())

    private fun saveUserAuthModel(userModel: UserAuthModel) =
        authHolder.login(userModel)
            .subscribeOn(Schedulers.io())

}