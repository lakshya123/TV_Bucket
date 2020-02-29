package com.fabuleux.wuntu.tv_bucket.kotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fabuleux.wuntu.tv_bucket.R
import com.fabuleux.wuntu.tv_bucket.databinding.ItemMovieListBinding
import com.fabuleux.wuntu.tv_bucket.kotlin.models.MovieListPojo

class MoviesListAdapter(private val movies: List<MovieListPojo>) :
        RecyclerView.Adapter<MoviesListAdapter.MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMovieListBinding>(
                layoutInflater,R.layout.item_movie_list,parent,false)

        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.binding.movie = movies[position]
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MoviesViewHolder : RecyclerView.ViewHolder
    {
        lateinit var binding: ItemMovieListBinding

        constructor(view: View) : super(view)

        constructor(binding: ItemMovieListBinding) :this(binding.root){
            this.binding = binding
        }
    }
}