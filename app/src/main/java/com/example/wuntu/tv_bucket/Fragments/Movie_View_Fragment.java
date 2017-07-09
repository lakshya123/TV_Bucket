package com.example.wuntu.tv_bucket.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wuntu.tv_bucket.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Movie_View_Fragment extends Fragment {


    public Movie_View_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie__view, container, false);
    }

}
