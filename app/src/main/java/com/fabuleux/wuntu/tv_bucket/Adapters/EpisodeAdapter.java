package com.fabuleux.wuntu.tv_bucket.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fabuleux.wuntu.tv_bucket.CastViewActivity;
import com.fabuleux.wuntu.tv_bucket.Fragments.CastViewListFragment;
import com.fabuleux.wuntu.tv_bucket.Models.Cast;
import com.fabuleux.wuntu.tv_bucket.Models.Episode;
import com.fabuleux.wuntu.tv_bucket.R;
import com.fabuleux.wuntu.tv_bucket.Utils.UrlConstants;
import com.fabuleux.wuntu.tv_bucket.YoutubeActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

/**
 * Created by Wuntu on 20-08-2017.
 */

public class EpisodeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    Context context;
    private ArrayList<Episode> episodeArrayList = new ArrayList<>();
    private UrlConstants urlConstants = UrlConstants.getSingletonRef();
    private ArrayList<Cast> castArrayList = new ArrayList<>();
    private int i;

    public EpisodeAdapter(ArrayList<Episode> episodeArrayList)
    {
        this.episodeArrayList = episodeArrayList;
    }

    private class EpisodeViewholder extends RecyclerView.ViewHolder
    {
        ImageView still_image;
        TextView episode_number,episode_name,episode_air_date,episode_overview,episode_ratings,guest_cast,episode_videos;
         EpisodeViewholder(View itemView) {
            super(itemView);
            still_image = (ImageView) itemView.findViewById(R.id.still_image);
            episode_name = (TextView) itemView.findViewById(R.id.episode_name);
            episode_air_date = (TextView) itemView.findViewById(R.id.episode_air_date);
            episode_number = (TextView) itemView.findViewById(R.id.episode_number);
            episode_overview = (TextView) itemView.findViewById(R.id.episode_overview);
            episode_ratings = (TextView) itemView.findViewById(R.id.episode_ratings);
            guest_cast = (TextView) itemView.findViewById(R.id.guest_cast);
            episode_videos = (TextView) itemView.findViewById(R.id.episode_videos);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_view,parent,false);
        return new EpisodeViewholder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
    {

        for (i = 0;i<episodeArrayList.get(position).getGuestStars().size();i++)
        {
            Cast cast = new Cast();
            cast.setName(episodeArrayList.get(position).getGuestStars().get(i).getName());
            cast.setCharacter(episodeArrayList.get(position).getGuestStars().get(i).getCharacter());
            cast.setId(episodeArrayList.get(position).getGuestStars().get(i).getId());
            cast.setProfilePath(episodeArrayList.get(position).getGuestStars().get(i).getProfilePath());
            castArrayList.add(i,cast);
        }
        ((EpisodeViewholder)holder).guest_cast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CastViewListFragment castViewListFragment = new CastViewListFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("FULL CREW LIST",castArrayList);
                castViewListFragment.setArguments(bundle);
                ((CastViewActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.container,castViewListFragment).addToBackStack(null).commit();
            }
        });

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                ((EpisodeViewholder)holder).still_image.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable)
            {
                ((EpisodeViewholder)holder).still_image.setImageDrawable(errorDrawable);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        String image_url = urlConstants.URL_Image + episodeArrayList.get(position).getStillPath();
        Picasso.with(context)
                .load(image_url)
                .error(R.drawable.not_available)
                .into(target);

        if (episodeArrayList.get(position).getEpisodeNumber() != null)
        {
            ((EpisodeViewholder)holder).episode_number.setText(String.valueOf(episodeArrayList.get(position).getEpisodeNumber()));
        }

        ((EpisodeViewholder)holder).episode_name.setText(episodeArrayList.get(position).getName());
        ((EpisodeViewholder)holder).episode_air_date.setText(episodeArrayList.get(position).getAirDate());
        ((EpisodeViewholder)holder).episode_overview.setText(episodeArrayList.get(position).getOverview());
        ((EpisodeViewholder)holder).episode_ratings.setText(String.valueOf(episodeArrayList.get(position).getVoteAverage()));


        ((EpisodeViewholder)holder).episode_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,YoutubeActivity.class);
                intent.putExtra("VIEW","EPISODE");
                intent.putExtra("ID",String.valueOf(episodeArrayList.get(position).getId()));
                intent.putExtra("SEASON_NUMBER",String.valueOf(episodeArrayList.get(position).getSeasonNumber()));
                intent.putExtra("EPISODE_NUMBER",String.valueOf(episodeArrayList.get(position).getEpisodeNumber()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return episodeArrayList.size();
    }
}
