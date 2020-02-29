package com.fabuleux.wuntu.tv_bucket.kotlin.retrofitHelper

import com.fabuleux.wuntu.tv_bucket.kotlin.models.CommonResponse
import com.fabuleux.wuntu.tv_bucket.kotlin.models.MovieListPojo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestInterface
{
    @GET("movie/popular")
    fun getMoviesList(@Query("page") page:Int)
            : Call<CommonResponse<MovieListPojo>>

}