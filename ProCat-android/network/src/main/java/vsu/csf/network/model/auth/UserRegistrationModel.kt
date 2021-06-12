package vsu.csf.network.model.auth

import com.google.gson.annotations.SerializedName

data class UserRegistrationModel(
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("username")
    val name: String,
    @SerializedName("email")
    val email: String,
)
