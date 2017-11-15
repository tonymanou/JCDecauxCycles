package com.tonymanou.jcdecauxcycles.net

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(
        private val apiKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url()

        // Add the API-key as query parameter
        val newHttpUrl = originalHttpUrl.newBuilder()
                .addQueryParameter("apiKey", apiKey)
                .build()
        val newRequest = originalRequest.newBuilder()
                .url(newHttpUrl)
                .build()

        return chain.proceed(newRequest)
    }
}
