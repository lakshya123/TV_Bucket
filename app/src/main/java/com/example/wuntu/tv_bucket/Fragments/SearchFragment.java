package com.example.wuntu.tv_bucket.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.wuntu.tv_bucket.Adapters.MoviesAdapter_OnClickListener;
import com.example.wuntu.tv_bucket.Adapters.SearchAdapter;
import com.example.wuntu.tv_bucket.CastViewActivity;
import com.example.wuntu.tv_bucket.Models.MultiSearchModel;
import com.example.wuntu.tv_bucket.Models.MultiSearchResultModel;
import com.example.wuntu.tv_bucket.Models.Popular_Movies_Model;
import com.example.wuntu.tv_bucket.MovieView;
import com.example.wuntu.tv_bucket.R;
import com.example.wuntu.tv_bucket.Utils.AppSingleton;
import com.example.wuntu.tv_bucket.Utils.UrlConstants;
import com.example.wuntu.tv_bucket.Utils.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    RecyclerView recyclerView;
    String query;
    TextView search_text;
    UrlConstants urlConstants = UrlConstants.getSingletonRef();
    ArrayList<MultiSearchResultModel> searchModelArrayList;
    SearchAdapter searchAdapter;
    private Gson gson;
    MultiSearchModel multiSearchModel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_search, container, false);
        search_text = (TextView) view.findViewById(R.id.search_text);

        recyclerView = (RecyclerView) view.findViewById(R.id.search_recyler_view);
        searchModelArrayList = new ArrayList<>();

        searchAdapter = new SearchAdapter(searchModelArrayList,SearchFragment.this);

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(searchAdapter);

        if(getArguments() != null)
        {
            query = getArguments().getString("QUERY");
        }
        else query = "";
        //Toast.makeText(getActivity(), query + "", Toast.LENGTH_SHORT).show();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        if (query.length() > 0)
        {
            search_text.setVisibility(View.GONE);
        }

        if (query.length()>2)
        {
            preparedata(query);
        }



        recyclerView.addOnItemTouchListener(
                new MoviesAdapter_OnClickListener(getContext(), recyclerView ,new MoviesAdapter_OnClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        if (searchModelArrayList.get(position).getMediaType().equalsIgnoreCase("person"))
                        {
                            Intent intent = new Intent(getActivity(), CastViewActivity.class);
                            intent.putExtra("EVENT","TOUCH EVENT");
                            intent.putExtra("ID",searchModelArrayList.get(position).getId());
                            startActivity(intent);
                        }
                        else if (searchModelArrayList.get(position).getMediaType().equalsIgnoreCase("movie"))
                        {
                            Intent intent = new Intent(getActivity(), MovieView.class);
                            String ID = String.valueOf(searchModelArrayList.get(position).getId());
                            intent.putExtra("VIEW","MOVIE");
                            intent.putExtra("ID",ID);
                            startActivity(intent);
                        }
                        else if (searchModelArrayList.get(position).getMediaType().equalsIgnoreCase("tv"))
                        {
                            Intent intent = new Intent(getActivity(), MovieView.class);
                            String ID = String.valueOf(searchModelArrayList.get(position).getId());
                            intent.putExtra("VIEW","TV");
                            intent.putExtra("ID",ID);
                            startActivity(intent);
                        }
                    }

                    @Override public void onLongItemClick(View view, int position)
                    {


                    }
                })
        );

        return view;
    }

    public void preparedata(String query)
    {
        String tag_json_obj = "json_obj_req";
        boolean b = Utility.isNetworkAvailable(getContext());


        if (!b)
        {
            Snackbar.make(getActivity().findViewById(R.id.coordinator_layout),"No Internet Connection",Snackbar.LENGTH_LONG).show();
        }
        String url = urlConstants.URL_multi_search + query;



        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                multiSearchModel = gson.fromJson(response,MultiSearchModel.class);

                int i;
                for (i=0;i<multiSearchModel.getResults().size();i++)
                {
                    MultiSearchResultModel multiSearchResultModel = new MultiSearchResultModel();
                    multiSearchResultModel.setTitle(multiSearchModel.getResults().get(i).getTitle());
                    multiSearchResultModel.setName(multiSearchModel.getResults().get(i).getName());
                    multiSearchResultModel.setId(multiSearchModel.getResults().get(i).getId());
                    multiSearchResultModel.setOriginalTitle(multiSearchModel.getResults().get(i).getOriginalTitle());
                    multiSearchResultModel.setPosterPath(multiSearchModel.getResults().get(i).getPosterPath());
                    multiSearchResultModel.setProfilePath(multiSearchModel.getResults().get(i).getProfilePath());
                    multiSearchResultModel.setMediaType(multiSearchModel.getResults().get(i).getMediaType());

                    searchModelArrayList.add(i,multiSearchResultModel);
                }
                if (multiSearchModel.getTotalResults() == 0)
                {
                    search_text.setText("No Results");
                    search_text.setVisibility(View.VISIBLE);
                }
                searchAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        });


        AppSingleton.getInstance(getContext()).addToRequestQueue(stringRequest, tag_json_obj);
    }



}
