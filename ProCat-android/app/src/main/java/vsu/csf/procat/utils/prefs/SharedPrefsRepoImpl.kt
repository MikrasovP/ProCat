package vsu.csf.procat.utils.prefs

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SharedPrefsRepoImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
) : SharedPrefsRepo {

    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)

    override fun saveUsername(username: String): Completable =
        saveStringValueRx(USERNAME_KEY, username)

    override fun getUsername(): Maybe<String> =
        getStringValueRx(USERNAME_KEY)

    override fun saveAuthToken(authToken: String): Completable =
        saveStringValueRx(AUTH_TOKEN_KEY, authToken)

    override fun getAuthToken(): Maybe<String> =
        getStringValueRx(AUTH_TOKEN_KEY)

    override fun clearAuthToken(): Completable =
        clearStringValueRx(AUTH_TOKEN_KEY)

    override fun saveUserEmail(email: String): Completable =
        saveStringValueRx(USER_EMAIL_KEY, email)

    override fun getUserEmail(): Maybe<String> =
        getStringValueRx(USER_EMAIL_KEY)

    override fun saveUserMobilePhone(phone: String): Completable =
        saveStringValueRx(USER_PHONE_KEY, phone)

    override fun getUserMobilePhone(): Maybe<String> =
        getStringValueRx(USER_PHONE_KEY)

    private fun getStringValueRx(key: String): Maybe<String> {
        return Single.fromCallable {
            sharedPrefs.getString(key, NULL_OBJECT)
        }
            .flatMapMaybe {
                return@flatMapMaybe if (it == NULL_OBJECT || it == null)
                    Maybe.empty()
                else
                    Maybe.just(it)
            }
            .subscribeOn(Schedulers.io())
    }

    @SuppressLint("ApplySharedPref")
    private fun saveStringValueRx(key: String, value: String): Completable {
        return Completable.fromAction {
            sharedPrefs.edit().putString(key, value).commit()
        }.subscribeOn(Schedulers.io())
    }

    @SuppressLint("ApplySharedPref")
    private fun clearStringValueRx(key: String): Completable {
        return Completable.fromAction {
            sharedPrefs.edit().remove(key).commit()
        }.subscribeOn(Schedulers.io())
    }

    companion object {
        private const val PREFS_FILE = "procat_shared_prefs"
        private const val NULL_OBJECT = ""

        private const val USERNAME_KEY = "username"
        private const val USER_EMAIL_KEY = "user_email"
        private const val USER_PHONE_KEY = "user_phone_number"
        private const val AUTH_TOKEN_KEY = "user_token"
    }

}