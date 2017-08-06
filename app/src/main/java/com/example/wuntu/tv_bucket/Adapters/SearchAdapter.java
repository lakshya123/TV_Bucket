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

        public SearchAdapter(ArrayList<MultiSearchResultModel> searchModelArrayList) {
            this.searchModelArrayList = searchModelArrayList;
        }



        public class MySearchViewHolder extends RecyclerView.ViewHolder {

            public ImageView search_image;
            public TextView search_title;
            public MySearchViewHolder(View view) {
                super(view);
                search_image = (ImageView)view.findViewById(R.id.search_image);
                search_title = (TextView) view.findViewById(R.id.search_title);
            }
        }

        public class SearchFooterViewHolder extends RecyclerView.ViewHolder
        {
            public TextView tag_text,previous_page,next_page,between_text;

            public SearchFooterViewHolder(View itemView) {
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
            return position != getItemCount() -1;
        }



        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            context = parent.getContext();

            if (viewType == VIEW_ITEM) {
                View v =  LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.search_item, parent, false);
                return new MySearchViewHolder(v);
            } else if (viewType == VIEW_PROG){
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.footer_layout, parent, false);
                return new SearchFooterViewHolder(v);
            }

            return null;
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


            if (holder instanceof MySearchViewHolder)
            {
                multiSearchResultModel = searchModelArrayList.get(position);
                if (multiSearchResultModel.getMediaType()!= null && multiSearchResultModel.getMediaType().equalsIgnoreCase("movie"))
                {
                    ((MySearchViewHolder)holder).search_title.setText(multiSearchResultModel.getTitle());
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
                    Picasso.with(context)
                            .load(url)
                            .placeholder(R.drawable.not_available)
                            .into(((MySearchViewHolder)holder).search_image);
                }
                else if (multiSearchResultModel.getMediaType()!= null && multiSearchResultModel.getMediaType().equalsIgnoreCase("tv"))
                {
                    ((MySearchViewHolder)holder).search_title.setText(multiSearchResultModel.getName());
                    String url = urlConstants.URL_Logo_Image + multiSearchResultModel.getPosterPath();
                    Picasso.with(context)
                            .load(url)
                            .placeholder(R.drawable.not_available)
                            .into(((MySearchViewHolder)holder).search_image);
                }

            }
            else if (holder instanceof SearchFooterViewHolder)
            {
                /*movie = moviesList.get(position);
                if(movie.getPage() == 1)
                {
                    ((com.example.wuntu.tv_bucket.Adapters.MoviesAdapter.FooterViewHolder)holder).previous_page.setVisibility(View.INVISIBLE);
                    ((com.example.wuntu.tv_bucket.Adapters.MoviesAdapter.FooterViewHolder)holder).between_text.setVisibility(View.INVISIBLE);
                }
                else
                {
                    ((com.example.wuntu.tv_bucket.Adapters.MoviesAdapter.FooterViewHolder)holder).previous_page.setVisibility(View.VISIBLE);
                    ((com.example.wuntu.tv_bucket.Adapters.MoviesAdapter.FooterViewHolder)holder).between_text.setVisibility(View.VISIBLE);
                }
                if (movie.getPage() == movie.getTotal_pages())
                {
                    ((com.example.wuntu.tv_bucket.Adapters.MoviesAdapter.FooterViewHolder)holder).between_text.setVisibility(View.INVISIBLE);
                    ((com.example.wuntu.tv_bucket.Adapters.MoviesAdapter.FooterViewHolder)holder).next_page.setVisibility(View.INVISIBLE);
                }
                else
                {
                    ((com.example.wuntu.tv_bucket.Adapters.MoviesAdapter.FooterViewHolder)holder).next_page.setVisibility(View.VISIBLE);
                }

                ((com.example.wuntu.tv_bucket.Adapters.MoviesAdapter.FooterViewHolder)holder).tag_text.setText("Viewing " + movie.getPage() + " of " + movie.getTotal_pages() + " Pages" );
                ((com.example.wuntu.tv_bucket.Adapters.MoviesAdapter.FooterViewHolder)holder).previous_page.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        ((MoviesMainFragment)fragment).prepareOnlineData(url,movie.getPage() - 1);
                    }
                });
                ((com.example.wuntu.tv_bucket.Adapters.MoviesAdapter.FooterViewHolder)holder).next_page.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MoviesMainFragment)fragment).prepareOnlineData(url,movie.getPage() + 1);
                    }
                });*/
            }



        }

        @Override
        public int getItemCount()
        {
            return this.searchModelArrayList.size();
        }
    }

