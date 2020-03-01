package com.fabuleux.wuntu.tv_bucket.kotlin.models

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.fabuleux.wuntu.tv_bucket.Models.*
import com.fabuleux.wuntu.tv_bucket.kotlin.utils.API
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso

data class MoviePojo (
    @SerializedName("adult") var adult:Boolean,
    @SerializedName("backdrop_path") var backdrop_path:String,
    @SerializedName("belongs_to_collection") var belongs_to_collection:BelongsToCollection,
    @SerializedName("budget") var budget:Long,
    @SerializedName("genres") var genres:List<Genre>,
    @SerializedName("homepage") var homepage:String,
    @SerializedName("id") var id:Int,
    @SerializedName("imdb_id") var imdb_id:String,
    @SerializedName("original_language") var original_language:String,
    @SerializedName("original_title") var original_title:String,
    @SerializedName("overview") var overview:String,
    @SerializedName("popularity") var popularity:Double,
    @SerializedName("poster_path") var poster_path:String,
    @SerializedName("release_date") var release_date:String,
    @SerializedName("revenue") var revenue:Long,
    @SerializedName("runtime") var runtime:Int,
    @SerializedName("status") var status:String,
    @SerializedName("tagline") var tagline:String,
    @SerializedName("title") var title:String,
    @SerializedName("video") var video:Boolean,
    @SerializedName("vote_average") var vote_average:Double,
    @SerializedName("credits") var credits:Credits)
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