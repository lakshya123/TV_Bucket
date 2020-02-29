package com.fabuleux.wuntu.tv_bucket.kotlin.models

import com.google.gson.annotations.SerializedName

data class CommonResponse<T>(@SerializedName("page") val page: Int,
                            @SerializedName("total_results") var total_results: Int,
                            @SerializedName("total_pages") var total_pages:Int,
                            @SerializedName("results") var result: List<T>)