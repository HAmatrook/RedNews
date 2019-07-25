package com.hawraa.rednews;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.hawraa.rednews.Models.NewsModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements RecentNewsFragment.OnNClickListener, FavoriteNewsFragment.OnNClickListener {

    public static final int REQUEST_INVITE = 49 ;

    private ViewPager mViewPager;
    public static final String EXTRA_SELECTED_ITEM = "selected item";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.mToolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        mViewPager = (ViewPager)findViewById(R.id.tab_viewPager);
        if (mViewPager != null){
            setupViewPager(mViewPager);
        }


        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}

        });

    }

    @Override
    public void onArticleSelected(NewsModel item) {
        Intent intent = new Intent(this, ArticleDetailsActivity.class);
        intent.putExtra(EXTRA_SELECTED_ITEM,item);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }



    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new RecentNewsFragment(), getString(R.string.recent_news));
       // adapter.addFrag(new RecentNewsFragment(), "Favorite New");
        adapter.addFrag(new FavoriteNewsFragment(),  getString(R.string.favorite_news));
        viewPager.setAdapter(adapter);
    }

    public void onShareClicked(View view) {
        onInviteClicked();
    }

    private void onInviteClicked() {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }
    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private final List<Fragment> mFragmentList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title){
            mFragmentTitleList.add(title);
            mFragmentList.add(fragment);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }
    }

}
