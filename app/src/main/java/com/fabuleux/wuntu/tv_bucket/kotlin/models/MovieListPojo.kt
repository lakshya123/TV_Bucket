package com.fabuleux.wuntu.tv_bucket.kotlin.models

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.fabuleux.wuntu.tv_bucket.kotlin.utils.API
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso

data class MovieListPojo(@SerializedName("vote_count") var vote_count: Int,
                         @SerializedName("id") var id:Long,
                         @SerializedName("video") var video:Boolean,
                         @SerializedName("vote_average") var vote_average:Double,
                         @SerializedName("title") var title:String,
                         @SerializedName("popularity") var popularity:Double,
                         @SerializedName("poster_path") var poster_path:String,
                         @SerializedName("original_language") var original_language:String,
                         @SerializedName("original_title") var original_title:String,
                         @SerializedName("genre_ids") var genre_ids:List<Int>,
                         @SerializedName("backdrop_path") var backdrop_path:String,
                         @SerializedName("adult") var adult:Boolean,
                         @SerializedName("overview") var overview:String,
                         @SerializedName("release_date") var release_date:String)
{
    companion object{
        @BindingAdapter("bind:image")
        @JvmStatic
        fun loadImage(view: ImageView, imageUrl: String?) {
            Picasso.with(view.context)
                    .load(API.URL_Image + imageUrl)
                    .into(view)
        }
    }

}