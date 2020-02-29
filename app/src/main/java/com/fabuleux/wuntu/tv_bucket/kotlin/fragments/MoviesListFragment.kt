package com.fabuleux.wuntu.tv_bucket.kotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabuleux.wuntu.tv_bucket.databinding.MoviesListFragmentBinding
import com.fabuleux.wuntu.tv_bucket.kotlin.adapters.MoviesListAdapter
import com.fabuleux.wuntu.tv_bucket.kotlin.models.MovieListPojo
import com.fabuleux.wuntu.tv_bucket.kotlin.viewmodels.MoviesListViewModel
import kotlinx.android.synthetic.main.movies_list_fragment.*

class MoviesListFragment : Fragment() {

    companion object {
        fun newInstance() = MoviesListFragment()
    }
    private lateinit var viewModel: MoviesListViewModel
    private lateinit var binding: MoviesListFragmentBinding
    private lateinit var moviesListAdapter : MoviesListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        binding = MoviesListFragmentBinding.inflate(inflater,
                container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(MoviesListViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getMovies().observe(viewLifecycleOwner, Observer { t -> notifyDataChange(t) })
    }

    private fun notifyDataChange(movies:List<MovieListPojo>)
    {
        moviesListAdapter = MoviesListAdapter(movies)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        tv_recycler_view.setHasFixedSize(true)
        tv_recycler_view.adapter = moviesListAdapter
        tv_recycler_view.layoutManager = layoutManager
        moviesListAdapter.notifyDataSetChanged()
    }

    override fun onResume()
    {
        super.onResume()
        viewModel.fetchMovies()
    }

}
