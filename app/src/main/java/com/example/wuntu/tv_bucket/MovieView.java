package com.example.wuntu.tv_bucket;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.wuntu.tv_bucket.Adapters.MoviesAdapter;
import com.example.wuntu.tv_bucket.Adapters.MoviesDetailAdapter;
import com.example.wuntu.tv_bucket.Models.MovieDetailFull;
import com.example.wuntu.tv_bucket.Models.MovieDetailModel;
import com.example.wuntu.tv_bucket.Models.Popular_Movies_Model;
import com.example.wuntu.tv_bucket.Models.SpokenLanguage;
import com.example.wuntu.tv_bucket.Utils.AppSingleton;
import com.example.wuntu.tv_bucket.Utils.UrlConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieView extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recycler_view;
    MoviesDetailAdapter moviesDetailAdapter;
    ArrayList<MovieDetailModel> detailArrayList;
    UrlConstants urlConstants = UrlConstants.getSingletonRef();
    private Gson gson;
    MovieDetailFull movieDetailFull;


    TextView ratings,overview,status,release_date,genre1,genre2,genre3,genre4,budget,revenue,runtime,homepage;
    ImageView backdrop_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        movieDetailFull = new MovieDetailFull();

        toolbar.setContentInsetStartWithNavigation(0);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String ID = getIntent().getStringExtra("ID");
        String url = urlConstants.Movie_1st_URL + ID + urlConstants.Movie_2nd_URL;



        ratings = (TextView) findViewById(R.id.ratings);
        overview = (TextView) findViewById(R.id.overview);
        status = (TextView) findViewById(R.id.status);
        release_date = (TextView) findViewById(R.id.release_date);
        genre1= (TextView) findViewById(R.id.genre1);
        genre2 = (TextView) findViewById(R.id.genre2);
        genre3 = (TextView) findViewById(R.id.genre3);
        genre4 = (TextView) findViewById(R.id.genre4);
        budget = (TextView) findViewById(R.id.budget);
        revenue = (TextView) findViewById(R.id.revenue);
        runtime = (TextView) findViewById(R.id.runtime);
        homepage = (TextView) findViewById(R.id.homepage);

        backdrop_image = (ImageView) findViewById(R.id.backdrop_image);


        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);





        detailArrayList = new ArrayList<>();
        moviesDetailAdapter = new MoviesDetailAdapter(MovieView.this,detailArrayList);

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(moviesDetailAdapter);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        prepareOnlineData(url);

        prepareData();


    }

    private void prepareOnlineData(String url)
    {

        String tag_json_obj = "json_obj_req1";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();


        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                movieDetailFull = gson.fromJson(response,MovieDetailFull.class);
                toolbar.setTitle(movieDetailFull.getTitle());
                ratings.setText(String.valueOf(movieDetailFull.getVoteAverage()));
                overview.setText(movieDetailFull.getOverview());
                status.setText(movieDetailFull.getStatus());
                release_date.setText(movieDetailFull.getReleaseDate());
                budget.setText("$" + String.valueOf(movieDetailFull.getBudget()));
                revenue.setText("$" + String.valueOf(movieDetailFull.getRevenue()));
                runtime.setText(String.valueOf(movieDetailFull.getRuntime()));
                homepage.setText(movieDetailFull.getHomepage());



                String url2 = movieDetailFull.getBackdropPath();
                String url3 = urlConstants.URL_Image + url2;


                Picasso.with(MovieView.this)
                        .load(url3)
                        .into(backdrop_image);


                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MovieView.this, "Volley Error", Toast.LENGTH_SHORT).show();
                pDialog.hide();

            }
        });

        AppSingleton.getInstance(this).addToRequestQueue(stringRequest1, tag_json_obj);
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

        MovieDetailModel movieDetailModel5 = new MovieDetailModel();
        movieDetailModel5.setName("Transformers Reunion");
        movieDetailModel5.setCharacter("Transformers");
        detailArrayList.add(0,movieDetailModel5);

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
