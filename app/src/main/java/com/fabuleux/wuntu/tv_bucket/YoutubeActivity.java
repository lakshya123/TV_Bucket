package com.fabuleux.wuntu.tv_bucket;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.fabuleux.wuntu.tv_bucket.Adapters.YoutubeViewAdapter;
import com.fabuleux.wuntu.tv_bucket.Models.YoutubeLinksGettingModel;
import com.fabuleux.wuntu.tv_bucket.Models.YoutubelinksFinalModel;
import com.fabuleux.wuntu.tv_bucket.Utils.AppSingleton;
import com.fabuleux.wuntu.tv_bucket.Utils.UrlConstants;
import com.fabuleux.wuntu.tv_bucket.Utils.Utility;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class YoutubeActivity extends YouTubeBaseActivity {

    RecyclerView youtube_recyclerview;
    YoutubeLinksGettingModel youtubeLinksGettingModel;
    YoutubelinksFinalModel youtubelinksFinalModel;
    ArrayList<YoutubelinksFinalModel> youtubeLinks;
    YoutubeViewAdapter youtubeViewAdapter;
    Toolbar toolbar;
    LinearLayout linearLayout;
    TextView no_videos;

    UrlConstants URLconstants = UrlConstants.getSingletonRef();
    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);


        linearLayout = (LinearLayout) findViewById(R.id.activity_youtube);
        no_videos = (TextView) findViewById(R.id.no_videos);


        String ID = getIntent().getStringExtra("ID");
        String view = getIntent().getStringExtra("VIEW");
        String Season_number = getIntent().getStringExtra("SEASON_NUMBER");
        String Episode_number = getIntent().getStringExtra("EPISODE_NUMBER");

        youtube_recyclerview = (RecyclerView) findViewById(R.id.youtube_recyclerview);

        youtubeLinks = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setActionBar(toolbar);
        }

        if (getActionBar() != null)
        {
            getActionBar().setTitle("Videos");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        }
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(true);




        String movie_url = URLconstants.Movies_Videos_1st_URL + ID + URLconstants.Movies_Videos_2nd_URL;

        String tv_url = URLconstants.Tv_Videos_1st_URL + ID + URLconstants.Tv_Videos_2nd_URL;

        String seasons_url = URLconstants.Season_Videos_1st_URL + ID + URLconstants.Season_Videos_2nd_URL + Season_number + URLconstants.Season_Videos_3rd_URL;

        String episodes_url = URLconstants.Episodes_Videos_1st_URL + ID + URLconstants.Episodes_Videos_2nd_URL + Season_number + URLconstants.Episodes_Videos_3rd_URL + Episode_number + URLconstants.Episodes_Videos_4th_URL;


        youtubeViewAdapter = new YoutubeViewAdapter(this, youtubeLinks);



        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        youtube_recyclerview.setLayoutManager(layoutManager);
        youtube_recyclerview.setAdapter(youtubeViewAdapter);


        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        switch (view) {
            case "MOVIE":
                getYoutubeLinks(movie_url);
                break;
            case "TV":
                getYoutubeLinks(tv_url);
                break;
            case "SEASON":
                getYoutubeLinks(seasons_url);
                break;
            case "EPISODE":
                getYoutubeLinks(episodes_url);
                break;
        }

    }

    public void getYoutubeLinks(String url) {

        boolean b = Utility.isNetworkAvailable(YoutubeActivity.this);

        if (!b)
        {
            Snackbar.make(linearLayout,"No Internet Connection",Snackbar.LENGTH_LONG).show();
        }
        String tag_json_obj = "json_obj_req";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                youtubeLinksGettingModel = gson.fromJson(response, YoutubeLinksGettingModel.class);
                int i ;
                for (i = 0; i < youtubeLinksGettingModel.getResults().size(); i++) {
                    youtubelinksFinalModel = new YoutubelinksFinalModel();
                    youtubelinksFinalModel.setName(youtubeLinksGettingModel.getResults().get(i).getName());
                    youtubelinksFinalModel.setKey(youtubeLinksGettingModel.getResults().get(i).getKey());

                    youtubeLinks.add(i, youtubelinksFinalModel);
                }

                if (youtubeLinksGettingModel.getResults().size() == 0)
                {
                    no_videos.setVisibility(View.VISIBLE);
                }

                youtubeViewAdapter.notifyDataSetChanged();


                pDialog.hide();

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.hide();
            }
        });

        AppSingleton.getInstance(this).addToRequestQueue(stringRequest, tag_json_obj);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
