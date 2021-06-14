package vsu.csf.procat.ui.payment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(

) : ViewModel() {

    val creditCardNumber = MutableLiveData("")
    val payAmountString = MutableLiveData("")

    val creditCardError = MutableLiveData(false)

}