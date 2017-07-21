package com.example.wuntu.tv_bucket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.example.wuntu.tv_bucket.Adapters.MoviesDetailAdapter;
import com.example.wuntu.tv_bucket.Adapters.SimpleDividerItemDecoration;
import com.example.wuntu.tv_bucket.Models.MovieDetailModel;

import java.util.ArrayList;

public class MovieView extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recycler_view;
    MoviesDetailAdapter moviesDetailAdapter;
    ArrayList<MovieDetailModel> detailArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);

        toolbar.setTitle("Tv Bucket");

        detailArrayList = new ArrayList<>();
        moviesDetailAdapter = new MoviesDetailAdapter(MovieView.this,detailArrayList);

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        recycler_view.setLayoutManager(mLayoutManager);
        //recycler_view.addItemDecoration(new SimpleDividerItemDecoration(MovieView.this));
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(moviesDetailAdapter);

        prepareData();


    }

    private void prepareData()
    {
        MovieDetailModel movieDetailModel = new MovieDetailModel();
        movieDetailModel.setName("Lakshya Punhani");
        movieDetailModel.setCharacter("Lakshya");
        detailArrayList.add(0,movieDetailModel);

        MovieDetailModel movieDetailModel1 = new MovieDetailModel();
        movieDetailModel1.setName("Lakshya Punhani");
        movieDetailModel1.setCharacter("Lakshya");
        detailArrayList.add(0,movieDetailModel1);

        MovieDetailModel movieDetailModel2 = new MovieDetailModel();
        movieDetailModel2.setName("Lakshya Punhani");
        movieDetailModel2.setCharacter("Lakshya");
        detailArrayList.add(0,movieDetailModel2);

        MovieDetailModel movieDetailModel3 = new MovieDetailModel();
        movieDetailModel3.setName("Lakshya Punhani");
        movieDetailModel3.setCharacter("Lakshya");
        detailArrayList.add(0,movieDetailModel3);

        MovieDetailModel movieDetailModel4 = new MovieDetailModel();
        movieDetailModel4.setName("Lakshya Punhani");
        movieDetailModel4.setCharacter("Lakshya");
        detailArrayList.add(0,movieDetailModel4);

        moviesDetailAdapter.notifyDataSetChanged();

    }
}
