package com.fabuleux.wuntu.tv_bucket.kotlin.repository

import androidx.lifecycle.LiveData
import com.fabuleux.wuntu.tv_bucket.kotlin.models.CommonResponse
import com.fabuleux.wuntu.tv_bucket.kotlin.models.MoviePojo
import com.fabuleux.wuntu.tv_bucket.kotlin.retrofitHelper.NetworkCall
import com.fabuleux.wuntu.tv_bucket.kotlin.retrofitHelper.Resource
import com.fabuleux.wuntu.tv_bucket.kotlin.retrofitHelper.RetrofitClient

class RepositoryImpl : Repository{

    private object HOLDER {
        val INSTANCE = RepositoryImpl()
    }

    companion object {
        val instance: RepositoryImpl by lazy { HOLDER.INSTANCE }
    }

    override fun getPopularMovies(page: Int):
            LiveData<Resource<CommonResponse<MoviePojo>>>
    {
        val call = NetworkCall<CommonResponse<MoviePojo>>()
        return call.makeCall(RetrofitClient.tmdbApi.getMoviesList(1))
    }

    override fun getMovieById(id: String): LiveData<Resource<MoviePojo>> {
        val call = NetworkCall<MoviePojo>()
        return call.makeCall(RetrofitClient.tmdbApi.getMovieById(id,"credits"))
    }


}