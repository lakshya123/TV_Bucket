package com.fabuleux.wuntu.tv_bucket.kotlin.repository

import androidx.lifecycle.LiveData
import com.fabuleux.wuntu.tv_bucket.kotlin.models.CommonResponse
import com.fabuleux.wuntu.tv_bucket.kotlin.models.MovieListPojo
import com.fabuleux.wuntu.tv_bucket.kotlin.retrofitHelper.Resource

interface Repository {

    fun getPopularMovies(page : Int) :
            LiveData<Resource<CommonResponse<MovieListPojo>>>
}