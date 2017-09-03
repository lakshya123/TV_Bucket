package com.example.wuntu.tv_bucket.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wuntu.tv_bucket.Fragments.MoviesMainFragment;
import com.example.wuntu.tv_bucket.Models.Result;
import com.example.wuntu.tv_bucket.R;
import com.example.wuntu.tv_bucket.Utils.UrlConstants;
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
    private final int VIEW_ITEM = 0;
    private final int VIEW_PROG = 1;
    private Fragment fragment;

    public MoviesAdapter(ArrayList<Result> moviesList, Fragment fragment) {
        this.moviesList = moviesList;
        this.fragment = fragment;
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

    private class FooterViewHolder extends RecyclerView.ViewHolder
    {
        TextView previous_page,next_page;

        FooterViewHolder(View itemView) {
            super(itemView);
            previous_page = (TextView)itemView.findViewById(R.id.previous_page);
            next_page = (TextView) itemView.findViewById(R.id.next_page);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionItem(position))
            return VIEW_ITEM;
        return VIEW_PROG;
    }

    private boolean isPositionItem(int position) {
        return position != getItemCount() -1;
    }


   /* private boolean isPositionItem(int position) {
        return position == moviesList.size();
    }*/


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

        if (viewType == VIEW_ITEM) {
            View v =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_list, parent, false);
            return new MyViewHolder(v);
        } else if (viewType == VIEW_PROG){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.footer_layout, parent, false);
            return new FooterViewHolder(v);
        }

        return null;
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
        else if (holder instanceof FooterViewHolder)
        {
            movie = moviesList.get(position);
            if(movie.getPage() == 1)
            {
                ((FooterViewHolder)holder).previous_page.setVisibility(View.INVISIBLE);
            }
            else
            {
                ((FooterViewHolder)holder).previous_page.setVisibility(View.VISIBLE);
            }
            if (movie.getPage() == movie.getTotal_pages())
            {
                ((FooterViewHolder)holder).next_page.setVisibility(View.INVISIBLE);
            }
            else
            {
                ((FooterViewHolder)holder).next_page.setVisibility(View.VISIBLE);
            }

            ((FooterViewHolder)holder).previous_page.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //Toast.makeText(context, movie.getURL() + "", Toast.LENGTH_SHORT).show();
                    ((MoviesMainFragment)fragment).prepareOnlineData(movie.getURL(),movie.getPage() - 1);
                }
            });
            ((FooterViewHolder)holder).next_page.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, movie.getURL() + "", Toast.LENGTH_SHORT).show();
                    ((MoviesMainFragment)fragment).prepareOnlineData(movie.getURL(),movie.getPage() + 1);
                }
            });
        }



    }

    @Override
    public int getItemCount()
    {
        return this.moviesList.size();
    }
}