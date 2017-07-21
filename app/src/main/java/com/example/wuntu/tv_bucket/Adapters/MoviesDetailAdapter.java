package com.example.wuntu.tv_bucket.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wuntu.tv_bucket.Models.MovieDetailModel;
import com.example.wuntu.tv_bucket.MovieView;
import com.example.wuntu.tv_bucket.R;

import java.util.ArrayList;

/**
 * Created by Wuntu on 21-07-2017.
 */

public class MoviesDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity a;
    ArrayList<MovieDetailModel> detailArrayList = new ArrayList<>();
    private final int VIEW_ITEM = 0;
    private final int VIEW_PROG = 1;

    public MoviesDetailAdapter(MovieView movieView, ArrayList<MovieDetailModel> detailArrayList)
    {
        a= movieView;
        this.detailArrayList = detailArrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder
    {

        public FooterViewHolder(View itemView) {
            super(itemView);


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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == VIEW_ITEM) {
            View v =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_list_row, parent, false);
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

    }

    @Override
    public int getItemCount() {
        return this.detailArrayList.size();
    }

}
