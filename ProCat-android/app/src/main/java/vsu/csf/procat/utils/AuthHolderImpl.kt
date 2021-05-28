package vsu.csf.procat.utils

import io.reactivex.rxjava3.core.Completable
import timber.log.Timber
import vsu.csf.network.AuthHolder
import vsu.csf.network.model.auth.UserAuthModel
import vsu.csf.procat.utils.prefs.SharedPrefsRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthHolderImpl @Inject constructor(
    private val sharedPrefsRepo: SharedPrefsRepo,
) : AuthHolder {

    override var authToken: String = ""

    var username: String = ""
    var userPhoneNumber: String = ""
    var userEmail: String = ""

    init {
        updateUserData()
            .subscribe({ }, { ex -> Timber.e(ex, "Error while reading user data") })
    }

    private fun updateUserData(): Completable {
        val tokenCompletable = sharedPrefsRepo.getAuthToken()
            .doOnSuccess { authToken = it }
            .doOnComplete { authToken = "" }
            .doOnError { Timber.e(it, "Error while reading saved token from prefs") }
            .ignoreElement()
        val usernameCompletable = sharedPrefsRepo.getUsername()
            .doOnSuccess { username = it }
            .doOnComplete { username = "" }
            .doOnError { Timber.e(it, "Error while reading saved username from prefs") }
            .ignoreElement()
        val userMobilePhoneCompletable = sharedPrefsRepo.getUserMobilePhone()
            .doOnSuccess { userPhoneNumber = it }
            .doOnComplete { userPhoneNumber = "" }
            .doOnError { Timber.e(it, "Error while reading saved user phone from prefs") }
            .ignoreElement()
        val userEmailCompletable = sharedPrefsRepo.getUserEmail()
            .doOnSuccess { userEmail = it }
            .doOnComplete { userEmail = "" }
            .doOnError { Timber.e(it, "Error while reading saved user email from prefs") }
            .ignoreElement()

        return Completable.concatArray(
            tokenCompletable,
            usernameCompletable,
            userMobilePhoneCompletable,
            userEmailCompletable,
        )
    }

    fun logout(): Completable {
        authToken = ""
        return sharedPrefsRepo.clearAuthToken()
            .andThen(updateUserData())
    }

    fun login(userModel: UserAuthModel): Completable {
        return with(sharedPrefsRepo) {
            Completable.concatArray(
                saveAuthToken(userModel.authToken),
                saveUsername(userModel.name),
                saveUserEmail(userModel.email),
                saveUserMobilePhone(userModel.phoneNumber),
                updateUserData(),
            )
        }
    }

}