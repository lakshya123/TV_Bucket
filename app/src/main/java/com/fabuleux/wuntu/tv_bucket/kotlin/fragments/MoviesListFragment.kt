package com.fabuleux.wuntu.tv_bucket.kotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fabuleux.wuntu.tv_bucket.databinding.MoviesListFragmentBinding
import com.fabuleux.wuntu.tv_bucket.kotlin.viewmodels.MoviesListViewModel

class MoviesListFragment : Fragment() {

    companion object {
        fun newInstance() = MoviesListFragment()
    }

    private lateinit var viewModel: MoviesListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val binding = MoviesListFragmentBinding.inflate(inflater,
                container,false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MoviesListViewModel::class.java)
        viewModel.getMovies()
    }

}
