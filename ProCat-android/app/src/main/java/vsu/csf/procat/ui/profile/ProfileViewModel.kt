package vsu.csf.procat.ui.profile

import android.telephony.PhoneNumberUtils
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber
import vsu.csf.procat.utils.AuthHolderImpl
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authHolderImpl: AuthHolderImpl,
) : ViewModel() {

    val isAuthorized = MutableLiveData(false)

    val userName = MediatorLiveData<String>().apply {
        addSource(isAuthorized) { value = authHolderImpl.username }
    }
    val userMobilePhone = MediatorLiveData<String>().apply {
        addSource(isAuthorized) { value = getFormattedPhoneNumber(authHolderImpl.userPhoneNumber) }
    }
    val userEmail = MediatorLiveData<String>().apply {
        addSource(isAuthorized) { value = authHolderImpl.userEmail }
    }

    fun updateAuthStatus() {
        isAuthorized.value = authHolderImpl.authToken.isNotBlank()
    }

    fun logout() {
        authHolderImpl.logout()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                updateAuthStatus()
            }, { ex ->
                Timber.e(ex, "An error occurred while logging out")
            })
    }

    private fun getFormattedPhoneNumber(unformattedPhoneNumber: String): String =
        "+7 " + PhoneNumberUtils.formatNumber(unformattedPhoneNumber, Locale.US.country)

}