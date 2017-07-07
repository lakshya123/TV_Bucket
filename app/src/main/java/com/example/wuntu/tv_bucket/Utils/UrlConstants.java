package com.example.wuntu.tv_bucket.Utils;

/**
 * Created by Wuntu on 05-07-2017.
 */

public class UrlConstants
{

    public String URL_popular_movies = "https://api.themoviedb.org/3/movie/popular?api_key=6d1c42e81f275fe7bcad0d4b020d010e&page=";

    public String URL_Image = "https://image.tmdb.org/t/p/w500";

    private static UrlConstants mSingletonRef;

    public static UrlConstants getSingletonRef() {
        if (mSingletonRef == null)
            mSingletonRef = new UrlConstants();
        return mSingletonRef;
    }
}
