package ru.surfstudio.standard.i_network.service

import ru.surfstudio.android.template.base.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.standard.base.util.getMd5Hash
import java.io.IOException
import javax.inject.Inject

const val PARAM_MARVEL_API_KEY = "apikey"
const val PARAM_MARVEL_TS_KEY = "ts"
const val PARAM_MARVEL_HASH_KEY = "hash"

/**
 * добавляет необходимые для каждого запроса параметры, такие как token
 */
@PerApplication
class ServiceInterceptor @Inject constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (originalRequest.headers[PARAM_MARVEL_API_KEY] != null) {
            return chain.proceed(originalRequest)
        }

        val timestamp = System.currentTimeMillis().toString()

        val url = originalRequest.url.newBuilder()
                .addQueryParameter(PARAM_MARVEL_API_KEY, BuildConfig.MARVEL_API_PUBLIC_KEY)
                .addQueryParameter(PARAM_MARVEL_TS_KEY, timestamp)
                .addQueryParameter(
                        PARAM_MARVEL_HASH_KEY,
                        getMd5Hash(
                                timestamp
                                        + BuildConfig.MARVEL_API_PRIVATE_KEY
                                        + BuildConfig.MARVEL_API_PUBLIC_KEY
                        )
                )

        val request = originalRequest.newBuilder()
                .url(url.build())
                .build()

        return chain.proceed(request)
    }
}
