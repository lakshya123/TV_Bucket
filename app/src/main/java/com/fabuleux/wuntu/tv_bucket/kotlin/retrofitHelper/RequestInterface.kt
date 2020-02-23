package com.fabuleux.wuntu.tv_bucket.kotlin.retrofitHelper

import com.fabuleux.wuntu.tv_bucket.kotlin.Models.RequestModel
import retrofit2.Call
import retrofit2.http.GET

interface RequestInterface
{
    @GET("")
    fun getMoviesList() : Call<RequestModel.ResponseModel<RequestModel.Movie>>

}