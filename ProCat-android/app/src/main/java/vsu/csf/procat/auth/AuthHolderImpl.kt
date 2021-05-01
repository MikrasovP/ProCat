package vsu.csf.procat.auth

import vsu.csf.network.AuthHolder
import javax.inject.Inject

class AuthHolderImpl @Inject constructor(): AuthHolder {

    private var authToken: String = "test"

    override fun getToken(): String = authToken
}