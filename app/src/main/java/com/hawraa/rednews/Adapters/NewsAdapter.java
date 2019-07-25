package com.hawraa.rednews.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hawraa.rednews.Models.NewsModel;
import com.hawraa.rednews.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    public Context context;
    public TextView mTitle;
    public TextView mArticle;
    public ImageView mImage;
    private final List<NewsModel> mNewsList;
    private OnNewsAdapterClickListener mClickListener;

//    @BindView(R.id.news_img)
//    ImageView newsImg;
//    @BindView(R.id.news_title_tv)
//    TextView newsTitleTv;
//    @BindView(R.id.news_article_tv)
//    TextView newsArticleTv;

    public NewsAdapter(List<NewsModel> newsList, OnNewsAdapterClickListener clickListener) {
        this.mNewsList = newsList;
        this.mClickListener = clickListener;
    }

    public interface OnNewsAdapterClickListener {
        void onArticleSelected(NewsModel item);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item_news;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean AttachImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, AttachImmediately);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        if(mNewsList != null) {
            if (mNewsList.size() >= 15)
                return 15;
            return mNewsList.size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        //ViewHolder.bind(mNewsList.get(position), mClickListener);
        final NewsModel item = mNewsList.get(position);
        mTitle = viewHolder.itemView.findViewById(R.id.news_title_tv);
        mImage = viewHolder.itemView.findViewById(R.id.news_img);
        mArticle = viewHolder.itemView.findViewById(R.id.news_article_tv);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mClickListener.onArticleSelected(item);
            }
        });
        mTitle.setText(item.getTitle());
        if(!(item.getDescription().equalsIgnoreCase("null") || item.getDescription().isEmpty())){
            mArticle.setText(item.getDescription());
        }else{
            mArticle.setText(item.getContent());
        }
        if(item.getImgURL() != null) {
            Picasso.get()
                    .load(item.getImgURL())
                    .error(R.drawable.defulte_image)
                    .into(mImage);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
//            mTitle = newsTitleTv;
//            mImage = newsImg;
//            mArticle = newsArticleTv;
        }

         void bind(final NewsModel item, final OnNewsAdapterClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onArticleSelected(item);
                }
            });
//            mTitle.setText(item.getTitle());
//            mArticle.setText(item.getDescription().substring(0,30)+"...");
//            Picasso.get()
//                    .load(item.getImgURL())
//                    .into(mImage);
        }

    }
}
