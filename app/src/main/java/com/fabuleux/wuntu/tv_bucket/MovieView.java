package com.fabuleux.wuntu.tv_bucket;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fabuleux.wuntu.tv_bucket.Adapters.CastDetailAdapter;
import com.fabuleux.wuntu.tv_bucket.Adapters.MoviesAdapter_OnClickListener;
import com.fabuleux.wuntu.tv_bucket.Models.Cast;
import com.fabuleux.wuntu.tv_bucket.Models.MovieDetailFull;
import com.fabuleux.wuntu.tv_bucket.Models.TvExampleModel;
import com.fabuleux.wuntu.tv_bucket.Models.TvSeasons;
import com.fabuleux.wuntu.tv_bucket.Utils.AppSingleton;
import com.fabuleux.wuntu.tv_bucket.Utils.UrlConstants;
import com.fabuleux.wuntu.tv_bucket.Utils.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MovieView extends AppCompatActivity {

    String image_url;
    Toolbar toolbar;
    RecyclerView recycler_view;
    CastDetailAdapter castDetailAdapter;
    UrlConstants urlConstants = UrlConstants.getSingletonRef();
    private Gson gson;
    MovieDetailFull movieDetailFull;
    CoordinatorLayout coordinator_layout_movie_view;
    LinearLayout season_layout;
    LinearLayout overview_layout,facts_layout;

    TvExampleModel tvExampleModel;
    TextView play_trailer;

    ArrayList<Cast> castArrayList;
    ArrayList<Cast> subCastArrayList;
    ArrayList<TvSeasons> seasonsArrayList;


    LinearLayout season_list;

    TextView ratings,overview,status,release_date,genre1,budget,revenue,runtime,homepage,top_billed_cast;
    ImageView backdrop_image,image_play_trailer,poster_image;
    TextView budget_title,revenue_title,current_season,season_number,current_season_year,current_season_episodes,current_season_tagline,view_all_seasons;
    TextView release_date_title,overview_title,status_title,genres_title,runtime_title,homepage_title,facts;
    String url2;
    String ID;
    int i;
    StringBuilder sb;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);
        initviews();

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);

        movieDetailFull = new MovieDetailFull();
        castArrayList = new ArrayList<>();
        subCastArrayList = new ArrayList<>();
        tvExampleModel = new TvExampleModel();
        seasonsArrayList = new ArrayList<>();

        toolbar.setContentInsetStartWithNavigation(0);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        ID = getIntent().getStringExtra("ID");

        castDetailAdapter = new CastDetailAdapter(castArrayList,subCastArrayList);

        RecyclerView.LayoutManager mLayoutManager = new
                StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setNestedScrollingEnabled(false);
        recycler_view.setAdapter(castDetailAdapter);

        recycler_view.addOnItemTouchListener(
                new MoviesAdapter_OnClickListener(
                        MovieView.this, recycler_view ,
                        new MoviesAdapter_OnClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        if (subCastArrayList.size()>7)
                        {
                            if (position == subCastArrayList.size() - 1)
                            {
                                // Done things in Adapter for this
                                Log.d("Nothing","Nothing");

                            }
                            else
                            {
                                Intent intent = new Intent(MovieView.this,CastViewActivity.class);
                                intent.putExtra("ID",subCastArrayList.get(position).getId());
                                intent.putExtra("EVENT","TOUCH EVENT");
                                startActivity(intent);
                            }
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

        final String view = getIntent().getStringExtra("VIEW");
        if (view.equals("MOVIE"))
        {
            season_layout.setVisibility(View.GONE);
            budget_title.setVisibility(View.VISIBLE);
            budget.setVisibility(View.VISIBLE);
            revenue_title.setVisibility(View.VISIBLE);
            revenue.setVisibility(View.VISIBLE);
            release_date_title.setText(R.string.release_date);
            String url = urlConstants.Movie_1st_URL + ID + urlConstants.Movie_2nd_URL;
            prepareMovieData(url);
        }
        else if (view.equals("TV"))
        {
            season_layout.setVisibility(View.VISIBLE);
            budget_title.setVisibility(View.GONE);
            budget.setVisibility(View.GONE);
            revenue.setVisibility(View.GONE);
            revenue_title.setVisibility(View.GONE);
            String url = urlConstants.TV_1st_URL + ID + urlConstants.TV_2nd_URL;
            release_date_title.setText(R.string.first_air_date);
            prepareTvData(url);
        }




        image_play_trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {
                if (view.equals("MOVIE"))
                {
                    Intent intent = new Intent(MovieView.this,YoutubeActivity.class);
                    intent.putExtra("VIEW","MOVIE");
                    intent.putExtra("ID",ID);
                    startActivity(intent);
                }
                else if (view.equals("TV"))
                {
                    Intent intent = new Intent(MovieView.this,YoutubeActivity.class);
                    intent.putExtra("VIEW","TV");
                    intent.putExtra("ID",ID);
                    startActivity(intent);
                }

            }
        });


        play_trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {
                if (view.equals("MOVIE"))
                {
                    Intent intent = new Intent(MovieView.this,YoutubeActivity.class);
                    intent.putExtra("VIEW","MOVIE");
                    intent.putExtra("ID",ID);
                    startActivity(intent);
                }
                else if (view.equals("TV"))
                {
                    Intent intent = new Intent(MovieView.this,YoutubeActivity.class);
                    intent.putExtra("VIEW","TV");
                    intent.putExtra("ID",ID);
                    startActivity(intent);
                }

            }
        });

        view_all_seasons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieView.this,CastViewActivity.class);
                intent.putExtra("EVENT","VIEW_SEASONS");
                intent.putExtra("SEASONS_LIST",seasonsArrayList);
                startActivity(intent);
            }
        });




    }



    private void initviews()
    {


        season_list = (LinearLayout) findViewById(R.id.season_list);
        poster_image = (ImageView) findViewById(R.id.poster_image);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        image_play_trailer= (ImageView)findViewById(R.id.image_play_trailer);
        coordinator_layout_movie_view = (CoordinatorLayout) findViewById(R.id.coordinator_layout_movie_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        season_layout = (LinearLayout)findViewById(R.id.season_layout);
        play_trailer = (TextView) findViewById(R.id.text_play_trailer);
        ratings = (TextView) findViewById(R.id.ratings);
        overview = (TextView) findViewById(R.id.overview);
        status = (TextView) findViewById(R.id.status);
        release_date = (TextView) findViewById(R.id.release_date);

        genre1 = (TextView) findViewById(R.id.genres);
        budget = (TextView) findViewById(R.id.budget);
        revenue = (TextView) findViewById(R.id.revenue);
        runtime = (TextView) findViewById(R.id.runtime);
        homepage = (TextView) findViewById(R.id.homepage);

        backdrop_image = (ImageView) findViewById(R.id.backdrop_image);


        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);

        budget_title = (TextView) findViewById(R.id.budget_title);
        revenue_title = (TextView) findViewById(R.id.revenue_title);

        current_season = (TextView) findViewById(R.id.current_season);
        season_number = (TextView) findViewById(R.id.season_number);
        current_season_year = (TextView) findViewById(R.id.current_season_year);
        current_season_episodes = (TextView) findViewById(R.id.current_season_episodes);
        current_season_tagline = (TextView) findViewById(R.id.current_season_tagline);
        view_all_seasons = (TextView) findViewById(R.id.view_all_seasons);

        release_date_title = (TextView) findViewById(R.id.release_date_title);
        overview_layout = (LinearLayout) findViewById(R.id.overview_layout);

        overview_title = (TextView)findViewById(R.id.overview_title);

        facts_layout = (LinearLayout)findViewById(R.id.facts_layout);

        status_title = (TextView) findViewById(R.id.status_title);
        genres_title = (TextView) findViewById(R.id.genres_title);
        runtime_title = (TextView) findViewById(R.id.runtime_title);
        homepage_title= (TextView) findViewById(R.id.homepage_title);

        facts = (TextView)findViewById(R.id.facts);

        top_billed_cast = (TextView) findViewById(R.id.top_billed_cast);



    }

    private void prepareTvData(String url)
    {
        String tag_json_obj = "json_obj_req1";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                pDialog.dismiss();
                tvExampleModel = gson.fromJson(response,TvExampleModel.class);



                if (tvExampleModel.getCredits().getCast().size() == 0)
                {
                    top_billed_cast.setVisibility(View.GONE);
                }



                if (tvExampleModel.getName() != null)
                {
                    toolbar.setTitle(tvExampleModel.getName());
                }


                if (tvExampleModel.getOverview() != null)
                {
                    overview.setText(tvExampleModel.getOverview());
                }
                else
                {
                    overview_layout.setVisibility(View.GONE);
                }
                if (tvExampleModel.getVoteAverage() != null)
                {
                    ratings.setText(String.valueOf(tvExampleModel.getVoteAverage()));
                }
                if (tvExampleModel.getStatus() != null)
                {
                    status.setText(tvExampleModel.getStatus());
                }

                if (tvExampleModel.getFirstAirDate() != null)
                {
                    release_date.setText(tvExampleModel.getFirstAirDate());
                }


                if (tvExampleModel.getSeasons().size() == 0)
                {
                    season_layout.setVisibility(View.GONE);
                }
                else {


                    //Season Layout

                    for (i = 0; i < tvExampleModel.getSeasons().size(); i++) {
                        TvSeasons tvSeasons = new TvSeasons();
                        tvSeasons.setAirDate(tvExampleModel.getSeasons().get(i).getAirDate());
                        tvSeasons.setEpisodeCount(tvExampleModel.getSeasons().get(i).getEpisodeCount());
                        tvSeasons.setId(tvExampleModel.getId());
                        tvSeasons.setPosterPath(tvExampleModel.getSeasons().get(i).getPosterPath());
                        tvSeasons.setSeasonNumber(tvExampleModel.getSeasons().get(i).getSeasonNumber());
                        tvSeasons.setLastAirDate(tvExampleModel.getLastAirDate());
                        seasonsArrayList.add(tvSeasons);
                    }
                    final int size = tvExampleModel.getSeasons().size();

                    Target target1 = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            poster_image.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                            poster_image.setImageDrawable(placeHolderDrawable);
                        }
                    };
                    String season_poster_image = urlConstants.URL_Image + tvExampleModel.getSeasons().get(size - 1).getPosterPath();
                    Picasso.with(MovieView.this)
                            .load(season_poster_image)
                            .placeholder(R.drawable.not_available)
                            .into(target1);

                    poster_image.setTag(target1);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    Calendar calendar = Calendar.getInstance();
                    String date = dateFormat.format(calendar.getTime());
                    String last_air_Date_string;



                    String last_season_air_date_string;

                    if (tvExampleModel.getSeasons().get(size - 1).getAirDate() != null && tvExampleModel.getLastAirDate() != null )
                    {
                        last_air_Date_string = tvExampleModel.getLastAirDate();
                        last_season_air_date_string = tvExampleModel.getSeasons().get(size - 1).getAirDate();
                        String last_season_year = last_season_air_date_string.substring(0, 4);
                        current_season_year.setText(last_season_year);

                        try {
                            Date today_date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
                            Date last_air_date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(last_air_Date_string);
                            Date last_season_air_date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(last_season_air_date_string);

                            if (last_air_date.compareTo(today_date) < 0) {
                                current_season.setText(R.string.last_season);
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Season ");
                                stringBuilder.append(String.valueOf(tvExampleModel.getSeasons().get(size - 1).getSeasonNumber()));
                                stringBuilder.append(" of ");
                                stringBuilder.append(tvExampleModel.getName());
                                stringBuilder.append(" premiered on ");
                                stringBuilder.append(last_season_air_date_string);
                                current_season_tagline.setText(stringBuilder);
                            } else if (last_air_date.compareTo(today_date) > 0) {
                                if (last_season_air_date.compareTo(today_date) > 0) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("Season ");
                                    stringBuilder.append(String.valueOf(tvExampleModel.getSeasons().get(size - 1).getSeasonNumber()));
                                    stringBuilder.append(" of ");
                                    stringBuilder.append(tvExampleModel.getName());
                                    stringBuilder.append(" will be premiered on ");
                                    stringBuilder.append(last_season_air_date_string);
                                    current_season_tagline.setText(stringBuilder);
                                    current_season.setText(R.string.upcoming_season);
                                } else if (last_season_air_date.compareTo(today_date) < 0) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("Season ");
                                    stringBuilder.append(String.valueOf(tvExampleModel.getSeasons().get(size - 1).getSeasonNumber()));
                                    stringBuilder.append(" of ");
                                    stringBuilder.append(tvExampleModel.getName());
                                    stringBuilder.append(" premiered on ");
                                    stringBuilder.append(last_season_air_date_string);
                                    current_season_tagline.setText(stringBuilder);
                                    current_season.setText(R.string.current_season);
                                }
                            }

                        } catch (ParseException e) {
                            Toast.makeText(MovieView.this, "try catch error", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }





                    if (tvExampleModel.getSeasons().size() != 0)
                    {
                        season_number.setText(String.valueOf(tvExampleModel.getSeasons().get(size - 1).getSeasonNumber()));
                        current_season_episodes.setText(String.valueOf(tvExampleModel.getSeasons().get(size - 1).getEpisodeCount()));
                    }







                    season_list.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MovieView.this, CastViewActivity.class);
                            intent.putExtra("ID", tvExampleModel.getId());
                            intent.putExtra("SEASON_NUM", tvExampleModel.getSeasons().get(size - 1).getSeasonNumber());
                            intent.putExtra("EVENT", "SEASON_EPISODES");
                            startActivity(intent);

                        }
                    });

                }


                sb = new StringBuilder();
                String prefix = "";
                for (i = 0;i<tvExampleModel.getGenres().size();i++)
                {
                    sb.append(prefix);
                    prefix = ",";
                    sb.append(tvExampleModel.getGenres().get(i).getName());
                }
                genre1.setText(sb);



                String runtimestring = String.valueOf(tvExampleModel.getEpisodeRunTime());
                String runtimefinal = runtimestring.substring(1,runtimestring.length()-1);
                runtime.setText(runtimefinal + " min");
                homepage.setText(tvExampleModel.getHomepage());

                if (tvExampleModel.getBackdropPath()!= null)
                {
                    image_url = urlConstants.URL_Image + tvExampleModel.getBackdropPath();
                }
                else
                {
                    overview_layout.setBackgroundColor(Color.GRAY);
                    facts_layout.setBackgroundColor(Color.GRAY);
                }


                Target target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
                    {
                        backdrop_image.setImageBitmap(bitmap);
                        Palette.from(bitmap)
                                .generate(new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(Palette palette) {
                                        Palette.Swatch textSwatch = palette.getDominantSwatch();
                                        Palette.Swatch swatch = palette.getVibrantSwatch();

                                        if (textSwatch == null ) {
                                            overview_layout.setBackgroundColor(Color.GRAY);
                                            facts_layout.setBackgroundColor(Color.GRAY);
                                        }
                                        if (swatch == null)
                                        {
                                            return;
                                        }
                                        else
                                        {
                                            collapsingToolbarLayout.setExpandedTitleColor(swatch.getRgb());
                                        }

                                        assert textSwatch != null;
                                        overview_layout.setBackgroundColor(textSwatch.getRgb());
                                        overview.setTextColor(textSwatch.getTitleTextColor());
                                        overview_title.setTextColor(textSwatch.getTitleTextColor());
                                        facts_layout.setBackgroundColor(textSwatch.getRgb());
                                        facts.setTextColor(textSwatch.getTitleTextColor());
                                        status_title.setTextColor(textSwatch.getTitleTextColor());
                                        status.setTextColor(textSwatch.getTitleTextColor());
                                        release_date_title.setTextColor(textSwatch.getTitleTextColor());
                                        release_date.setTextColor(textSwatch.getTitleTextColor());
                                        genres_title.setTextColor(textSwatch.getTitleTextColor());
                                        runtime.setTextColor(textSwatch.getTitleTextColor());
                                        runtime_title.setTextColor(textSwatch.getTitleTextColor());
                                        homepage_title.setTextColor(textSwatch.getTitleTextColor());
                                        homepage.setTextColor(textSwatch.getTitleTextColor());
                                        genre1.setTextColor(textSwatch.getTitleTextColor());
                                        homepage.setLinkTextColor(textSwatch.getTitleTextColor());
                                        collapsingToolbarLayout.setContentScrimColor(textSwatch.getRgb());

                                        Activity activity = MovieView.this;
                                        Window window = activity.getWindow();


                                        int color =  mixColors(textSwatch.getRgb(),getResources().getColor(R.color.black));

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                            window.setStatusBarColor(color);
                                        }


                                    }
                                });
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable)
                    {
                        overview_layout.setBackgroundColor(Color.GRAY);
                        facts_layout.setBackgroundColor(Color.GRAY);
                        backdrop_image.setImageDrawable(errorDrawable);

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable)
                    {


                    }
                };

                Picasso.with(MovieView.this)
                        .load(image_url)
                        .placeholder(R.drawable.not_available)
                        .into(target);

                backdrop_image.setTag(target);

                //Making Arraylist for RecyclerView
                int i;
                for (i=0;i<tvExampleModel.getCredits().getCast().size();i++)
                {
                    Cast cast = new Cast();
                    cast.setName(tvExampleModel.getCredits().getCast().get(i).getName());
                    cast.setCharacter(tvExampleModel.getCredits().getCast().get(i).getCharacter());
                    cast.setId(tvExampleModel.getCredits().getCast().get(i).getId());
                    cast.setProfilePath(tvExampleModel.getCredits().getCast().get(i).getProfilePath());
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



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                pDialog.dismiss();
                boolean b = Utility.isNetworkAvailable(MovieView.this);

                if (!b)
                {
                    Snackbar.make(coordinator_layout_movie_view,"No Internet Connection",Snackbar.LENGTH_LONG).show();
                }

            }
        });

        AppSingleton.getInstance(this).addToRequestQueue(stringRequest, tag_json_obj);
    }



    private void prepareMovieData(String url)
    {

        String tag_json_obj = "json_obj_req1";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();


        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                pDialog.dismiss();
                //Setting text to the textviews
                movieDetailFull = gson.fromJson(response,MovieDetailFull.class);

                if (movieDetailFull.getRuntime() != null)
                {
                    String hours = String.valueOf(movieDetailFull.getRuntime() / 60);
                    String minutes = String.valueOf(movieDetailFull.getRuntime() % 60);
                    runtime.setText(hours + "h " + minutes  +"m");
                }
                else
                {
                    runtime_title.setVisibility(View.GONE);
                    runtime.setVisibility(View.GONE);
                }

                if (movieDetailFull.getTitle() != null)
                {
                    toolbar.setTitle(movieDetailFull.getTitle());
                }

                if (movieDetailFull.getVoteAverage() != null)
                {
                    ratings.setText(String.valueOf(movieDetailFull.getVoteAverage()));
                }

                if (movieDetailFull.getOverview() != null)
                {
                    overview.setText(movieDetailFull.getOverview());
                }
                else
                {
                    overview_layout.setVisibility(View.GONE);
                }

                if (movieDetailFull.getStatus() != null)
                {
                    status.setText(movieDetailFull.getStatus());
                }
                else
                {
                    status.setVisibility(View.GONE);
                    status_title.setVisibility(View.GONE);
                }
                if (movieDetailFull.getReleaseDate() != null)
                {
                    release_date.setText(movieDetailFull.getReleaseDate());
                }
                else
                {
                    release_date.setVisibility(View.GONE);
                    release_date_title.setVisibility(View.GONE);
                }
                if (movieDetailFull.getBudget() != null)
                {
                    budget.setText("$" + String.valueOf(movieDetailFull.getBudget()));
                }
                else
                {
                    budget.setVisibility(View.GONE);
                    budget_title.setVisibility(View.GONE);
                }
                if (movieDetailFull.getRevenue() != null)
                {
                    revenue.setText("$" + String.valueOf(movieDetailFull.getRevenue()));
                }
                else
                {
                    revenue.setVisibility(View.GONE);
                    revenue_title.setVisibility(View.GONE);
                }


                if (movieDetailFull.getHomepage() != null)
                {
                    homepage.setText(movieDetailFull.getHomepage());
                }
                else
                {
                    homepage.setVisibility(View.GONE);
                    homepage_title.setVisibility(View.GONE);
                }


                //Setting text to genres
                int value = movieDetailFull.getGenres().size();

                if (value != 0)
                {
                    sb = new StringBuilder();
                    String prefix = "";
                    for (i = 0;i<value;i++)
                    {
                        sb.append(prefix);
                        prefix = ",";
                        sb.append(movieDetailFull.getGenres().get(i).getName());
                    }
                    genre1.setText(sb);
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

                Target target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
                    {
                        backdrop_image.setImageBitmap(bitmap);

                        Palette.from(bitmap)
                                .generate(new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(Palette palette) {
                                        Palette.Swatch textSwatch = palette.getDominantSwatch();
                                        Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();


                                        if (textSwatch == null ) {
                                            //Toast.makeText(MovieView.this, "Null swatch :(", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        if (vibrantSwatch == null)
                                        {
                                            //nothing here as well
                                        }
                                        else
                                        {
                                            collapsingToolbarLayout.setExpandedTitleColor(vibrantSwatch.getRgb());

                                        }

                                        overview_layout.setBackgroundColor(textSwatch.getRgb());
                                        overview.setTextColor(textSwatch.getTitleTextColor());
                                        overview_title.setTextColor(textSwatch.getTitleTextColor());
                                        facts_layout.setBackgroundColor(textSwatch.getRgb());
                                        facts.setTextColor(textSwatch.getTitleTextColor());
                                        status_title.setTextColor(textSwatch.getTitleTextColor());
                                        status.setTextColor(textSwatch.getTitleTextColor());
                                        release_date_title.setTextColor(textSwatch.getTitleTextColor());
                                        release_date.setTextColor(textSwatch.getTitleTextColor());
                                        genres_title.setTextColor(textSwatch.getTitleTextColor());
                                        runtime.setTextColor(textSwatch.getTitleTextColor());
                                        runtime_title.setTextColor(textSwatch.getTitleTextColor());
                                        homepage_title.setTextColor(textSwatch.getTitleTextColor());
                                        homepage.setTextColor(textSwatch.getTitleTextColor());
                                        budget_title.setTextColor(textSwatch.getTitleTextColor());
                                        budget.setTextColor(textSwatch.getTitleTextColor());
                                        revenue.setTextColor(textSwatch.getTitleTextColor());
                                        revenue_title.setTextColor(textSwatch.getTitleTextColor());
                                        genre1.setTextColor(textSwatch.getTitleTextColor());
                                        homepage.setLinkTextColor(textSwatch.getTitleTextColor());
                                        collapsingToolbarLayout.setContentScrimColor(textSwatch.getRgb());

                                        int color =  mixColors(textSwatch.getRgb(),getResources().getColor(R.color.black));

                                        Activity activity = MovieView.this;
                                        Window window = activity.getWindow();
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                                            window.setStatusBarColor(color);
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable)
                    {
                        overview_layout.setBackgroundColor(Color.GRAY);
                        facts_layout.setBackgroundColor(Color.GRAY);
                        backdrop_image.setImageDrawable(errorDrawable);

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable)
                    {


                    }
                };

                Picasso.with(MovieView.this)
                        .load(url3)
                        .placeholder(R.drawable.not_available)
                        .into(target);

                backdrop_image.setTag(target);



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





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                boolean b = Utility.isNetworkAvailable(MovieView.this);

                if (!b)
                {
                    Snackbar.make(coordinator_layout_movie_view,"No Internet Connection",Snackbar.LENGTH_LONG).show();
                }

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


    public int mixColors(int col1, int col2) {
        int r1, g1, b1, r2, g2, b2;

        r1 = Color.red(col1);
        g1 = Color.green(col1);
        b1 = Color.blue(col1);

        r2 = Color.red(col2);
        g2 = Color.green(col2);
        b2 = Color.blue(col2);

        int r3 = (r1 + r2)/2;
        int g3 = (g1 + g2)/2;
        int b3 = (b1 + b2)/2;

        return Color.rgb(r3, g3, b3);
    }




}
