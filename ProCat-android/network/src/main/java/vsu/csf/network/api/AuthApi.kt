package vsu.csf.network.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import vsu.csf.network.model.auth.UserAuthModel
import vsu.csf.network.model.auth.UserLoginModel
import vsu.csf.network.model.auth.UserRegistrationModel

interface AuthApi {

    @GET("/auth/check/{phoneNumber}")
    fun checkIsRegistered(@Path(value = "phoneNumber") phoneNumber: String): Single<Boolean>

    @POST("/auth/login")
    fun login(@Body loginModel: UserLoginModel): Single<UserAuthModel>

    @POST("/auth/register")
    fun register(@Body registrationModel: UserRegistrationModel): Single<UserAuthModel>

}