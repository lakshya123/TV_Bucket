package com.example.wuntu.tv_bucket.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wuntu.tv_bucket.Models.Cast;
import com.example.wuntu.tv_bucket.R;
import com.example.wuntu.tv_bucket.Utils.UrlConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Wuntu on 26-07-2017.
 */

public class FullCastListAdapter extends RecyclerView.Adapter {

    ArrayList<Cast> castArrayList;
    private Context context;
    Cast cast;
    UrlConstants urlConstants = UrlConstants.getSingletonRef();

    public FullCastListAdapter(ArrayList<Cast> castArrayList)
    {
        this.castArrayList = castArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView cast_profile_picture;
        TextView cast_name,cast_character_name;

        public ViewHolder(View itemView) {
            super(itemView);
            cast_profile_picture = (ImageView) itemView.findViewById(R.id.thumbnail);
            cast_name = (TextView) itemView.findViewById(R.id.title);
            cast_character_name = (TextView) itemView.findViewById(R.id.count);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        context = parent.getContext();
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cast_details, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        cast = castArrayList.get(position);
        ((ViewHolder)holder).cast_character_name.setText(cast.getCharacter());
        ((ViewHolder)holder).cast_name.setText(cast.getName());
        String url3 = urlConstants.URL_Image + cast.getProfilePath();
        Picasso.with(context)
                .load(url3)
                .into(((ViewHolder)holder).cast_profile_picture);

    }

    @Override
    public int getItemCount() {
        return this.castArrayList.size();
    }
}
