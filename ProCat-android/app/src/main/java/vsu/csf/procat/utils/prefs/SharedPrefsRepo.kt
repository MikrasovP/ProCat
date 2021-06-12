package vsu.csf.procat.utils.prefs

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

interface SharedPrefsRepo {

    fun saveUsername(username: String): Completable

    fun getUsername(): Maybe<String>

    fun saveAuthToken(authToken: String): Completable

    fun getAuthToken(): Maybe<String>

    fun clearAuthToken(): Completable

    fun saveUserEmail(email: String): Completable

    fun getUserEmail(): Maybe<String>

    fun saveUserMobilePhone(phone: String): Completable

    fun getUserMobilePhone(): Maybe<String>

}