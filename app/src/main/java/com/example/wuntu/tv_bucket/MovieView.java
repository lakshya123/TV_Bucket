package com.example.wuntu.tv_bucket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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

        toolbar.setContentInsetStartWithNavigation(0);
        setSupportActionBar(toolbar);

        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);

        getSupportActionBar().setTitle("Tv Bucket");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        detailArrayList = new ArrayList<>();
        moviesDetailAdapter = new MoviesDetailAdapter(MovieView.this,detailArrayList);

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(moviesDetailAdapter);

        prepareData();


    }

    private void prepareData()
    {
        MovieDetailModel movieDetailModel = new MovieDetailModel();
        movieDetailModel.setName("Transformers Reunion");
        movieDetailModel.setCharacter("Transformers");
        detailArrayList.add(0,movieDetailModel);

        MovieDetailModel movieDetailModel1 = new MovieDetailModel();
        movieDetailModel1.setName("Transformers Reunion");
        movieDetailModel1.setCharacter("Transformers");
        detailArrayList.add(0,movieDetailModel1);

        MovieDetailModel movieDetailModel2 = new MovieDetailModel();
        movieDetailModel2.setName("Transformers Reunion");
        movieDetailModel2.setCharacter("Transformers");
        detailArrayList.add(0,movieDetailModel2);

        MovieDetailModel movieDetailModel3 = new MovieDetailModel();
        movieDetailModel3.setName("Transformers Reunion");
        movieDetailModel3.setCharacter("Transformers");
        detailArrayList.add(0,movieDetailModel3);

        MovieDetailModel movieDetailModel4 = new MovieDetailModel();
        movieDetailModel4.setName("Transformers Reunion");
        movieDetailModel4.setCharacter("Transformers");
        detailArrayList.add(0,movieDetailModel4);

        moviesDetailAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
