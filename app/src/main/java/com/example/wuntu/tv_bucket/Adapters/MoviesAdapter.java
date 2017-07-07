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
import java.util.List;

/**
 * Created by Wuntu on 05-07-2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    UrlConstants urlConstants = UrlConstants.getSingletonRef();
    private List<Result> moviesList = new ArrayList<>();
    private Context context;
    private final int VIEW_ITEM = 0;
    private final int VIEW_PROG = 1;
    Fragment fragment;

    public MoviesAdapter(List<Result> moviesList,Fragment fragment) {
        this.moviesList = moviesList;
        this.fragment = fragment;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView backdrop_image;
        public TextView title, date;
        public MyViewHolder(View view) {
            super(view);
            backdrop_image = (ImageView)view.findViewById(R.id.backdrop_image);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tag_text,previous_page,next_page,between_text;

        public FooterViewHolder(View itemView) {
            super(itemView);
            between_text = (TextView) itemView.findViewById(R.id.between_text);
            tag_text = (TextView)itemView.findViewById(R.id.tag_text);
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
        return position != getItemCount()-1;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

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



        if (holder instanceof MyViewHolder)
        {
            Result movie = moviesList.get(position);
            ((MyViewHolder)holder).title.setText(movie.getTitle());
            ((MyViewHolder)holder).date.setText(movie.getReleaseDate());
            String url = urlConstants.URL_Image + movie.getBackdropPath();
            Picasso.with(context)
                    .load(url)
                    .into(((MyViewHolder)holder).backdrop_image);
        }
        else
        {
            final Result movie = moviesList.get(position);
            if(movie.getPage() == 1)
            {
                ((FooterViewHolder)holder).previous_page.setVisibility(View.INVISIBLE);
                ((FooterViewHolder)holder).between_text.setVisibility(View.INVISIBLE);
            }
            else
            {
                ((FooterViewHolder)holder).previous_page.setVisibility(View.VISIBLE);
                ((FooterViewHolder)holder).between_text.setVisibility(View.VISIBLE);
            }
            if (movie.getPage() == movie.getTotal_pages())
            {
                ((FooterViewHolder)holder).between_text.setVisibility(View.INVISIBLE);
                ((FooterViewHolder)holder).next_page.setVisibility(View.INVISIBLE);
            }
            else
            {
                ((FooterViewHolder)holder).next_page.setVisibility(View.VISIBLE);
            }

            ((FooterViewHolder)holder).tag_text.setText("Viewing " + movie.getPage() + " of " + movie.getTotal_pages() + " Pages" );
            ((FooterViewHolder)holder).previous_page.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    ((MoviesMainFragment)fragment).prepareOnlineData(movie.getPage() - 1);
                }
            });
            ((FooterViewHolder)holder).next_page.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MoviesMainFragment)fragment).prepareOnlineData(movie.getPage() + 1);
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