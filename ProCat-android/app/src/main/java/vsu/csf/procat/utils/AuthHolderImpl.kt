package vsu.csf.procat.utils

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import timber.log.Timber
import vsu.csf.network.AuthHolder
import vsu.csf.network.model.auth.UserAuthModel
import vsu.csf.procat.utils.prefs.SharedPrefsRepo
import javax.inject.Inject

class AuthHolderImpl @Inject constructor(
    private val sharedPrefsRepo: SharedPrefsRepo,
) : AuthHolder {

    override var authToken: String = ""

    var username: String = ""
    var userPhoneNumber: String = ""
    var userEmail: String = ""

    init {
        getUserData()
    }

    private fun getUserData() {
        val tokenCompletable = sharedPrefsRepo.getAuthToken()
            .doOnSuccess { authToken = it }
            .doOnComplete { authToken = "" }
            .doOnError { Timber.e(it, "Error while reading saved token from prefs") }
        val usernameCompletable = sharedPrefsRepo.getUsername()
            .doOnSuccess { username = it }
            .doOnComplete { username = "" }
            .doOnError { Timber.e(it, "Error while reading saved username from prefs") }
        val userMobilePhoneCompletable = sharedPrefsRepo.getUserMobilePhone()
            .doOnSuccess { userPhoneNumber = it }
            .doOnComplete { userPhoneNumber = "" }
            .doOnError { Timber.e(it, "Error while reading saved user phone from prefs") }
        val userEmailCompletable = sharedPrefsRepo.getUserEmail()
            .doOnSuccess { userEmail = it }
            .doOnComplete { userEmail = "" }
            .doOnError { Timber.e(it, "Error while reading saved user email from prefs") }

        Maybe.mergeArray(
            tokenCompletable,
            usernameCompletable,
            userMobilePhoneCompletable,
            userEmailCompletable
        ).subscribe({}, Timber::e)
    }

    fun logout(): Completable {
        authToken = ""
        return sharedPrefsRepo.clearAuthToken()
    }

    fun login(userModel: UserAuthModel): Completable {
        authToken = userModel.authToken
        return with(sharedPrefsRepo) {
            saveAuthToken(userModel.authToken)
                .andThen(saveUsername(userModel.name))
                .andThen(saveUserEmail(userModel.email))
                .andThen(saveUserMobilePhone(userModel.phoneNumber))
        }
    }

}