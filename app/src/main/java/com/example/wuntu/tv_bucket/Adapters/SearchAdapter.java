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
import com.example.wuntu.tv_bucket.Fragments.SearchFragment;
import com.example.wuntu.tv_bucket.Models.MultiSearchResultModel;
import com.example.wuntu.tv_bucket.Models.Result;
import com.example.wuntu.tv_bucket.R;
import com.example.wuntu.tv_bucket.Utils.UrlConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Wuntu on 06-08-2017.
 */


    public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        UrlConstants urlConstants = UrlConstants.getSingletonRef();
        private ArrayList<MultiSearchResultModel> searchModelArrayList = new ArrayList<>();
        private Context context;
        private final int VIEW_ITEM = 0;
        private final int VIEW_PROG = 1;
        Fragment fragment;
        String url;
        MultiSearchResultModel multiSearchResultModel = new MultiSearchResultModel();

        public SearchAdapter(ArrayList<MultiSearchResultModel> searchModelArrayList,Fragment fragment) {
            this.searchModelArrayList = searchModelArrayList;
            this.fragment = fragment;
        }



        public class MySearchViewHolder extends RecyclerView.ViewHolder {

            public ImageView search_image;
            public TextView search_title,color_textview;
            public MySearchViewHolder(View view) {
                super(view);
                search_image = (ImageView)view.findViewById(R.id.search_image);
                search_title = (TextView) view.findViewById(R.id.search_title);
                color_textview = (TextView) view.findViewById(R.id.color_textview);
            }
        }





        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            context = parent.getContext();


                View v =  LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.search_item, parent, false);
                return new MySearchViewHolder(v);
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


            if (holder instanceof MySearchViewHolder)
            {
                multiSearchResultModel = searchModelArrayList.get(position);
                if (multiSearchResultModel.getMediaType()!= null && multiSearchResultModel.getMediaType().equalsIgnoreCase("movie"))
                {
                    ((MySearchViewHolder)holder).search_title.setText(multiSearchResultModel.getTitle());
                    ((MySearchViewHolder)holder).color_textview.setText("Movie");
                    String url = urlConstants.URL_Logo_Image + multiSearchResultModel.getPosterPath();
                    Picasso.with(context)
                            .load(url)
                            .placeholder(R.drawable.not_available)
                            .into(((MySearchViewHolder)holder).search_image);
                }
                else if(multiSearchResultModel.getMediaType()!= null && multiSearchResultModel.getMediaType().equalsIgnoreCase("person"))
                {
                    ((MySearchViewHolder)holder).search_title.setText(multiSearchResultModel.getName());
                    String url = urlConstants.URL_Logo_Image + multiSearchResultModel.getProfilePath();
                    ((MySearchViewHolder)holder).color_textview.setText("Person");
                    Picasso.with(context)
                            .load(url)
                            .placeholder(R.drawable.not_available)
                            .into(((MySearchViewHolder)holder).search_image);
                }
                else if (multiSearchResultModel.getMediaType()!= null && multiSearchResultModel.getMediaType().equalsIgnoreCase("tv"))
                {
                    ((MySearchViewHolder)holder).search_title.setText(multiSearchResultModel.getName());
                    String url = urlConstants.URL_Logo_Image + multiSearchResultModel.getPosterPath();
                    ((MySearchViewHolder)holder).color_textview.setText("TV");
                    Picasso.with(context)
                            .load(url)
                            .placeholder(R.drawable.not_available)
                            .into(((MySearchViewHolder)holder).search_image);
                }

            }
        }

        @Override
        public int getItemCount()
        {
            return this.searchModelArrayList.size();
        }
    }

