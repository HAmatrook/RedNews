package com.hawraa.rednews;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hawraa.rednews.Adapters.NewsAdapter;
import com.hawraa.rednews.Models.NewsModel;

import java.util.List;

import timber.log.Timber;

public class RecentNewsFragment extends Fragment implements NewsAdapter.OnNewsAdapterClickListener {
    public RecyclerView mRecyclerView;
    public NewsAdapter mAdapter;
    public List<NewsModel> mFavoriteNewsList;
    public static OnNClickListener onClickListener;

    public RecentNewsFragment() { }

    public interface OnNClickListener {
        void onArticleSelected(NewsModel item);
    }

    @Override
    public void onArticleSelected(NewsModel item) {
        onClickListener.onArticleSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_recent_news, container, false);
        mRecyclerView= rootView.findViewById(R.id.news_list_rv);
        setRecyclerView();
        return rootView;
    }

    private void setRecyclerView( ) {
        int columnCount = 2;
        StaggeredGridLayoutManager sglm =
                new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(sglm);
        mRecyclerView.setHasFixedSize(true);

//        if(MainActivity.currentTab.equalsIgnoreCase("Favorite New")){
//            System.out.println("================="+"Favorite");
//            NewsViewModel viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
//            viewModel.getFavorite().observe(this, new Observer<List<NewsModel>>() {
//                @Override
//                public void onChanged(@Nullable List<NewsModel> mNewsList) {
//                    mFavoriteNewsList.addAll(mNewsList);
//                    setRecyclerView();
//                    mAdapter = new NewsAdapter(mFavoriteNewsList, new NewsAdapter.OnNewsAdapterClickListener() {
//                        @Override
//                        public void onArticleSelected(NewsModel item) {
//                            Timber.d("A card position was selected");
//                            onClickListener.onArticleSelected(item);
//                        }
//                    });
//                }
//            });
        //}else {
            mAdapter = new NewsAdapter(WelcomeUI.mRecentNewsList, new NewsAdapter.OnNewsAdapterClickListener() {
                @Override
                public void onArticleSelected(NewsModel item) {
                    Timber.d("A card position was selected");
                    onClickListener.onArticleSelected(item);
                }
            });
        //}
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNClickListener) {
            onClickListener = (OnNClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onClickListener= null;
    }


}
