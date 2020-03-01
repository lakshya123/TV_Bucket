package com.fabuleux.wuntu.tv_bucket.kotlin.repository

import androidx.lifecycle.LiveData
import com.fabuleux.wuntu.tv_bucket.kotlin.models.CommonResponse
import com.fabuleux.wuntu.tv_bucket.kotlin.models.MoviePojo
import com.fabuleux.wuntu.tv_bucket.kotlin.retrofitHelper.Resource

interface Repository {

    fun getPopularMovies(page : Int) :
            LiveData<Resource<CommonResponse<MoviePojo>>>

    fun getMovieById(id:String) : LiveData<Resource<MoviePojo>>
}