package com.example.wuntu.tv_bucket.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuntu.tv_bucket.R;
import com.example.wuntu.tv_bucket.Utils.UrlConstants;

public class SearchFragment extends Fragment {

    RecyclerView recyclerView;
    String query;
    TextView search_text;
    UrlConstants urlConstants = UrlConstants.getSingletonRef();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_search, container, false);
        search_text = (TextView) view.findViewById(R.id.search_text);
       // Toast.makeText(getActivity(), "New Fragment", Toast.LENGTH_SHORT).show();

        if(getArguments() != null)
        {
            query = getArguments().getString("QUERY");
        }
        else query = "";
        //Toast.makeText(getActivity(), query + "", Toast.LENGTH_SHORT).show();

        if (query.length()>0)
        {
            search_text.setVisibility(View.GONE);
            preparedata(query);
        }



        recyclerView = (RecyclerView) view.findViewById(R.id.search_recyler_view);

        return view;
    }

    private void preparedata(String query)
    {
        String url = urlConstants.URL_multi_search + query;
        Toast.makeText(getActivity(), url + "", Toast.LENGTH_SHORT).show();
    }

}
