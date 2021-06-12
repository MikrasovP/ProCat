package vsu.csf.procat.ui.auth

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber
import vsu.csf.procat.R
import vsu.csf.procat.repo.AuthRepo
import javax.inject.Inject
import kotlin.random.Random

/**
 * Screen states:
 *                                      LOGIN
 *                                    /
 * INPUT_NUMBER -> INPUT_CONFIRMATION -- REGISTER
 *
 * If phone number is changed, returning to state INPUT_NUMBER
 *
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepo,
) : ViewModel() {

    private enum class AuthScreenState {
        INPUT_NUMBER,
        INPUT_CONFIRMATION_CODE,
        LOGIN,
        REGISTRATION,
    }

    val authSuccess = MutableLiveData(false)
    val networkError = MutableLiveData(false)

    val phoneNumber = MutableLiveData("")
    val isPhoneNumberError = MutableLiveData(false)

    private val state = MediatorLiveData<AuthScreenState>().apply {
        value = AuthScreenState.INPUT_NUMBER
        addSource(phoneNumber) { onNumberChanged() }
    }

    val titleTextStringId = MediatorLiveData<Int>().apply {
        addSource(state) { setTitleTextDueState() }
    }

    val name = MutableLiveData("")
    val isNameError = MutableLiveData(false)

    val email = MutableLiveData("")
    val isEmailError = MutableLiveData(false)

    var properConfirmationCode = MutableLiveData<String>()
    val isConfirmationProgressBarVisible = MutableLiveData(false)
    val isConfirmationButtonVisible = MediatorLiveData<Boolean>().apply {
        value = false
        addSource(state) {
            value = (it == AuthScreenState.INPUT_CONFIRMATION_CODE
                    || it == AuthScreenState.INPUT_NUMBER)
        }
    }
    val isConfirmationTextVisible = MediatorLiveData<Boolean>().apply {
        value = false
        addSource(state) { value = (it == AuthScreenState.INPUT_CONFIRMATION_CODE) }
    }
    val confirmationCodeText = MutableLiveData("")
    val confirmationCodeError = MutableLiveData(false)

    val isAuthProgressBarVisible = MutableLiveData(false)
    val isRegistrationViewsVisible = MediatorLiveData<Boolean>().apply {
        addSource(state) { value = (it == AuthScreenState.REGISTRATION) }
    }
    val isLoginButtonVisible = MediatorLiveData<Boolean>().apply {
        addSource(state) { value = (it == AuthScreenState.LOGIN) }
    }

    fun onGetConfirmationCodeClick() {
        if (!validatePhoneNumber())
            return
        state.value = AuthScreenState.INPUT_CONFIRMATION_CODE
        generateNewConfirmationCode()

    }

    fun checkConfirmationCode() {
        if (confirmationCodeText.value != properConfirmationCode.value) {
            confirmationCodeError.value = true
        } else {
            checkWhetherRegistered()
        }
    }

    private fun checkWhetherRegistered() {
        isConfirmationProgressBarVisible.value = true
        authRepo.checkIsRegistered(phoneNumber.value!!)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { isRegistered ->
                    confirmationCodeText.value = ""
                    state.value = if (isRegistered) {
                        AuthScreenState.LOGIN
                    } else {
                        AuthScreenState.REGISTRATION
                    }
                    isConfirmationProgressBarVisible.value = false
                }, { ex ->
                    Timber.e(ex, "Error while checking whether user registered")
                    isConfirmationProgressBarVisible.value = false
                    networkError.value = true
                })
    }

    private fun onNumberChanged() {
        if (state.value != AuthScreenState.INPUT_NUMBER)
            state.value = AuthScreenState.INPUT_NUMBER
    }

    private fun validatePhoneNumber(): Boolean {
        if (phoneNumber.value?.length != PHONE_NUMBER_LENGTH) {
            isPhoneNumberError.value = true
            return false
        }
        isPhoneNumberError.value = false
        return true
    }

    private fun generateNewConfirmationCode() {
        properConfirmationCode.value =
            Random.nextInt(MIN_CONFIRMATION_CODE, MAX_CONFIRMATION_CODE).toString()
    }

    fun submitLogin() {
        isAuthProgressBarVisible.value = true
        authRepo.submitLogin(phoneNumber.value!!)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isAuthProgressBarVisible.value = false
                authSuccess.value = true
            }, { ex ->
                Timber.e(ex, "Error while logging in")
                isAuthProgressBarVisible.value = false
                networkError.value = true
            })
    }

    fun submitRegister() {
        if (!validateSubmitRegister())
            return
        isAuthProgressBarVisible.value = true
        authRepo.submitRegistration(phoneNumber.value!!, name.value!!, email.value!!)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isAuthProgressBarVisible.value = false
                authSuccess.value = true
            }, { ex ->
                Timber.e(ex, "Error while registration")
                isAuthProgressBarVisible.value = false
                networkError.value = true
            })
    }

    private fun validateSubmitRegister(): Boolean {
        if ((name.value?.length ?: 0) < MIN_NAME_LENGTH) {
            isNameError.value = true
            return false
        }
        isNameError.value = false

        if (!isEmailValid(email.value)) {
            isEmailError.value = true
            return false
        }
        isEmailError.value = false
        return true
    }

    private fun isEmailValid(email: String?): Boolean {
        return if (email.isNullOrBlank()) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }

    private fun setTitleTextDueState() {
        titleTextStringId.value = when (state.value) {
            AuthScreenState.INPUT_NUMBER -> R.string.input_phone_number_title
            AuthScreenState.INPUT_CONFIRMATION_CODE -> R.string.input_confirmation_title
            AuthScreenState.LOGIN -> R.string.login_continue
            AuthScreenState.REGISTRATION -> R.string.registration_offer_title
            else -> R.string.error
        }

    }

    companion object {
        private const val PHONE_NUMBER_LENGTH = 10
        private const val MIN_NAME_LENGTH = 3
        private const val MIN_CONFIRMATION_CODE = 1000
        private const val MAX_CONFIRMATION_CODE = 9999
    }

}