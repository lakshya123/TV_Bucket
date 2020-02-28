package com.fabuleux.wuntu.tv_bucket.kotlin.repository

import androidx.lifecycle.LiveData
import com.fabuleux.wuntu.tv_bucket.kotlin.Models.RequestModel
import com.fabuleux.wuntu.tv_bucket.kotlin.fragments.MoviesListFragment
import com.fabuleux.wuntu.tv_bucket.kotlin.retrofitHelper.NetworkCall
import com.fabuleux.wuntu.tv_bucket.kotlin.retrofitHelper.RequestInterface
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
            LiveData<Resource<RequestModel.ResponseModel<RequestModel.Movie>>>
    {
        val foreCastWeatherCall = NetworkCall<RequestModel.ResponseModel<RequestModel.Movie>>()
        return foreCastWeatherCall.makeCall(RetrofitClient.tmdbApi.getMoviesList(1))
    }
}