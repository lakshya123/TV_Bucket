package com.example.wuntu.tv_bucket.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.wuntu.tv_bucket.Adapters.CastDetailAdapter;
import com.example.wuntu.tv_bucket.Adapters.EpisodeAdapter;
import com.example.wuntu.tv_bucket.Adapters.MoviesAdapter_OnClickListener;
import com.example.wuntu.tv_bucket.CastViewActivity;
import com.example.wuntu.tv_bucket.Models.Cast;
import com.example.wuntu.tv_bucket.Models.Episode;
import com.example.wuntu.tv_bucket.Models.SeasonDetailGettingModel;
import com.example.wuntu.tv_bucket.MovieView;
import com.example.wuntu.tv_bucket.R;
import com.example.wuntu.tv_bucket.Utils.AppSingleton;
import com.example.wuntu.tv_bucket.Utils.UrlConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeasonEpisodesFragment extends Fragment {

    Integer id1;
    Integer season_num1;
    Gson gson;
    UrlConstants urlConstants = UrlConstants.getSingletonRef();
    RecyclerView recycler_view_season_view;
    CastDetailAdapter castDetailAdapter;
    ArrayList<Cast> castArrayList;
    ArrayList<Cast> subCastArrayList;
    ArrayList<Episode> episodeArrayList;
    ArrayList<Cast> episodeGuestList;
    SeasonDetailGettingModel seasonDetailGettingModel;
    EpisodeAdapter episodeAdapter;
    RecyclerView recycler_view_episodes_view;

    TextView season_num,season_overview,episode_total;
    int i;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_season_episodes, container, false);

        recycler_view_season_view = (RecyclerView) view.findViewById(R.id.recycler_view_season_view);
        recycler_view_episodes_view = (RecyclerView) view.findViewById(R.id.recycler_view_episodes_view);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recycler_view_episodes_view);

        season_num = (TextView) view.findViewById(R.id.season_num);
        season_overview = (TextView) view.findViewById(R.id.season_overview);

        episode_total = (TextView) view.findViewById(R.id.episode_total);


        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("TV Bucket");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        id1 = getArguments().getInt("ID");
        season_num1 = getArguments().getInt("SEASON_NUM");


        castArrayList = new ArrayList<>();
        subCastArrayList = new ArrayList<>();
        episodeArrayList = new ArrayList<>();
        episodeGuestList = new ArrayList<>();

        seasonDetailGettingModel = new SeasonDetailGettingModel();

        String id = String.valueOf(id1);
        String season_number = String.valueOf(season_num1);



        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        String url = urlConstants.TV_Episodes_1st_URL + id  + urlConstants.TV_Episodes_2nd_URL + season_number + urlConstants.TV_Episodes_3rd_URL;




        castDetailAdapter = new CastDetailAdapter(castArrayList,subCastArrayList);

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        recycler_view_season_view.setLayoutManager(mLayoutManager);
        recycler_view_season_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view_season_view.setAdapter(castDetailAdapter);




        episodeAdapter = new EpisodeAdapter(episodeArrayList);

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        recycler_view_episodes_view.setLayoutManager(layoutManager);
        recycler_view_episodes_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view_episodes_view.setAdapter(episodeAdapter);


        recycler_view_season_view.addOnItemTouchListener(
                new MoviesAdapter_OnClickListener(getActivity(), recycler_view_season_view ,new MoviesAdapter_OnClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        if (position == subCastArrayList.size() - 1)
                        {
                            // Done things in Adapter for this

                        }
                        else
                        {
                            Intent intent = new Intent(getActivity(),CastViewActivity.class);
                            intent.putExtra("ID",subCastArrayList.get(position).getId());
                            intent.putExtra("EVENT","TOUCH EVENT");
                            startActivity(intent);
                        }



                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever

                    }
                })
        );

        preparedata(url);


        return view;
    }

    private void preparedata(String url)
    {
        String tag = "TAG";
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                seasonDetailGettingModel = gson.fromJson(response,SeasonDetailGettingModel.class);

                episode_total.setText(String.valueOf(seasonDetailGettingModel.getEpisodes().size()));


                if (seasonDetailGettingModel.getEpisodes().size() > 0)
                {
                    for (i = 0;i<seasonDetailGettingModel.getEpisodes().size();i++)
                    {
                        Episode episode = new Episode();
                        episode.setStillPath(seasonDetailGettingModel.getEpisodes().get(i).getStillPath());
                        episode.setEpisodeNumber(seasonDetailGettingModel.getEpisodes().get(i).getEpisodeNumber());
                        episode.setName(seasonDetailGettingModel.getEpisodes().get(i).getName());
                        episode.setAirDate(seasonDetailGettingModel.getEpisodes().get(i).getAirDate());
                        episode.setOverview(seasonDetailGettingModel.getEpisodes().get(i).getOverview());
                        episode.setVoteAverage(seasonDetailGettingModel.getEpisodes().get(i).getVoteAverage());

                        int j;
                        for (j= 0;j<seasonDetailGettingModel.getEpisodes().get(i).getGuestStars().size();j++)
                        {
                            Cast cast = new Cast();
                            cast.setName(seasonDetailGettingModel.getEpisodes().get(i).getGuestStars().get(j).getName());
                            cast.setCharacter(seasonDetailGettingModel.getEpisodes().get(i).getGuestStars().get(j).getCharacter());
                            cast.setId(seasonDetailGettingModel.getEpisodes().get(i).getGuestStars().get(j).getId());
                            cast.setProfilePath(seasonDetailGettingModel.getEpisodes().get(i).getGuestStars().get(j).getProfilePath());
                            episodeGuestList.add(j,cast);
                            episodeAdapter.notifyDataSetChanged();
                        }
                        episode.setGuestStars(episodeGuestList);
                        episodeArrayList.add(i,episode);
                        episodeAdapter.notifyDataSetChanged();
                    }
                }


                if (seasonDetailGettingModel.getName() != null)
                {
                    season_num.setText(String.valueOf(seasonDetailGettingModel.getName()));
                }

                if (seasonDetailGettingModel.getOverview() != null)
                {
                    season_overview.setText(seasonDetailGettingModel.getOverview());
                }


                for (i = 0;i<seasonDetailGettingModel.getCredits().getCast().size();i++)
                {
                    Cast cast = new Cast();
                    cast.setName(seasonDetailGettingModel.getCredits().getCast().get(i).getName());
                    cast.setCharacter(seasonDetailGettingModel.getCredits().getCast().get(i).getCharacter());
                    cast.setId(seasonDetailGettingModel.getCredits().getCast().get(i).getId());
                    cast.setProfilePath(seasonDetailGettingModel.getCredits().getCast().get(i).getProfilePath());
                    castArrayList.add(i,cast);
                    castDetailAdapter.notifyDataSetChanged();
                }

                castDetailAdapter.notifyDataSetChanged();

                subCastArrayList.clear();

                if (castArrayList.size() > 8)
                {
                    subCastArrayList.addAll(castArrayList.subList(0,8));
                    castDetailAdapter.notifyDataSetChanged();
                }
                else
                {
                    subCastArrayList.addAll(castArrayList);
                    castDetailAdapter.notifyDataSetChanged();
                }


                progressDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        });

        AppSingleton.getInstance(getContext()).addToRequestQueue(stringRequest,tag);
    }

}
