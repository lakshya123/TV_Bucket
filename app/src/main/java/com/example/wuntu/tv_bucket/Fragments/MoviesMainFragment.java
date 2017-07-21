package com.example.wuntu.tv_bucket.Fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesMainFragment extends Fragment
{


    private ArrayList<Result> movie = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    UrlConstants URLconstants = UrlConstants.getSingletonRef();
    private Gson gson;
    int page_number = 1;
    Popular_Movies_Model example;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_main, container, false);



        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new MoviesAdapter(movie,MoviesMainFragment.this);

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);



     recyclerView.addOnItemTouchListener(
                new MoviesAdapter_OnClickListener(getContext(), recyclerView ,new MoviesAdapter_OnClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        if (position == 20)
                        {

                        }
                        else
                        {
                            Intent intent = new Intent(getActivity(), MovieView.class);
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

        prepareOnlineData(page_number);

        return view;
    }

    public void prepareOnlineData(int page_number)
    {
        recyclerView.scrollToPosition(0);
        mAdapter.notifyDataSetChanged();
        String tag_json_obj = "json_obj_req";
        String page_String = String.valueOf(page_number);

        String url = URLconstants.URL_popular_movies+page_String;

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                example = gson.fromJson(response,Popular_Movies_Model.class);
                movie.clear();
                mAdapter.notifyDataSetChanged();

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
                    movie.add(i,result);
                }

                Result result = new Result();
                result.setId(0);
                result.setTitle("a");
                result.setOriginalTitle("b");
                result.setBackdropPath("aas");
                result.setReleaseDate("we");
                result.setVoteAverage(1.2);
                result.setPage(example.getPage());
                result.setTotal_pages(example.getTotalPages());
                movie.add(20,result);
                mAdapter.notifyDataSetChanged();

                pDialog.hide();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
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
