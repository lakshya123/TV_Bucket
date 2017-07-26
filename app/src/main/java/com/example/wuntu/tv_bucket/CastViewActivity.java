package com.example.wuntu.tv_bucket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.wuntu.tv_bucket.Fragments.CastViewListFragment;
import com.example.wuntu.tv_bucket.Models.Cast;

import java.util.ArrayList;

public class CastViewActivity extends AppCompatActivity
{
    ArrayList<Cast> FullCastList = new ArrayList<>();
    CastViewListFragment castViewListFragment;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        castViewListFragment = new CastViewListFragment();

        FullCastList = getIntent().getParcelableArrayListExtra("LIST");

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("FULL CREW LIST",FullCastList);
        castViewListFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,castViewListFragment).commit();





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
