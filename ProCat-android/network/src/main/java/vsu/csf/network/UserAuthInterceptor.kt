package vsu.csf.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class UserAuthInterceptor : Interceptor {

    companion object {
        const val AUTH_TOKEN_HEADER_NAME = "X-Auth-Token"
    }

    val authToken: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val builder: Request.Builder = request.newBuilder()
        authToken?.let {token ->
            // Добавляем auth token, если еще не добавлен
            if (request.header(AUTH_TOKEN_HEADER_NAME) == null)
                builder.header(AUTH_TOKEN_HEADER_NAME, token)
        }
        request = builder.build()
        return chain.proceed(request)
    }

}