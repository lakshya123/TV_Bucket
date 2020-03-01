package com.fabuleux.wuntu.tv_bucket.kotlin.retrofitHelper

import com.fabuleux.wuntu.tv_bucket.kotlin.models.CommonResponse
import com.fabuleux.wuntu.tv_bucket.kotlin.models.MoviePojo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RequestInterface
{
    @GET("movie/popular")
    fun getMoviesList(@Query("page") page:Int)
            : Call<CommonResponse<MoviePojo>>

    @GET("movie/{movie_id}")
    fun getMovieById(@Path("movie_id") movie_id:String,
                     @Query("append_to_response") string: String) : Call<MoviePojo>

}