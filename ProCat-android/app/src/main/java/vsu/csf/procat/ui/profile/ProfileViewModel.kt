package vsu.csf.procat.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import vsu.csf.procat.utils.AuthHolderImpl
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authHolderImpl: AuthHolderImpl,
) : ViewModel() {

    val isAuthorized = MutableLiveData(false)

    fun updateAuthStatus() {
        isAuthorized.value = authHolderImpl.authToken.isNotBlank()
    }

}