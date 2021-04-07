package ru.surfstudio.standard.i_network.service

import okhttp3.Interceptor
import okhttp3.Response
import ru.surfstudio.android.dagger.scope.PerApplication
import java.io.IOException
import javax.inject.Inject

const val HEADER_AUTH_KEY = "Bearer"

/**
 * добавляет необходимые для каждого запроса параметры, такие как token
 */
@PerApplication
class ServiceInterceptor @Inject constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (originalRequest.headers[HEADER_AUTH_KEY] != null) {
            return chain.proceed(originalRequest)
        }

        val headersBuilder = originalRequest.headers.newBuilder()

        val request = originalRequest.newBuilder()
                .headers(headersBuilder.build())
                .build()
        return chain.proceed(request)
    }
}
