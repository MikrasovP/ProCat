package vsu.csf.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class UserAuthInterceptor @Inject constructor(
    private val authHolder: AuthHolder
) : Interceptor {

    companion object {
        private const val AUTH_TOKEN_HEADER_NAME = "Authorization"
        private const val AUTH_TOKEN_HEADER_BEARER = "Bearer "
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder: Request.Builder = chain.request().newBuilder()
        val token = authHolder.authToken

        // Add auth token if we have one
        if (token.isNotBlank())
            requestBuilder.header(AUTH_TOKEN_HEADER_NAME, AUTH_TOKEN_HEADER_BEARER + token)

        return chain.proceed(requestBuilder.build())
    }

}