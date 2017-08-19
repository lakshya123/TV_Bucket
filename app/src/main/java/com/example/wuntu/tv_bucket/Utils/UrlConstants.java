package com.example.wuntu.tv_bucket.Utils;

/**
 * Created by Wuntu on 05-07-2017.
 */

public class UrlConstants
{

    public String URL_popular_movies = "https://api.themoviedb.org/3/movie/popular?api_key=6d1c42e81f275fe7bcad0d4b020d010e&page=";

    public String URL_Image = "https://image.tmdb.org/t/p/w500";

    public String URL_Logo_Image = "https://image.tmdb.org/t/p/w92";

    public String Movie_1st_URL = "https://api.themoviedb.org/3/movie/";

    public String Movie_2nd_URL = "?api_key=6d1c42e81f275fe7bcad0d4b020d010e&append_to_response=credits";

    public String Person_1st_URL = "http://api.themoviedb.org/3/person/";

    public String Person_2nd_URL = "?api_key=6d1c42e81f275fe7bcad0d4b020d010e";

    public String Movies_Videos_1st_URL = "http://api.themoviedb.org/3/movie/";

    public String Movies_Videos_2nd_URL = "/videos?api_key=6d1c42e81f275fe7bcad0d4b020d010e";

    public String Tv_Videos_1st_URL = "https://api.themoviedb.org/3/tv/";

    public String Tv_Videos_2nd_URL = "/videos?api_key=6d1c42e81f275fe7bcad0d4b020d010e&language=en-US";

    public String Youtube_URL = "https://www.youtube.com/watch?v=";

    public String URL_upcoming_movies = "https://api.themoviedb.org/3/movie/upcoming?api_key=6d1c42e81f275fe7bcad0d4b020d010e&page=";

    public String URL_top_rated_movies = "https://api.themoviedb.org/3/movie/top_rated?api_key=6d1c42e81f275fe7bcad0d4b020d010e&page=";

    public String URL_now_playing_movies = "https://api.themoviedb.org/3/movie/now_playing?api_key=6d1c42e81f275fe7bcad0d4b020d010e&page=";

    public String URL_multi_search = "https://api.themoviedb.org/3/search/multi?api_key=6d1c42e81f275fe7bcad0d4b020d010e&language=en-US&query=";

    public String URL_multi_search_2 = "&page=";

    public String URL_tv_airing_today = "https://api.themoviedb.org/3/tv/airing_today?api_key=6d1c42e81f275fe7bcad0d4b020d010e&page=";

    public String URL_tv_on_air = "https://api.themoviedb.org/3/tv/on_the_air?api_key=6d1c42e81f275fe7bcad0d4b020d010e&language=en-US&page=";

    public String URL_popular_tv_shows = "https://api.themoviedb.org/3/tv/popular?api_key=6d1c42e81f275fe7bcad0d4b020d010e&language=en-US&page=";

    public String URL_top_rated_tv_shows = "https://api.themoviedb.org/3/tv/top_rated?api_key=6d1c42e81f275fe7bcad0d4b020d010e&language=en-US&page=";

    public String TV_1st_URL = "https://api.themoviedb.org/3/tv/";

    public String TV_2nd_URL = "?api_key=6d1c42e81f275fe7bcad0d4b020d010e&language=en-US&append_to_response=credits";

    public String TV_Episodes_1st_URL = "https://api.themoviedb.org/3/tv/";

    public String TV_Episodes_2nd_URL = "/season/";

    public String TV_Episodes_3rd_URL = "?api_key=6d1c42e81f275fe7bcad0d4b020d010e&language=en-US&append_to_response=credits";


    private static UrlConstants mSingletonRef;

    public static UrlConstants getSingletonRef() {
        if (mSingletonRef == null)
            mSingletonRef = new UrlConstants();
        return mSingletonRef;
    }
}
