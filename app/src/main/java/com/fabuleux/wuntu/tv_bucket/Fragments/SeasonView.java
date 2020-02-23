package com.fabuleux.wuntu.tv_bucket.Fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.fabuleux.wuntu.tv_bucket.Adapters.MoviesAdapter_OnClickListener;
import com.fabuleux.wuntu.tv_bucket.Adapters.SeasonListAdapter;
import com.fabuleux.wuntu.tv_bucket.Models.TvSeasons;
import com.fabuleux.wuntu.tv_bucket.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeasonView extends Fragment {

    ArrayList<TvSeasons> seasonsArrayList;
    SeasonListAdapter seasonListAdapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_season_view, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_season);


        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Full Season List");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        seasonsArrayList = new ArrayList<>();


        if (getArguments().getParcelableArrayList("SEASON_LIST") != null)
        {
            seasonsArrayList = getArguments().getParcelableArrayList("SEASON_LIST");
        }
        else
        {
            Toast.makeText(getActivity(), "No Result", Toast.LENGTH_SHORT).show();
        }

        seasonListAdapter = new SeasonListAdapter(seasonsArrayList);
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(seasonListAdapter);

        recyclerView.addOnItemTouchListener(
                new MoviesAdapter_OnClickListener(getActivity(), recyclerView ,new MoviesAdapter_OnClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        SeasonEpisodesFragment seasonEpisodesFragment = new SeasonEpisodesFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("ID",seasonsArrayList.get(position).getId());
                        bundle.putInt("SEASON_NUM",seasonsArrayList.get(position).getSeasonNumber());
                        seasonEpisodesFragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.container,seasonEpisodesFragment).addToBackStack(null).commit();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever

                    }
                })
        );







        return view;
    }

}
