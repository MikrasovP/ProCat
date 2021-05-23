package vsu.csf.network.model.auth

import com.google.gson.annotations.SerializedName

data class UserAuthModel(
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("authToken")
    val authToken: String,
)
