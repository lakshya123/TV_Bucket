package com.example.wuntu.tv_bucket.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wuntu.tv_bucket.Adapters.FullCastListAdapter;
import com.example.wuntu.tv_bucket.Adapters.MoviesAdapter_OnClickListener;
import com.example.wuntu.tv_bucket.CastViewActivity;
import com.example.wuntu.tv_bucket.Models.Cast;
import com.example.wuntu.tv_bucket.MovieView;
import com.example.wuntu.tv_bucket.R;

import java.util.ArrayList;

public class CastViewListFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Cast> castArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_cast_view_list, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Full Cast List");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        castArrayList = getArguments().getParcelableArrayList("FULL CREW LIST");

        FullCastListAdapter fullCastListAdapter = new FullCastListAdapter(castArrayList);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(fullCastListAdapter);

        recyclerView.addOnItemTouchListener(
                new MoviesAdapter_OnClickListener(getContext(), recyclerView ,new MoviesAdapter_OnClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        ((CastViewActivity)getActivity()).setViewtoCastView(castArrayList.get(position).getId());
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever

                    }
                })
        );


        return view;
    }

}
