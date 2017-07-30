package com.example.wuntu.tv_bucket;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.wuntu.tv_bucket.Adapters.YoutubeViewAdapter;
import com.example.wuntu.tv_bucket.Models.YoutubeLinksGettingModel;
import com.example.wuntu.tv_bucket.Models.YoutubelinksFinalModel;
import com.example.wuntu.tv_bucket.Utils.AppSingleton;
import com.example.wuntu.tv_bucket.Utils.UrlConstants;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class YoutubeActivity extends YouTubeBaseActivity {

    RecyclerView youtube_recyclerview;
    YoutubeLinksGettingModel youtubeLinksGettingModel;
    YoutubelinksFinalModel youtubelinksFinalModel;
    ArrayList<YoutubelinksFinalModel> youtubelinks;
    YoutubeViewAdapter youtubeViewAdapter;
    android.widget.Toolbar toolbar;

    UrlConstants URLconstants = UrlConstants.getSingletonRef();
    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);


        String ID = getIntent().getStringExtra("ID");

        youtube_recyclerview = (RecyclerView) findViewById(R.id.youtube_recyclerview);

        youtubelinks = new ArrayList<>();

        toolbar = (android.widget.Toolbar) findViewById(R.id.toolbar);

        setActionBar(toolbar);

        getActionBar().setTitle("Videos");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(true);




        String url = URLconstants.Videos_1st_URL + ID + URLconstants.Videos_2nd_URL;


        youtubeViewAdapter = new YoutubeViewAdapter(this, youtubelinks);



        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        youtube_recyclerview.setLayoutManager(layoutManager);
        youtube_recyclerview.setAdapter(youtubeViewAdapter);


        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        getYoutubeLinks(url);


        // youTubePlayerView.initialize(DEVELOPER_KEY, this);


    }

    public void getYoutubeLinks(String url) {
        String tag_json_obj = "json_obj_req";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                youtubeLinksGettingModel = gson.fromJson(response, YoutubeLinksGettingModel.class);
                int i = 0;
                for (i = 0; i < youtubeLinksGettingModel.getResults().size(); i++) {
                    youtubelinksFinalModel = new YoutubelinksFinalModel();
                    youtubelinksFinalModel.setName(youtubeLinksGettingModel.getResults().get(i).getName());
                    youtubelinksFinalModel.setKey(youtubeLinksGettingModel.getResults().get(i).getKey());

                    youtubelinks.add(i, youtubelinksFinalModel);
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
