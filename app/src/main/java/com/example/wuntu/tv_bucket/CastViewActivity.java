package com.example.wuntu.tv_bucket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.wuntu.tv_bucket.Fragments.CastViewFragment;
import com.example.wuntu.tv_bucket.Fragments.CastViewListFragment;
import com.example.wuntu.tv_bucket.Models.Cast;

import java.util.ArrayList;

public class CastViewActivity extends AppCompatActivity
{
    ArrayList<Cast> FullCastList = new ArrayList<>();
    CastViewListFragment castViewListFragment;
    Toolbar toolbar;
    String check;
    CastViewFragment castViewFragment;
    Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setContentInsetStartWithNavigation(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSupportFragmentManager().getBackStackEntryCount()>0)
                {
                    getSupportFragmentManager().popBackStack();
                }
                else finish();

            }
        });
        castViewListFragment = new CastViewListFragment();
        castViewFragment = new CastViewFragment();

        check = getIntent().getStringExtra("EVENT");
        FullCastList = getIntent().getParcelableArrayListExtra("LIST");
        id = getIntent().getIntExtra("ID",0);

        if (check.equals("TOUCH EVENT") )
        {
            setViewtoCastView(id);
        }
        else if (check.equals("FULL LIST CAST"))
        {
            setViewtoCastList();
        }
    }

    public void setViewtoCastView(Integer id)
    {
        Bundle bundle = new Bundle();
        bundle.putInt("ID",id);
        castViewFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,castViewFragment,"CAST VIEW FRAGMENT").commit();
    }

    public void setViewtoCastList()
    {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("FULL CREW LIST",FullCastList);
        castViewListFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,castViewListFragment,"CAST VIEW LIST FRAGMENT").commit();
    }
}
