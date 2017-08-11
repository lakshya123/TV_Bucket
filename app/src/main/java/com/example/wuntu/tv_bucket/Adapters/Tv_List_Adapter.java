package com.example.wuntu.tv_bucket.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuntu.tv_bucket.Fragments.MoviesMainFragment;
import com.example.wuntu.tv_bucket.Fragments.TvMainFragment;
import com.example.wuntu.tv_bucket.Models.Result;
import com.example.wuntu.tv_bucket.Models.TVListResultModel;
import com.example.wuntu.tv_bucket.R;
import com.example.wuntu.tv_bucket.Utils.UrlConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wuntu on 05-07-2017.
 */

public class Tv_List_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    TVListResultModel resultModel;
    UrlConstants urlConstants = UrlConstants.getSingletonRef();
    private ArrayList<TVListResultModel> tv_list = new ArrayList<>();
    private Context context;
    private final int VIEW_ITEM = 0;
    private final int VIEW_PROG = 1;
    Fragment fragment;
    String url;

    public Tv_List_Adapter(ArrayList<TVListResultModel> tv_list, Fragment fragment, String url) {
        this.tv_list = tv_list;
        this.fragment = fragment;
        this.url = url;
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
        public TextView previous_page,next_page;

        public FooterViewHolder(View itemView) {
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
            resultModel = tv_list.get(position);
            ((MyViewHolder)holder).title.setText(resultModel.getName());
            ((MyViewHolder)holder).date.setText(resultModel.getFirstAirDate());
            String url = urlConstants.URL_Image + resultModel.getBackdropPath();
            Picasso.with(context)
                    .load(url)
                    .placeholder(R.drawable.not_available)
                    .into(((MyViewHolder)holder).backdrop_image);
        }
        else if (holder instanceof FooterViewHolder)
        {
            resultModel = tv_list.get(position);
            if(resultModel.getPage() == 1)
            {
                ((FooterViewHolder)holder).previous_page.setVisibility(View.INVISIBLE);
            }
            else
            {
                ((FooterViewHolder)holder).previous_page.setVisibility(View.VISIBLE);
            }
            if (resultModel.getPage() == resultModel.getTotalPages())
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
                    ((TvMainFragment)fragment).prepareOnlineData(url,resultModel.getPage() - 1);
                }
            });
            ((FooterViewHolder)holder).next_page.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((TvMainFragment)fragment).prepareOnlineData(url,resultModel.getPage() + 1);
                }
            });
        }



    }

    @Override
    public int getItemCount()
    {
        return this.tv_list.size();
    }
}