package com.fabuleux.wuntu.tv_bucket.kotlin.retrofitHelper

import com.fabuleux.wuntu.tv_bucket.kotlin.utils.API
import okhttp3.Interceptor
import okhttp3.OkHttpClient
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

    //OkhttpClient for building http request url
    private val tmdbClient = OkHttpClient().newBuilder()
            .addInterceptor(authInterceptor)
            .build()


    fun retrofit() : Retrofit = Retrofit.Builder()
            .client(tmdbClient)
            .baseUrl(API.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    val tmdbApi : RequestInterface = retrofit().create(RequestInterface::class.java)

}

//class CallBackKt<T> : Callback<T> {
//    override fun onFailure(call: Call<T>, t: Throwable) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onResponse(call: Call<T>, response: Response<T>) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}