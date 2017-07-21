package com.example.wuntu.tv_bucket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.wuntu.tv_bucket.Adapters.MoviesDetailAdapter;
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


    }
}
