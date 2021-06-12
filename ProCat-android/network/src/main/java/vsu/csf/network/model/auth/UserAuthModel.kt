package vsu.csf.network.model.auth

import com.google.gson.annotations.SerializedName

data class UserAuthModel(
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("username")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("token")
    val authToken: String,
)
