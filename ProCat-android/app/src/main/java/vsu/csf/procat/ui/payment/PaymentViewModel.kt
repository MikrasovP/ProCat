package vsu.csf.procat.ui.payment

import androidx.core.text.trimmedLength
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber
import vsu.csf.procat.repo.RentRepo
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val rentRepo: RentRepo,
) : ViewModel() {

    private val payAmount = MutableLiveData(BigDecimal.valueOf(0))
    var rentId = MutableLiveData<Long>(null)
    val paySuccess = MutableLiveData(false)
    val payError = MutableLiveData(false)
    val payWaiting = MediatorLiveData<Boolean>().apply {
        value = true
        addSource(paySuccess) { value = isPayWaiting() }
        addSource(payError) { value = isPayWaiting() }
    }

    val creditCardNumber = MutableLiveData("")
    val payAmountString = Transformations.map(payAmount) { getPayAmountString(it) }

    val paymentTimeString = MutableLiveData("")

    val creditCardError = MutableLiveData(false)
    val loading = MutableLiveData(false)
    val networkError = MutableLiveData(false)

    fun onCreate(payAmount: BigDecimal?, rentId: Long?) {
        if (payAmount == null || rentId == null)
            return
        this.payAmount.value = payAmount
        this.rentId.value = rentId
    }

    fun onPayClick() {
        if (validateCreditCard())
            pay()
    }

    private fun validateCreditCard(): Boolean {
        if (creditCardNumber.value!!.trimmedLength() != PROPER_CREDIT_CARD_LENGTH) {
            creditCardError.value = true
            return false
        }
        creditCardError.value = false
        return true
    }

    private fun pay() {
        loading.value = true
        rentId.value?.let {
            rentRepo.payForRent(it)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ success ->
                    if (success){
                        paySuccess.value = true
                        paymentTimeString.value = formatCurrentTime()
                    }
                    else
                        payError.value = false
                    loading.value = false
                    networkError.value = false
                }, { ex ->
                    Timber.e(
                        ex,
                        "Error while rent payment request, rentId: $rentId , pay amount: ${payAmount.value}"
                    )
                    loading.value = false
                    networkError.value = true
                })
        } ?: Timber.e("Attempt to pay while rentId is null")
    }

    private fun getPayAmountString(payAmount: BigDecimal): String =
        payAmount.toString() + PAY_AMOUNT_SUFFIX

    private fun isPayWaiting(): Boolean =
        paySuccess.value == false && payError.value == false

    private fun formatCurrentTime(): String =
        SimpleDateFormat(DATE_FORMAT, Locale.ROOT).format(Date())

    companion object {
        private const val PAY_AMOUNT_SUFFIX = " рублей"
        private const val PROPER_CREDIT_CARD_LENGTH = 16
        private const val DATE_FORMAT = "dd.MM.yyyy HH:mm:ss"
    }

}