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
    MovieDetailModel movieDetailModel;
    private final int VIEW_ITEM = 0;
    private final int VIEW_PROG = 1;

    public MoviesDetailAdapter(MovieView movieView, ArrayList<MovieDetailModel> detailArrayList)
    {
        a= movieView;
        this.detailArrayList = detailArrayList;
    }

    public class MyViewHolder1 extends RecyclerView.ViewHolder
    {
        ImageView cast_profile_picture;
        TextView cast_name,cast_character_name;

        public MyViewHolder1(View view)
        {
            super(view);
            cast_profile_picture = (ImageView) view.findViewById(R.id.thumbnail);
            cast_name = (TextView) view.findViewById(R.id.title);
            cast_character_name = (TextView) view.findViewById(R.id.count);
        }
    }

   /* public class FooterViewHolder extends RecyclerView.ViewHolder
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
*/
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

            View v =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cast_details, parent, false);
            return new MyViewHolder1(v);

        /*} else if (viewType == VIEW_PROG){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.footer_layout, parent, false);
            return new FooterViewHolder(v);
        }*/

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof MyViewHolder1)
        {
            movieDetailModel = detailArrayList.get(position);
            ((MyViewHolder1)holder).cast_character_name.setText(movieDetailModel.getCharacter());
            ((MyViewHolder1)holder).cast_name.setText(movieDetailModel.getName());
        }
    }

    @Override
    public int getItemCount() {
        return this.detailArrayList.size();
    }

}
