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
import com.example.wuntu.tv_bucket.Adapters.Tv_List_Adapter;
import com.example.wuntu.tv_bucket.Models.Popular_Movies_Model;
import com.example.wuntu.tv_bucket.Models.Result;

import com.example.wuntu.tv_bucket.Models.TVListGettingModel;
import com.example.wuntu.tv_bucket.Models.TVListResultModel;
import com.example.wuntu.tv_bucket.MovieView;
import com.example.wuntu.tv_bucket.R;
import com.example.wuntu.tv_bucket.Utils.AppSingleton;
import com.example.wuntu.tv_bucket.Utils.UrlConstants;
import com.example.wuntu.tv_bucket.Utils.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvMainFragment extends Fragment
{

    public RelativeLayout relativeLayout;
    private ArrayList<TVListResultModel> tv_list = new ArrayList<>();
    private RecyclerView recyclerView;
    private Tv_List_Adapter mAdapter;
    public UrlConstants URLconstants = UrlConstants.getSingletonRef();
    private Gson gson;
    int page_number = 1;
    TVListGettingModel gettingModel;
    String url = URLconstants.URL_tv_on_air;
    String url1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tv_main, container, false);


        relativeLayout = (RelativeLayout) view.findViewById(R.id.fragment_tv_main);
        recyclerView = (RecyclerView) view.findViewById(R.id.tv_recycler_view);
        mAdapter = new Tv_List_Adapter(tv_list,TvMainFragment.this,url);

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);



        recyclerView.addOnItemTouchListener(
                new MoviesAdapter_OnClickListener(getContext(), recyclerView ,new MoviesAdapter_OnClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        if (position == tv_list.size() - 1)
                        {

                        }
                        else
                        {
                            Intent intent = new Intent(getActivity(), MovieView.class);
                            intent.putExtra("ID",tv_list.get(position).getId().toString());
                            intent.putExtra("VIEW","TV");
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



        prepareOnlineData(url,page_number);

        return view;
    }



    public void prepareOnlineData(String url,int page_number)
    {

        boolean b = Utility.isNetworkAvailable(getContext());

        if (!b)
        {
            Snackbar.make(getActivity().findViewById(R.id.coordinator_layout),"No Internet Connection",Snackbar.LENGTH_LONG).show();
        }
        recyclerView.scrollToPosition(0);
        mAdapter.notifyDataSetChanged();
        String tag_json_obj = "json_obj_req";
        String page_String = String.valueOf(page_number);

        url1 = url + page_String;
        mAdapter.notifyDataSetChanged();

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url1, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                gettingModel = gson.fromJson(response,TVListGettingModel.class);
                tv_list.clear();
                mAdapter.notifyDataSetChanged();

                for(int i = 0;i<gettingModel.getResults().size();i++)
                {
                    TVListResultModel result = new TVListResultModel();
                    result.setId(gettingModel.getResults().get(i).getId());
                    result.setName(gettingModel.getResults().get(i).getName());
                    result.setOriginalName(gettingModel.getResults().get(i).getOriginalName());
                    result.setBackdropPath(gettingModel.getResults().get(i).getBackdropPath());
                    result.setFirstAirDate(gettingModel.getResults().get(i).getFirstAirDate());
                    result.setPage(gettingModel.getPage());
                    result.setTotalPages(gettingModel.getTotalPages());
                    tv_list.add(i,result);
                }

                TVListResultModel result = new TVListResultModel();
                result.setId(0);
                result.setName("a");
                result.setOriginalName("b");
                result.setBackdropPath("aas");
                result.setFirstAirDate("we");
                result.setPage(gettingModel.getPage());
                result.setTotalPages(gettingModel.getTotalPages());
                tv_list.add(gettingModel.getResults().size(),result);
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
