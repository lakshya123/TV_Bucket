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
import com.example.wuntu.tv_bucket.Fragments.SeasonEpisodesFragment;
import com.example.wuntu.tv_bucket.Fragments.SeasonView;
import com.example.wuntu.tv_bucket.Models.Cast;
import com.example.wuntu.tv_bucket.Models.TvSeasons;

import java.util.ArrayList;

public class CastViewActivity extends AppCompatActivity
{
    ArrayList<Cast> fullCastList;
    CastViewListFragment castViewListFragment;
    Toolbar toolbar;
    String check;
    CastViewFragment castViewFragment;
    Integer id;
    ArrayList<TvSeasons> seasonsArrayList;

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

        fullCastList = new ArrayList<>();
        seasonsArrayList = new ArrayList<>();
        castViewListFragment = new CastViewListFragment();
        castViewFragment = new CastViewFragment();

        if (getIntent().getStringExtra("EVENT") != null)
        {
            check = getIntent().getStringExtra("EVENT");
        }





        if (check.equals("TOUCH EVENT") )
        {
            id = getIntent().getIntExtra("ID",0);
            setViewtoCastView(id);
        }
        else if (check.equals("FULL LIST CAST"))
        {
            fullCastList = getIntent().getParcelableArrayListExtra("LIST");
            setViewtoCastList();
        }
        else if (check.equals("VIEW_SEASONS"))
        {

            setViewtoSeasonList();

        }
        else if (check.equals("SEASON_EPISODES"))
        {
            setViewtoSeasonEpisodes();
        }
    }

    private void setViewtoSeasonEpisodes()
    {
        Integer ID = getIntent().getIntExtra("ID",0);
        Integer SEASON_NUM = getIntent().getIntExtra("SEASON_NUM",0);
        SeasonEpisodesFragment seasonEpisodesFragment = new SeasonEpisodesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ID",ID);
        bundle.putInt("SEASON_NUM",SEASON_NUM);
        seasonEpisodesFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,seasonEpisodesFragment).commit();
    }

    public void setViewtoSeasonList()
    {
        seasonsArrayList = getIntent().getParcelableArrayListExtra("SEASONS_LIST");
        SeasonView seasonView = new SeasonView();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("SEASON_LIST",seasonsArrayList);
        seasonView.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,seasonView).commit();
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
        //Toast.makeText(this, fullCastList.get(0).getName() + " ", Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("FULL CREW LIST",fullCastList);
        castViewListFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,castViewListFragment,"CAST VIEW LIST FRAGMENT").commit();
    }
}
