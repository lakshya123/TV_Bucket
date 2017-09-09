package com.example.wuntu.tv_bucket.Fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wuntu.tv_bucket.Adapters.FullCastListAdapter;
import com.example.wuntu.tv_bucket.Adapters.MoviesAdapter_OnClickListener;
import com.example.wuntu.tv_bucket.Models.Cast;
import com.example.wuntu.tv_bucket.R;
import com.example.wuntu.tv_bucket.Utils.Utility;

import java.util.ArrayList;

public class CastViewListFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Cast> castArrayList = new ArrayList<>();
    TextView no_results;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_cast_view_list, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Full Cast List");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        no_results = (TextView) view.findViewById(R.id.no_results);

        castArrayList = getArguments().getParcelableArrayList("FULL CREW LIST");


        assert castArrayList != null;
        if (castArrayList.size()  == 0)
        {
            no_results.setVisibility(View.VISIBLE);
            //Toast.makeText(getActivity(), "No Results", Toast.LENGTH_SHORT).show();
        }
        else no_results.setVisibility(View.GONE);

//        Toast.makeText(getActivity(), castArrayList.get(0).getCharacter() +"", Toast.LENGTH_SHORT).show();


        boolean b = Utility.isNetworkAvailable(getActivity());

        if (!b)
        {
            Snackbar.make(getActivity().findViewById(R.id.relative_layout_cast_view),"No Internet Connection",Snackbar.LENGTH_LONG).show();
        }

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
                        CastViewFragment castViewFragment = new CastViewFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("ID",castArrayList.get(position).getId());
                        castViewFragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.container,castViewFragment).addToBackStack(null).commit();
                    }

                    @Override public void onLongItemClick(View view, int position)
                    {


                    }
                })
        );


        return view;
    }

}
