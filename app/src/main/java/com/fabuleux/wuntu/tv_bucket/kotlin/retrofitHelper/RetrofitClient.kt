package com.fabuleux.wuntu.tv_bucket.kotlin.retrofitHelper

import com.fabuleux.wuntu.tv_bucket.kotlin.utils.API
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient
{
    private val authInterceptor = Interceptor {chain->
        val newUrl = chain.request().url()
                .newBuilder()
                .addQueryParameter("api_key", API.tmdbApiKey)
                .build()

        val newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .build()

        chain.proceed(newRequest)
    }

    private val loggingInterceptor = HttpLoggingInterceptor()


    //OkhttpClient for building http request url
    private val tmdbClient = OkHttpClient().newBuilder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()


    fun retrofit() : Retrofit = Retrofit.Builder()
            .client(tmdbClient)
            .baseUrl(API.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    val tmdbApi : RequestInterface = retrofit().create(RequestInterface::class.java)
}
