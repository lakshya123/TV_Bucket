package com.example.wuntu.tv_bucket.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Wuntu on 10-08-2017.
 */

public class TvSeasons implements Parcelable
{
    @SerializedName("air_date")
    @Expose
    private String airDate;
    @SerializedName("episode_count")
    @Expose
    private Integer episodeCount;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("season_number")
    @Expose
    private Integer seasonNumber;

    @SerializedName("last_air_date")
    @Expose
    private String lastAirDate;


    public TvSeasons(Parcel in) {
        airDate = in.readString();
        episodeCount = in.readInt();
        id = in.readInt();
        posterPath = in.readString();
        seasonNumber = in.readInt();
        lastAirDate = in.readString();
    }

    public static final Creator<TvSeasons> CREATOR = new Creator<TvSeasons>() {
        @Override
        public TvSeasons createFromParcel(Parcel in) {
            return new TvSeasons(in);
        }

        @Override
        public TvSeasons[] newArray(int size) {
            return new TvSeasons[size];
        }
    };

    public TvSeasons() {

    }
    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public Integer getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(Integer episodeCount) {
        this.episodeCount = episodeCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(airDate);
        parcel.writeInt(episodeCount);
        parcel.writeInt(id);
        parcel.writeString(posterPath);
        parcel.writeInt(seasonNumber);
        parcel.writeString(lastAirDate);
    }
}
