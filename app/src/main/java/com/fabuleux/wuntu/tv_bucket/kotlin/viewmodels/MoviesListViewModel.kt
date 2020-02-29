package com.fabuleux.wuntu.tv_bucket.kotlin.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.fabuleux.wuntu.tv_bucket.kotlin.models.CommonResponse
import com.fabuleux.wuntu.tv_bucket.kotlin.models.MovieListPojo
import com.fabuleux.wuntu.tv_bucket.kotlin.repository.RepositoryImpl
import com.fabuleux.wuntu.tv_bucket.kotlin.retrofitHelper.Resource

class MoviesListViewModel : ViewModel() {

    private var repository:RepositoryImpl = RepositoryImpl()
    private val callObserver:
            Observer<Resource<CommonResponse<MovieListPojo>>>
            = Observer { t -> processResponse(t) }
    private val moviesResponse:MutableLiveData<List<MovieListPojo>> = MutableLiveData()

    fun fetchMovies() {
        repository.getPopularMovies(1).observeForever { callObserver.onChanged(it) }
    }

    private fun processResponse(response:
                                Resource<CommonResponse<MovieListPojo>>?){
        when(response?.status){
            Resource.Status.LOADING -> {
                Log.d("STATUS","LOADING")
            }
            Resource.Status.SUCCESS -> {
                Log.d("STATUS","SUCCESS")
                moviesResponse.value = response.data?.result
            }
            Resource.Status.ERROR -> {
                Log.d("STATUS","ERROR")
            }
        }
    }

    fun getMovies() : LiveData<List<MovieListPojo>>
    {
        return moviesResponse
    }
}
