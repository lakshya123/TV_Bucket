package com.example.wuntu.tv_bucket.Fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.wuntu.tv_bucket.Adapters.MoviesAdapter;
import com.example.wuntu.tv_bucket.Adapters.MoviesAdapter_OnClickListener;
import com.example.wuntu.tv_bucket.Adapters.SimpleDividerItemDecoration;
import com.example.wuntu.tv_bucket.Models.Popular_Movies_Model;
import com.example.wuntu.tv_bucket.Models.Result;

import com.example.wuntu.tv_bucket.MovieView;
import com.example.wuntu.tv_bucket.R;
import com.example.wuntu.tv_bucket.Utils.AppSingleton;
import com.example.wuntu.tv_bucket.Utils.UrlConstants;
import com.example.wuntu.tv_bucket.Utils.Utility;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;
import static android.view.View.GONE;
import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesMainFragment extends Fragment
{
    public RelativeLayout relativeLayout;
    private ArrayList<Result> movie = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    public UrlConstants URLconstants = UrlConstants.getSingletonRef();
    private Gson gson;
    Integer page_number = 1;
    Popular_Movies_Model example;
    String url = URLconstants.URL_now_playing_movies;
    String url1 = " ";
    LinearLayoutManager mLayoutManager;
    public static String static_url ;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_main, container, false);





        relativeLayout = (RelativeLayout) view.findViewById(R.id.fragment_movie_main);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new MoviesAdapter(movie,MoviesMainFragment.this);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    //Log.d("STATUS","LOADING TRUE BUT NOT IF");
                    if (totalItemCount > previousTotal) {
                        //Log.d("STATUS","LOADING TRUE");
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached

                    //Log.d("STATUS", "end called");

                    // Do something
                    page_number = page_number + 1;
                    prepareOnlineData(static_url,page_number);

                    loading = true;
                }
            }
        });




        recyclerView.addOnItemTouchListener(
                new MoviesAdapter_OnClickListener(getContext(), recyclerView ,new MoviesAdapter_OnClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        Intent intent = new Intent(getActivity(), MovieView.class);
                        intent.putExtra("ID",movie.get(position).getId().toString());
                        intent.putExtra("VIEW","MOVIE");
                        startActivity(intent);




                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever

                    }
                })
        );



        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();



        prepareOnlineData(url,page_number);

        return view;
    }


    public void prepareOnlineData(final String url, Integer page_number)
    {


        if (url.equals(static_url))
        {
            Log.d("CHANGE","Same URL");
        }
        else
        {
            previousTotal = 0;
            movie.clear();
        }
        static_url = url;

        String tag_json_obj = "json_obj_req";
        String page_String = String.valueOf(page_number);

        url1 = url + page_String;
        mAdapter.notifyDataSetChanged();

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url1, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                example = gson.fromJson(response,Popular_Movies_Model.class);
//                movie.clear();
                mAdapter.notifyDataSetChanged();
               // Toast.makeText(getActivity(), "movie arraylist size = " + movie.size(), Toast.LENGTH_SHORT).show();
                int j = movie.size();

                for(int i = 0;i<example.getResults().size();i++)
                {
                    Result result = new Result();
                    result.setId(example.getResults().get(i).getId());
                    result.setTitle(example.getResults().get(i).getTitle());
                    result.setOriginalTitle(example.getResults().get(i).getOriginalTitle());
                    result.setBackdropPath(example.getResults().get(i).getBackdropPath());
                    result.setReleaseDate(example.getResults().get(i).getReleaseDate());
                    result.setVoteAverage(example.getResults().get(i).getVoteAverage());
                    result.setPage(example.getPage());
                    result.setTotal_pages(example.getTotalPages());
                    result.setURL(url);

                    movie.add(j,result);
                    j= j+ 1;
                    mAdapter.notifyDataSetChanged();
                }


                pDialog.hide();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
                boolean b = Utility.isNetworkAvailable(getContext());
                if (!b)
                {
                    Snackbar.make(getActivity().findViewById(R.id.coordinator_layout),"No Internet Connection",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        AppSingleton.getInstance(getContext()).addToRequestQueue(stringRequest, tag_json_obj);
    }



}
