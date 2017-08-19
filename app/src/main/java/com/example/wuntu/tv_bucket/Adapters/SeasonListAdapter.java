package com.example.wuntu.tv_bucket.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuntu.tv_bucket.Models.TvSeasons;
import com.example.wuntu.tv_bucket.MovieView;
import com.example.wuntu.tv_bucket.R;
import com.example.wuntu.tv_bucket.Utils.UrlConstants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Wuntu on 18-08-2017.
 */

public class SeasonListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    Context context;
    ArrayList<TvSeasons> seasonsArrayList = new ArrayList<>();
    UrlConstants urlConstants = UrlConstants.getSingletonRef();
    public SeasonListAdapter(ArrayList<TvSeasons> seasonsArrayList)
    {
        this.seasonsArrayList = seasonsArrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView season_number,current_season_year,current_season_episodes,current_season_tagline;
        ImageView poster_image;
        public MyViewHolder(View itemView) {
            super(itemView);
            season_number = (TextView) itemView.findViewById(R.id.season_number);
            current_season_year = (TextView) itemView.findViewById(R.id.current_season_year);
            current_season_episodes = (TextView) itemView.findViewById(R.id.current_season_episodes);
            current_season_tagline = (TextView) itemView.findViewById(R.id.current_season_tagline);
            poster_image = (ImageView) itemView.findViewById(R.id.poster_image);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        context = parent.getContext();

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.season_list,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position)
    {

        Target target1 = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
            {
                ((MyViewHolder)holder).poster_image.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable)
            {
                ((MyViewHolder)holder).poster_image.setImageDrawable(placeHolderDrawable);
            }
        };
        String season_poster_image = urlConstants.URL_Image + seasonsArrayList.get(position).getPosterPath();
        Picasso.with(context)
                .load(season_poster_image)
                .placeholder(R.drawable.not_available)
                .into(target1);

        ((MyViewHolder)holder).poster_image.setTag(target1);



        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        String date = dateFormat.format(calendar.getTime());
        String last_air_Date_string = seasonsArrayList.get(position).getLastAirDate();
        String last_season_air_date_string;
        if (seasonsArrayList.get(position).getAirDate() != null)
        {
            last_season_air_date_string = seasonsArrayList.get(position).getAirDate();
            String last_season_year = last_season_air_date_string.substring(0,4);
            ((MyViewHolder)holder).current_season_year.setText(last_season_year);

            try {
                Date today_date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
                Date last_air_date = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).parse(last_air_Date_string);
                Date last_season_air_date = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).parse(last_season_air_date_string);

                if (last_air_date.compareTo(today_date) < 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Season ");
                    stringBuilder.append(String.valueOf(seasonsArrayList.get(position).getSeasonNumber()));
                    stringBuilder.append(" premiered on ");
                    stringBuilder.append(last_season_air_date_string);
                    ((MyViewHolder) holder).current_season_tagline.setText(stringBuilder);
                }
                else if (last_air_date.compareTo(today_date) > 0)
                {
                    if (last_season_air_date.compareTo(today_date) > 0)
                    {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Season ");
                        stringBuilder.append(String.valueOf(seasonsArrayList.get(position).getSeasonNumber()));
                        stringBuilder.append(" will be premiered on ");
                        stringBuilder.append(last_season_air_date_string);
                        ((MyViewHolder)holder).current_season_tagline.setText(stringBuilder);
                    }
                    else if (last_season_air_date.compareTo(today_date) < 0)
                    {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Season ");
                        stringBuilder.append(String.valueOf(seasonsArrayList.get(position).getSeasonNumber()));
                        stringBuilder.append(" premiered on ");
                        stringBuilder.append(last_season_air_date_string);
                        ((MyViewHolder)holder).current_season_tagline.setText(stringBuilder);
                    }
                }

            } catch (ParseException e) {
                Toast.makeText(context, "try catch error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }




        ((MyViewHolder)holder).season_number.setText(String.valueOf(seasonsArrayList.get(position).getSeasonNumber()));

        ((MyViewHolder)holder).current_season_episodes.setText(String.valueOf(seasonsArrayList.get(position).getEpisodeCount()));



    }

    @Override
    public int getItemCount() {
        return seasonsArrayList.size();
    }
}
