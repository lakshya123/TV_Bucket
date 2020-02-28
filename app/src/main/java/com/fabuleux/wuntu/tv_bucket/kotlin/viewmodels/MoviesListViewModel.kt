package com.fabuleux.wuntu.tv_bucket.kotlin.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.fabuleux.wuntu.tv_bucket.kotlin.Models.RequestModel
import com.fabuleux.wuntu.tv_bucket.kotlin.repository.RepositoryImpl
import com.fabuleux.wuntu.tv_bucket.kotlin.retrofitHelper.Resource

class MoviesListViewModel : ViewModel() {

    var repository:RepositoryImpl = RepositoryImpl()
    private val callObserver: Observer<Resource<RequestModel.ResponseModel<RequestModel.Movie>>>
            = Observer { t -> processResponse(t) }

    fun getMovies(){
        repository.getPopularMovies(1).observeForever { callObserver.onChanged(it) }
    }

    fun processResponse(response: Resource<RequestModel.ResponseModel<RequestModel.Movie>>?){
        when(response?.status){
            Resource.Status.LOADING -> {
                Log.d("STATUS","LOADING")
            }
            Resource.Status.SUCCESS -> {
                Log.d("STATUS","SUCCESS")
            }
            Resource.Status.ERROR -> {
                Log.d("STATUS","ERROR")
            }
        }
    }
}
