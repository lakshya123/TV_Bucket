package com.fabuleux.wuntu.tv_bucket.kotlin.retrofitHelper

class Resource<T>(val status:Status,val data: T?,val apiError:String?)
{
    enum class Status{
        SUCCESS,ERROR,LOADING
    }

    companion object{
        fun <T> success(data:T?) : Resource<T> {
            return Resource(Status.SUCCESS,data,null)
        }
        fun <T> error(apiError: String?,data:T?) : Resource<T> {
            return Resource(Status.ERROR,data,apiError)
        }

        fun <T> loading(data:T?) : Resource<T> {
            return Resource(Status.LOADING,data,null)
        }

    }
}