package com.fabuleux.wuntu.tv_bucket.kotlin.retrofitHelper

import com.fabuleux.wuntu.tv_bucket.kotlin.Models.RequestModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestInterface
{
    @GET("movie/popular")
    fun getMoviesList(@Query("page") page:Int)
            : Call<RequestModel.ResponseModel<RequestModel.Movie>>

}