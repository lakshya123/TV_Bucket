package com.fabuleux.wuntu.tv_bucket.Fragments;


import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fabuleux.wuntu.tv_bucket.Adapters.MoviesAdapter_OnClickListener;
import com.fabuleux.wuntu.tv_bucket.Adapters.SearchAdapter;
import com.fabuleux.wuntu.tv_bucket.CastViewActivity;
import com.fabuleux.wuntu.tv_bucket.Models.MultiSearchModel;
import com.fabuleux.wuntu.tv_bucket.Models.MultiSearchResultModel;
import com.fabuleux.wuntu.tv_bucket.MovieView;
import com.fabuleux.wuntu.tv_bucket.R;
import com.fabuleux.wuntu.tv_bucket.Utils.AppSingleton;
import com.fabuleux.wuntu.tv_bucket.Utils.UrlConstants;
import com.fabuleux.wuntu.tv_bucket.Utils.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    RecyclerView recyclerView;
    String query= "";
    TextView search_text,no_result_textview;
    UrlConstants urlConstants = UrlConstants.getSingletonRef();
    ArrayList<MultiSearchResultModel> searchModelArrayList;
    SearchAdapter searchAdapter;
    private Gson gson;
    MultiSearchModel multiSearchModel;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    int page_number = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_search, container, false);
        search_text = (TextView) view.findViewById(R.id.search_text);

        no_result_textview = (TextView) view.findViewById(R.id.no_result_textview);


        recyclerView = (RecyclerView) view.findViewById(R.id.search_recyler_view);
        searchModelArrayList = new ArrayList<>();

        searchAdapter = new SearchAdapter(searchModelArrayList,SearchFragment.this);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(searchAdapter);

        if(getArguments() != null)
        {
            query = getArguments().getString("QUERY");
            //Toast.makeText(getActivity(), "Not Null Arguments query " + query.length(), Toast.LENGTH_SHORT).show();
        }


        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        if (query.length() == 0)
        {
            search_text.setVisibility(View.VISIBLE);
            no_result_textview.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }

        if (query.length() >= 1)
        {
            no_result_textview.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            search_text.setVisibility(View.GONE);
        }

        if (query.length()>1)
        {
            preparedata(query,page_number);
        }


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
                    preparedata(query,page_number);

                    loading = true;
                }
            }
        });



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

    public void preparedata(String query,int page_number)
    {
        String tag_json_obj = "json_obj_req";
        boolean b = Utility.isNetworkAvailable(getContext());

       /* final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();*/


        if (!b)
        {
            Snackbar.make(getActivity().findViewById(R.id.coordinator_layout),"No Internet Connection",Snackbar.LENGTH_LONG).show();
        }
        String pg_no = String.valueOf(page_number);
        String page_url = urlConstants.URL_multi_search_2 + pg_no;
        String url = urlConstants.URL_multi_search + query + page_url;



        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                multiSearchModel = gson.fromJson(response,MultiSearchModel.class);

                int i;
                int j = searchModelArrayList.size();
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

                    searchModelArrayList.add(j,multiSearchResultModel);
                    j = j+ 1;
                }
                if (multiSearchModel.getTotalResults() == 0)
                {
                    no_result_textview.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    search_text.setVisibility(View.GONE);
                }
                searchAdapter.notifyDataSetChanged();
               // progressDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                //progressDialog.hide();
            }
        });


        AppSingleton.getInstance(getContext()).addToRequestQueue(stringRequest, tag_json_obj);
    }



}
