package com.example.wuntu.tv_bucket.Fragments;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import com.example.wuntu.tv_bucket.Utils.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


public class CastViewFragment extends Fragment {


    ImageView person_image;
    TextView person_name,person_biography,person_birthday,person_deathday,person_birthplace,person_homepage;
    TextView person_birthday_title,person_deathday_title,person_birthplace_title,person_homepage_title,personal_info;
    UrlConstants urlConstants = UrlConstants.getSingletonRef();
    private Gson gson;
    CastDetailModel castDetailModel;
    Integer id;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_cast_view, container, false);


        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("TV Bucket");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);



        if (getArguments() != null)
        {
            id = getArguments().getInt("ID");
        }
        String id1 = String.valueOf(id);

        String url = urlConstants.Person_1st_URL + id1 + urlConstants.Person_2nd_URL;

        person_image = (ImageView) view.findViewById(R.id.person_image);
        person_name = (TextView) view.findViewById(R.id.person_name);
        person_biography = (TextView) view.findViewById(R.id.person_biography);
        person_birthday = (TextView) view.findViewById(R.id.person_birthday);
        person_deathday = (TextView) view.findViewById(R.id.person_deathday);
        person_birthplace = (TextView) view.findViewById(R.id.person_birthplace);
        person_homepage = (TextView) view.findViewById(R.id.person_homepage);


        person_birthday_title = (TextView) view.findViewById(R.id.person_birthday_title);
        person_deathday_title = (TextView) view.findViewById(R.id.person_deathday_title);
        person_birthplace_title = (TextView) view.findViewById(R.id.person_birthplace_title);
        person_homepage_title = (TextView) view.findViewById(R.id.person_homepage_title);

        personal_info = (TextView) view.findViewById(R.id.personal_info);


        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        setdata(url);


        return view;
    }

    private void setdata(String url)
    {

        boolean b = Utility.isNetworkAvailable(getActivity());

        if (!b)
        {
            Snackbar.make(getActivity().findViewById(R.id.relative_layout_cast_view),"No Internet Connection",Snackbar.LENGTH_LONG).show();
        }
        String tag_json_obj = "json_obj_req";

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                castDetailModel = gson.fromJson(response,CastDetailModel.class);
                String image_url = urlConstants.URL_Image + castDetailModel.getProfilePath();

                if (castDetailModel.getHomepage() == null && castDetailModel.getPlaceOfBirth() == null
                        && castDetailModel.getDeathday() == null && castDetailModel.getBirthday() == null)
                {
                    personal_info.setVisibility(View.GONE);
                }

                Target target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        person_image.setBackground(new BitmapDrawable(getContext().getResources(),bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        person_image.setImageDrawable(errorDrawable);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                };

                Picasso.with(getActivity())
                        .load(image_url)
                        .error(R.drawable.not_available)
                        .into(target);

                person_image.setTag(target);


                if (castDetailModel.getName() != null)
                {
                    person_name.setText(castDetailModel.getName());
                }

                if (!castDetailModel.getBiography().isEmpty() && castDetailModel.getBiography() != null)
                {
                    person_biography.setText(castDetailModel.getBiography());
                }
                else
                {
                    person_biography.setVisibility(View.GONE);
                }

                if (castDetailModel.getBirthday() != null)
                {
                    person_birthday.setText(castDetailModel.getBirthday());
                }
                else
                {
                    person_birthday.setVisibility(View.GONE);
                    person_birthday_title.setVisibility(View.GONE);
                }
                if (castDetailModel.getDeathday() != null)
                {
                    person_deathday.setText(String.valueOf(castDetailModel.getDeathday()));
                }
                else
                {
                    person_deathday.setVisibility(View.GONE);
                    person_deathday_title.setVisibility(View.GONE);
                }
                if (castDetailModel.getPlaceOfBirth() != null)
                {
                    person_birthplace.setText(castDetailModel.getPlaceOfBirth());
                }
                else
                {
                    person_birthplace.setVisibility(View.GONE);
                    person_birthplace_title.setVisibility(View.GONE);
                }
                if (castDetailModel.getHomepage() != null)
                {
                    person_homepage.setText(castDetailModel.getHomepage());
                }
                else
                {
                    person_homepage.setVisibility(View.GONE);
                    person_homepage_title.setVisibility(View.GONE);
                }


                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                pDialog.hide();

            }
        });

        AppSingleton.getInstance(getContext()).addToRequestQueue(stringRequest2, tag_json_obj);
    }

}
