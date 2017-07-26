package com.example.wuntu.tv_bucket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.wuntu.tv_bucket.Adapters.CastDetailAdapter;
import com.example.wuntu.tv_bucket.Adapters.MoviesAdapter_OnClickListener;
import com.example.wuntu.tv_bucket.Models.Cast;
import com.example.wuntu.tv_bucket.Models.MovieDetailFull;
import com.example.wuntu.tv_bucket.Utils.AppSingleton;
import com.example.wuntu.tv_bucket.Utils.UrlConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieView extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recycler_view;
    CastDetailAdapter castDetailAdapter;
    UrlConstants urlConstants = UrlConstants.getSingletonRef();
    private Gson gson;
    MovieDetailFull movieDetailFull;

    ArrayList<Cast> castArrayList;
    ArrayList<Cast> subCastArrayList;


    TextView ratings,overview,status,release_date,genre1,genre2,genre3,genre4,genre5,genre6,budget,revenue,runtime,homepage;
    ImageView backdrop_image;
    String url2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        movieDetailFull = new MovieDetailFull();
        castArrayList = new ArrayList<>();
        subCastArrayList = new ArrayList<>();




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
        genre5 = (TextView) findViewById(R.id.genre5);
        genre6 = (TextView) findViewById(R.id.genre6);
        budget = (TextView) findViewById(R.id.budget);
        revenue = (TextView) findViewById(R.id.revenue);
        runtime = (TextView) findViewById(R.id.runtime);
        homepage = (TextView) findViewById(R.id.homepage);

        backdrop_image = (ImageView) findViewById(R.id.backdrop_image);


        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);





        castDetailAdapter = new CastDetailAdapter(MovieView.this,castArrayList,subCastArrayList);

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(castDetailAdapter);


        recycler_view.addOnItemTouchListener(
                new MoviesAdapter_OnClickListener(MovieView.this, recycler_view ,new MoviesAdapter_OnClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        if (position == subCastArrayList.size() - 1)
                        {

                        }
                        else
                        {
                            Intent intent = new Intent(MovieView.this,CastViewActivity.class);
                            intent.putExtra("ID",subCastArrayList.get(position).getId());
                            intent.putExtra("EVENT","TOUCH EVENT");
                            startActivity(intent);
                        }



                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever

                    }
                })
        );

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        prepareOnlineData(url);



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

                //Setting text to the textviews
                movieDetailFull = gson.fromJson(response,MovieDetailFull.class);
                String hours = String.valueOf(movieDetailFull.getRuntime() / 60);
                String minutes = String.valueOf(movieDetailFull.getRuntime() % 60);
                toolbar.setTitle(movieDetailFull.getTitle());
                ratings.setText(String.valueOf(movieDetailFull.getVoteAverage()));
                overview.setText(movieDetailFull.getOverview());
                status.setText(movieDetailFull.getStatus());
                release_date.setText(movieDetailFull.getReleaseDate());
                budget.setText("$" + String.valueOf(movieDetailFull.getBudget()));
                revenue.setText("$" + String.valueOf(movieDetailFull.getRevenue()));
                runtime.setText(hours + "h " + minutes  +"m");
                homepage.setText(movieDetailFull.getHomepage());

                //Setting text to genres
                int value = movieDetailFull.getGenres().size();
                if (value >= 6)
                {
                    genre1.setText(movieDetailFull.getGenres().get(0).getName());
                    genre2.setText(movieDetailFull.getGenres().get(1).getName());
                    genre3.setText(movieDetailFull.getGenres().get(2).getName());
                    genre4.setText(movieDetailFull.getGenres().get(3).getName());
                    genre5.setText(movieDetailFull.getGenres().get(4).getName());
                    genre6.setText(movieDetailFull.getGenres().get(4).getName());
                }
                else if (value == 5)
                {
                    genre1.setText(movieDetailFull.getGenres().get(0).getName());
                    genre2.setText(movieDetailFull.getGenres().get(1).getName());
                    genre3.setText(movieDetailFull.getGenres().get(2).getName());
                    genre4.setText(movieDetailFull.getGenres().get(3).getName());
                    genre5.setText(movieDetailFull.getGenres().get(4).getName());
                    genre6.setVisibility(View.GONE);
                }
                else if (value == 4)
                {
                    genre1.setText(movieDetailFull.getGenres().get(0).getName());
                    genre2.setText(movieDetailFull.getGenres().get(1).getName());
                    genre3.setText(movieDetailFull.getGenres().get(2).getName());
                    genre4.setText(movieDetailFull.getGenres().get(3).getName());
                    genre5.setVisibility(View.GONE);
                    genre6.setVisibility(View.GONE);
                }
                else if (value == 3)
                {
                    genre1.setText(movieDetailFull.getGenres().get(0).getName());
                    genre2.setText(movieDetailFull.getGenres().get(1).getName());
                    genre3.setText(movieDetailFull.getGenres().get(2).getName());
                    genre4.setVisibility(View.GONE);
                    genre5.setVisibility(View.GONE);
                    genre6.setVisibility(View.GONE);
                }
                else if (value == 2)
                {
                    genre1.setText(movieDetailFull.getGenres().get(0).getName());
                    genre2.setText(movieDetailFull.getGenres().get(1).getName());
                    genre3.setVisibility(View.GONE);
                    genre4.setVisibility(View.GONE);
                    genre5.setVisibility(View.GONE);
                    genre6.setVisibility(View.GONE);
                }
                else if (value == 1)
                {
                    genre1.setText(movieDetailFull.getGenres().get(0).getName());
                    genre2.setVisibility(View.GONE);
                    genre3.setVisibility(View.GONE);
                    genre4.setVisibility(View.GONE);
                    genre5.setVisibility(View.GONE);
                    genre6.setVisibility(View.GONE);
                }
                else
                {
                    genre1.setVisibility(View.GONE);
                    genre2.setVisibility(View.GONE);
                    genre3.setVisibility(View.GONE);
                    genre4.setVisibility(View.GONE);
                    genre5.setVisibility(View.GONE);
                    genre6.setVisibility(View.GONE);
                }


                //Setting Image to BAckDrop Imageview
                if (movieDetailFull.getBelongsToCollection() == null)
                {
                    url2 = movieDetailFull.getBackdropPath();
                }
                else
                {
                    url2 = movieDetailFull.getBelongsToCollection().getBackdropPath();
                }

                String url3 = urlConstants.URL_Image + url2;

                Picasso.with(MovieView.this)
                        .load(url3)
                        .into(backdrop_image);


                //Making Arraylist for RecyclerView
                int i;
                for (i=0;i<movieDetailFull.getCredits().getCast().size();i++)
                {
                    Cast cast = new Cast();
                    cast.setName(movieDetailFull.getCredits().getCast().get(i).getName());
                    cast.setCharacter(movieDetailFull.getCredits().getCast().get(i).getCharacter());
                    cast.setId(movieDetailFull.getCredits().getCast().get(i).getId());
                    cast.setProfilePath(movieDetailFull.getCredits().getCast().get(i).getProfilePath());
                    castArrayList.add(i,cast);
                    castDetailAdapter.notifyDataSetChanged();
                }

                castDetailAdapter.notifyDataSetChanged();

                subCastArrayList.clear();

                if (castArrayList.size() > 8)
                {
                    subCastArrayList.addAll(castArrayList.subList(0,8));
                    castDetailAdapter.notifyDataSetChanged();
                }
                else
                {
                    subCastArrayList.addAll(castArrayList);
                    castDetailAdapter.notifyDataSetChanged();
                }





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




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
