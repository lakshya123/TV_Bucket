package com.fabuleux.wuntu.tv_bucket.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fabuleux.wuntu.tv_bucket.Models.Result;
import com.fabuleux.wuntu.tv_bucket.R;
import com.fabuleux.wuntu.tv_bucket.Utils.UrlConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Wuntu on 05-07-2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Result movie;
    private UrlConstants urlConstants = UrlConstants.getSingletonRef();
    private ArrayList<Result> moviesList = new ArrayList<>();
    private Context context;

    public MoviesAdapter(ArrayList<Result> moviesList) {
        this.moviesList = moviesList;
    }



    private class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView backdrop_image;
        TextView title, date;
        MyViewHolder(View view) {
            super(view);
            backdrop_image = (ImageView)view.findViewById(R.id.backdrop_image);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();


            View v =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_list, parent, false);
            return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyViewHolder)
        {
            movie = moviesList.get(position);
            ((MyViewHolder)holder).title.setText(movie.getTitle());
            ((MyViewHolder)holder).date.setText(movie.getReleaseDate());
            String url = urlConstants.URL_Image + movie.getBackdropPath();
            Picasso.with(context)
                    .load(url)
                    .placeholder(R.drawable.not_available)
                    .into(((MyViewHolder)holder).backdrop_image);
        }
    }

    @Override
    public int getItemCount()
    {
        return this.moviesList.size();
    }
}