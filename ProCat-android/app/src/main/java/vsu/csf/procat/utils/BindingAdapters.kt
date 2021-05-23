package vsu.csf.procat.utils

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorText")
fun setError(tInputLayout: TextInputLayout, str: String?) {
    if (!str.isNullOrEmpty()) {
        tInputLayout.error = str
    } else {
        tInputLayout.error = null
    }
}
