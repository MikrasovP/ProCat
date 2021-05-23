package vsu.csf.network.model.auth

import com.google.gson.annotations.SerializedName

data class UserLoginModel(
    @SerializedName("phoneNumber")
    val phoneNumber: String,
)
