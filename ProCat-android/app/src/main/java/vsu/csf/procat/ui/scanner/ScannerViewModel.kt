package vsu.csf.procat.ui.scanner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ScannerViewModel @Inject constructor() : ViewModel() {

    val itemUuid = MutableLiveData<String>()

    fun proceedCodeData(rawCode: String?) {
        if (rawCode == null || !rawCode.startsWith(PROCAT_QR_START, ignoreCase = false))
            return

        itemUuid.value = rawCode.substring(PROCAT_QR_START.length)
    }

    companion object {
        private const val PROCAT_QR_START = "ProCat:"
    }

}