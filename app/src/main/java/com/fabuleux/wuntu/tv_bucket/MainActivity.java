package com.fabuleux.wuntu.tv_bucket;



import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.fabuleux.wuntu.tv_bucket.Adapters.ExpandListAdapter;
import com.fabuleux.wuntu.tv_bucket.Fragments.MoviesMainFragment;
import com.fabuleux.wuntu.tv_bucket.Fragments.SearchFragment;
import com.fabuleux.wuntu.tv_bucket.Fragments.TvMainFragment;
import com.fabuleux.wuntu.tv_bucket.Utils.UrlConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    ArrayList<String> listDataHeader;
    HashMap<String,List<String>> listDataChild;
    ExpandableListView expListView;
    ExpandListAdapter listAdapter;
    UrlConstants urlConstants = UrlConstants.getSingletonRef();
    MoviesMainFragment moviesMainFragment = null;
    TvMainFragment tvMainFragment = null;
    DrawerLayout drawer;
    SearchView searchView = null;
    ActionBarDrawerToggle toggle;
    SearchFragment searchFragment;
    FrameLayout frameLayout;
    AppBarLayout.LayoutParams params;
    private int lastExpandedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout) findViewById(R.id.framelayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();

        searchFragment = new SearchFragment();


        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("TV Bucket");
            getSupportActionBar().setElevation(0);
        }



        enableExpandableList();

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        toggle.syncState();

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        Intent intent  = getIntent();

       /* if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            Toast.makeText(this, query +"", Toast.LENGTH_SHORT).show();
        }*/



    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        moviesMainFragment = new MoviesMainFragment();
        tvMainFragment = new TvMainFragment();
        adapter.addFragment(moviesMainFragment, getResources().getString(R.string.movies));
        adapter.addFragment(tvMainFragment, getResources().getString(R.string.tv_series));
        viewPager.setAdapter(adapter);
    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);


        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }

        assert searchView != null;
        final ImageView searchClose = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);



        searchView.setQueryHint(getResources().getString(R.string.search_hint));

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSupportActionBar() != null)
                {
                    getSupportActionBar().setDisplayShowTitleEnabled(false);
                }

                viewPager.setVisibility(GONE);
                tabLayout.setVisibility(GONE);
                toggle.setDrawerIndicatorEnabled(false);

                frameLayout.setVisibility(View.VISIBLE);
                params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, searchFragment).commit();
            }
        });

        searchClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (searchView.getQuery().length() > 1)
                {
                    //Toast.makeText(MainActivity.this, searchView.getQuery().length() + "", Toast.LENGTH_SHORT).show();
                    searchView.setQuery("",false);
                }
                else
                {
                    //Toast.makeText(MainActivity.this, " length is 1", Toast.LENGTH_SHORT).show();
                    viewPager.setVisibility(View.VISIBLE);
                    tabLayout.setVisibility(View.VISIBLE);
                    toggle.setDrawerIndicatorEnabled(true);
                    params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                            | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
                    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                        getSupportFragmentManager().popBackStack();
                    }
                    getSupportFragmentManager().beginTransaction().remove(searchFragment).commit();
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayShowTitleEnabled(true);
                    }


                    frameLayout.setVisibility(GONE);

                    searchView.onActionViewCollapsed();
                }

            }
        });




        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


                searchFragment = new SearchFragment();
                String query1 = query.replaceAll(" ","%20");

                Bundle bundle = new Bundle();
                bundle.putString("QUERY",query1);
                searchFragment.setArguments(bundle);

                //  Toast.makeText(MainActivity.this, MySuggestionProvider.AUTHORITY + "", Toast.LENGTH_SHORT).show();
                /*SearchRecentSuggestions suggestions = new SearchRecentSuggestions(MainActivity.this,
                        MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
                suggestions.saveRecentQuery(query, null);*/

                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, searchFragment).commit();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty())
                {
                    searchFragment = new SearchFragment();
                    String query1 = newText.replaceAll(" ","%20");
                    Bundle bundle = new Bundle();
                    bundle.putString("QUERY",query1);
                    searchFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, searchFragment).commit();
                }
                else
                {
                    searchFragment = new SearchFragment();
                    String query1 = newText.replaceAll(" ","%20");
                    Bundle bundle = new Bundle();
                    bundle.putString("QUERY",query1);
                    searchFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, searchFragment).commit();
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (frameLayout.getVisibility() == View.VISIBLE)
        {
            viewPager.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            toggle.setDrawerIndicatorEnabled(true);
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                    | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);

            if (getSupportActionBar() != null)
            {
                getSupportActionBar().setDisplayShowTitleEnabled(true);
            }


            frameLayout.setVisibility(GONE);

            searchView.onActionViewCollapsed();
        }
        else {
            super.onBackPressed();
        }
    }

    private void enableExpandableList() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        expListView = (ExpandableListView) findViewById(R.id.left_drawer);

        prepareListData(listDataHeader, listDataChild);
        listAdapter = new ExpandListAdapter(this, listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.expandGroup(0);

        expListView.expandGroup(1);

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {

                return true;
            }

        });
        // Listview Group expanded listener
       /* expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    expListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });*/


        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition)
            {
            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {


                if (groupPosition == 0)
                {
                    switch (childPosition) {
                        case 0:
                            viewPager.setCurrentItem(0);
                            moviesMainFragment.prepareOnlineData(urlConstants.URL_popular_movies, 1);
                            drawer.closeDrawer(GravityCompat.START);
                            break;
                        case 1:
                            viewPager.setCurrentItem(0);
                            moviesMainFragment.prepareOnlineData(urlConstants.URL_top_rated_movies, 1);
                            drawer.closeDrawer(GravityCompat.START);
                            break;
                        case 2:
                            viewPager.setCurrentItem(0);
                            moviesMainFragment.prepareOnlineData(urlConstants.URL_upcoming_movies, 1);
                            drawer.closeDrawer(GravityCompat.START);
                            break;
                        case 3:
                            viewPager.setCurrentItem(0);
                            moviesMainFragment.prepareOnlineData(urlConstants.URL_now_playing_movies, 1);
                            drawer.closeDrawer(GravityCompat.START);
                            break;
                        default:
                            viewPager.setCurrentItem(0);
                            moviesMainFragment.prepareOnlineData(urlConstants.URL_popular_movies,1);
                            drawer.closeDrawer(GravityCompat.START);
                            break;
                    }
                }
                else if (groupPosition == 1)
                {
                    switch (childPosition)
                    {
                        case 0:
                            viewPager.setCurrentItem(1);
                            drawer.closeDrawer(GravityCompat.START);
                            tvMainFragment.prepareOnlineData(urlConstants.URL_popular_tv_shows,1);
                            break;
                        case 1:
                            viewPager.setCurrentItem(1);
                            tvMainFragment.prepareOnlineData(urlConstants.URL_top_rated_tv_shows,1);
                            drawer.closeDrawer(GravityCompat.START);
                            break;
                        case 2:
                            viewPager.setCurrentItem(1);
                            drawer.closeDrawer(GravityCompat.START);
                            tvMainFragment.prepareOnlineData(urlConstants.URL_tv_on_air,1);
                            break;
                        case 3:
                            viewPager.setCurrentItem(1);
                            drawer.closeDrawer(GravityCompat.START);
                            tvMainFragment.prepareOnlineData(urlConstants.URL_tv_airing_today,1);
                            break;
                    }
                }

                return false;
            }
        });}

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void prepareListData(List<String> listDataHeader, Map<String,
            List<String>> listDataChild) {


        // Adding child data
        listDataHeader.add("Movies");
        listDataHeader.add("TV Shows");
//        listDataHeader.add("People");

        // Adding child data
        List<String> top = new ArrayList<>();
        top.add("Popular");
        top.add("Top Rated");
        top.add("Upcoming");
        top.add("Now Playing");


        List<String> mid = new ArrayList<>();
        mid.add("Popular");
        mid.add("Top Rated");
        mid.add("On TV");
        mid.add("Airing Today");

       /* List<String> bottom = new ArrayList<>();
        bottom.add("Popular People");
*/


        listDataChild.put(listDataHeader.get(0), top);
        listDataChild.put(listDataHeader.get(1), mid);
        // listDataChild.put(listDataHeader.get(2), bottom);
    }
}
