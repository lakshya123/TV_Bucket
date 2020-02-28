package com.fabuleux.wuntu.tv_bucket.kotlin.Models

import com.google.gson.annotations.SerializedName

object RequestModel
{
    data class ResponseModel<T>(@SerializedName("page") val page: Int,
                                @SerializedName("total_results") var total_results: Int,
                                @SerializedName("total_pages") var total_pages:Int,
                                @SerializedName("result") var result: List<T>)

    data class Movie(@SerializedName("URL") var URL: String,
                     @SerializedName("page") var page:String,
                     @SerializedName("total_pages") var total_pages: Int,
                     @SerializedName("vote_count") var vote_count: Int,
                     @SerializedName("id") var id:Int,
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
                     @SerializedName("release_date") var release_date:String
    )
}