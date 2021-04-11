package vsu.csf.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class UserAuthInterceptor @Inject constructor(
    private val authHolder: AuthHolder
) : Interceptor {

    companion object {
        const val AUTH_TOKEN_HEADER_NAME = "X-Auth-Token"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val builder: Request.Builder = request.newBuilder()
        val token = authHolder.getToken()

        // Add auth token if we have one
        if (token.isNotBlank())
            builder.header(AUTH_TOKEN_HEADER_NAME, token)

        request = builder.build()
        return chain.proceed(request)
    }

}