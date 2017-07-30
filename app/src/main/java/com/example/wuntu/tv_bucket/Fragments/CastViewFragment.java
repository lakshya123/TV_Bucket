package com.example.wuntu.tv_bucket.Fragments;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.wuntu.tv_bucket.Models.CastDetailModel;
import com.example.wuntu.tv_bucket.R;
import com.example.wuntu.tv_bucket.Utils.AppSingleton;
import com.example.wuntu.tv_bucket.Utils.UrlConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


public class CastViewFragment extends Fragment {


    ImageView person_image;
    TextView person_name,person_biography,person_birthday,person_deathday,person_birthplace,person_homepage;
    UrlConstants urlConstants = UrlConstants.getSingletonRef();
    private Gson gson;
    CastDetailModel castDetailModel;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_cast_view, container, false);


        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("TV Bucket");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);




        Integer id = getArguments().getInt("ID");
        String id1 = String.valueOf(id);

        String url = urlConstants.Person_1st_URL + id1 + urlConstants.Person_2nd_URL;

        person_image = (ImageView) view.findViewById(R.id.person_image);
        person_name = (TextView) view.findViewById(R.id.person_name);
        person_biography = (TextView) view.findViewById(R.id.person_biography);
        person_birthday = (TextView) view.findViewById(R.id.person_birthday);
        person_deathday = (TextView) view.findViewById(R.id.person_deathday);
        person_birthplace = (TextView) view.findViewById(R.id.person_birthplace);
        person_homepage = (TextView) view.findViewById(R.id.person_homepage);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        setdata(url);


        return view;
    }

    private void setdata(String url)
    {
        String tag_json_obj = "json_obj_req";

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                castDetailModel = gson.fromJson(response,CastDetailModel.class);
                String image_url = urlConstants.URL_Image + castDetailModel.getProfilePath();

                Picasso.with(getActivity())
                        .load(image_url)
                        .into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
                            {
                                person_image.setBackground(new BitmapDrawable(getContext().getResources(),bitmap));
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable)
                            {
                                Log.d("TAG", "FAILED");
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable)
                            {
                                Log.d("TAG", "Prepare Load");
                            }
                        });

                person_name.setText(castDetailModel.getName());
                person_biography.setText(castDetailModel.getBiography());
                person_birthday.setText(castDetailModel.getBirthday());
                person_deathday.setText(String.valueOf(castDetailModel.getDeathday()));
                person_birthplace.setText(castDetailModel.getPlaceOfBirth());
                person_homepage.setText(castDetailModel.getHomepage());

                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppSingleton.getInstance(getContext()).addToRequestQueue(stringRequest2, tag_json_obj);
    }

}
