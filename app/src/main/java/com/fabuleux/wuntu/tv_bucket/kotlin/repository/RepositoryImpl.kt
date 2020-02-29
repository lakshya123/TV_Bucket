package com.fabuleux.wuntu.tv_bucket.kotlin.repository

import androidx.lifecycle.LiveData
import com.fabuleux.wuntu.tv_bucket.kotlin.models.CommonResponse
import com.fabuleux.wuntu.tv_bucket.kotlin.models.MovieListPojo
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
            LiveData<Resource<CommonResponse<MovieListPojo>>>
    {
        val call = NetworkCall<CommonResponse<MovieListPojo>>()
        return call.makeCall(RetrofitClient.tmdbApi.getMoviesList(1))
    }
}