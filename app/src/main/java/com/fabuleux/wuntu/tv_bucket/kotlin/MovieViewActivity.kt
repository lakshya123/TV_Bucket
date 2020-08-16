package com.fabuleux.wuntu.tv_bucket.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.fabuleux.wuntu.tv_bucket.R
import com.fabuleux.wuntu.tv_bucket.databinding.ActivityMovieViewBinding
import com.fabuleux.wuntu.tv_bucket.kotlin.models.MoviePojo
import com.fabuleux.wuntu.tv_bucket.kotlin.viewmodels.MovieViewModel

class MovieViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieViewBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_movie_view)

        movieId = intent.getStringExtra("movie_id")!!

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        viewModel.getMovie().observe(this, Observer { t -> initData(t) })
        viewModel.fetchParticularMovie(movieId)
    }

    private fun initData(moviePojo: MoviePojo){
        binding.movie = moviePojo
    }

}
