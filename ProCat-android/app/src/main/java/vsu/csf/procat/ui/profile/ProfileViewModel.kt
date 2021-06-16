package vsu.csf.procat.ui.profile

import android.telephony.PhoneNumberUtils
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber
import vsu.csf.procat.model.CurrentRentInventoryDto
import vsu.csf.procat.repo.RentRepo
import vsu.csf.procat.utils.AuthHolderImpl
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authHolderImpl: AuthHolderImpl,
    private val rentRepo: RentRepo,
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

    val currentRentInventoryList = MutableLiveData(listOf<CurrentRentInventoryDto>())
    val loading = MutableLiveData(false)
    val networkError = MutableLiveData(false)

    fun updateAuthStatus() {
        isAuthorized.value = authHolderImpl.authToken.isNotBlank()
        updateCurrentRentList()
    }

    private fun updateCurrentRentList() {
        if (isAuthorized.value == false) {
            currentRentInventoryList.value = listOf()
            return
        }
        loading.value = true
        rentRepo.getCurrentRentItems()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ items ->
                currentRentInventoryList.value = items
                loading.value = false
                networkError.value = false
            }, { ex ->
                loading.value = false
                networkError.value = true
                Timber.e(ex, "Error while retrieving current rent list")
            })
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