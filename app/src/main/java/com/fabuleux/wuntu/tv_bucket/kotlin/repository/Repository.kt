package com.fabuleux.wuntu.tv_bucket.kotlin.repository

import androidx.lifecycle.LiveData
import com.fabuleux.wuntu.tv_bucket.kotlin.Models.RequestModel
import com.fabuleux.wuntu.tv_bucket.kotlin.retrofitHelper.Resource

interface Repository {

    fun getPopularMovies(page : Int) :
            LiveData<Resource<RequestModel.ResponseModel<RequestModel.Movie>>>
}