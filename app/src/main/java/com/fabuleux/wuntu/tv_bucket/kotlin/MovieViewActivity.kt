package com.fabuleux.wuntu.tv_bucket.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fabuleux.wuntu.tv_bucket.R
import com.fabuleux.wuntu.tv_bucket.databinding.ActivityMovieViewBinding
import com.fabuleux.wuntu.tv_bucket.kotlin.models.MoviePojo
import com.fabuleux.wuntu.tv_bucket.kotlin.viewmodels.MovieViewModel

class MovieViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieViewBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var movie_id:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_movie_view)

        movie_id = intent.getStringExtra("movie_id")!!

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        viewModel.getMovie().observe(this, Observer { t -> initData(t) })
        viewModel.fetchParticularMovie(movie_id)

    }

    private fun initData(moviePojo: MoviePojo){
        binding.setVariable(1,moviePojo)
        binding.executePendingBindings()
    }

}
