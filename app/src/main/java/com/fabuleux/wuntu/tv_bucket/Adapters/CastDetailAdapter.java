package com.fabuleux.wuntu.tv_bucket.Adapters;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fabuleux.wuntu.tv_bucket.CastViewActivity;
import com.fabuleux.wuntu.tv_bucket.Models.Cast;
import com.fabuleux.wuntu.tv_bucket.MovieView;
import com.fabuleux.wuntu.tv_bucket.R;
import com.fabuleux.wuntu.tv_bucket.Utils.UrlConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Wuntu on 21-07-2017.
 */

public class CastDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Cast> subArrayList = new ArrayList<>() ;
    private UrlConstants urlConstants = UrlConstants.getSingletonRef();
    private Cast cast;
    private final int VIEW_ITEM = 0;
    private final int VIEW_PROG = 1;
    private Context context;
    MovieView a;
    ArrayList<Cast> fullArrayList = new ArrayList<>();

    public CastDetailAdapter(ArrayList<Cast> detailArrayList,ArrayList<Cast> subCastArrayList)
    {

        this.subArrayList = subCastArrayList;
        this.fullArrayList = detailArrayList;
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

    public class FooterViewHolder1 extends RecyclerView.ViewHolder
    {
        TextView view_more;

        public FooterViewHolder1(View itemView) {
            super(itemView);
            view_more = (TextView) itemView.findViewById(R.id.view_more);


        }
    }

    @Override
    public int getItemViewType(int position) {
        if (subArrayList.size() >7) {
            if (isPositionItem(position))
                return VIEW_ITEM;
            return VIEW_PROG;
        }
        else return VIEW_ITEM;
    }

    private boolean isPositionItem(int position) {
        return position != getItemCount() -1;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        context = parent.getContext();

        if (viewType == VIEW_ITEM)
        {
            View v =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cast_details, parent, false);
            return new MyViewHolder1(v);

        } else if (viewType == VIEW_PROG){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.footer_layout_movie_details, parent, false);
            return new FooterViewHolder1(v);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof MyViewHolder1)
        {
            cast = subArrayList.get(position);
            ((MyViewHolder1)holder).cast_character_name.setText(cast.getCharacter());
            ((MyViewHolder1)holder).cast_name.setText(cast.getName());
            String url3 = urlConstants.URL_Image + cast.getProfilePath();
            Picasso.with(context)
                    .load(url3)
                    .placeholder(R.drawable.not_available)
                    .into(((MyViewHolder1)holder).cast_profile_picture);
        }
        else if (holder instanceof FooterViewHolder1)
        {
            ((FooterViewHolder1)holder).view_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(context,CastViewActivity.class);
                    intent.putExtra("EVENT","FULL LIST CAST");
                    intent.putParcelableArrayListExtra("LIST",fullArrayList);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.subArrayList.size();
    }

}
