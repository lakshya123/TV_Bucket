package com.example.wuntu.tv_bucket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.wuntu.tv_bucket.Fragments.CastViewListFragment;
import com.example.wuntu.tv_bucket.Models.Cast;

import java.util.ArrayList;

public class CastViewActivity extends AppCompatActivity
{
    ArrayList<Cast> FullCastList = new ArrayList<>();
    CastViewListFragment castViewListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_view);
        FrameLayout frameLayout;
        frameLayout = (FrameLayout) findViewById(R.id.container);
        castViewListFragment = new CastViewListFragment();

        FullCastList = getIntent().getParcelableArrayListExtra("LIST");
        Toast.makeText(this, FullCastList.get(0).getName() + "", Toast.LENGTH_SHORT).show();

        getSupportFragmentManager().beginTransaction().add(R.id.container,castViewListFragment).commit();





    }
}
