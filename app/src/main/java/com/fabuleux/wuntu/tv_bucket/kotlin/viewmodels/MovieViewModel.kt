package com.fabuleux.wuntu.tv_bucket.kotlin.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.fabuleux.wuntu.tv_bucket.kotlin.models.MoviePojo
import com.fabuleux.wuntu.tv_bucket.kotlin.repository.RepositoryImpl
import com.fabuleux.wuntu.tv_bucket.kotlin.retrofitHelper.Resource

class MovieViewModel : ViewModel() {

    private var repository : RepositoryImpl = RepositoryImpl()
    private var callObserver: Observer<Resource<MoviePojo>> =
            Observer { t -> processResponse(t) }
    private var movie:MutableLiveData<MoviePojo> = MutableLiveData()

    fun fetchParticularMovie(id:String){
        repository.getMovieById(id).observeForever { callObserver.onChanged(it) }
    }

    private fun processResponse(response:
                                Resource<MoviePojo>?){
        when(response?.status){
            Resource.Status.LOADING -> {
                Log.d("STATUS","LOADING")
            }
            Resource.Status.SUCCESS -> {
                movie.value = response.data
                Log.d("STATUS","SUCCESS")
            }
            Resource.Status.ERROR -> {
                Log.d("STATUS","ERROR")
            }
        }
    }

    fun getMovie() : LiveData<MoviePojo> = movie
}