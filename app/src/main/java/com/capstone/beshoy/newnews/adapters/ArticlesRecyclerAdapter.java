package com.capstone.beshoy.newnews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.capstone.beshoy.newnews.R;
import com.capstone.beshoy.newnews.classes.Article;
import com.capstone.beshoy.newnews.interfaces.ClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by bisho on 12-May-17.
 */

public class ArticlesRecyclerAdapter extends RecyclerView.Adapter<ArticlesRecyclerAdapter.ViewHolder> {

    private ArrayList<Article> articles;
    private Context mContext;
    private ClickListener clickListener;

    public ArticlesRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return 1;
        else return 2;
    }

    @Override
    public ArticlesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view ;
        if(viewType == 1)
            view = inflater.inflate(R.layout.main_article_item, parent, false);
        else
            view = inflater.inflate(R.layout.article_item, parent, false);
        return new ArticlesRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticlesRecyclerAdapter.ViewHolder holder, int position) {
        String article_image = articles.get(position).getImageURL();
        String article_title = articles.get(position).getTitle();
        String article_date  = articles.get(position).getPublishedAt();
        Picasso.with(mContext)
                .load(article_image)
                .error(R.drawable.image_error)
                .into(holder.article_image);
        holder.article_title.setText(article_title);
        if(!article_date.equals("null"))
            holder.article_date.setText(article_date);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView article_image;
        TextView article_title;
        TextView article_date;

        public ViewHolder(View itemView) {
            super(itemView);
            article_image = (ImageView) itemView.findViewById(R.id.article_image);
            article_title = (TextView) itemView.findViewById(R.id.article_title);
            article_date  = (TextView) itemView.findViewById(R.id.article_date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.articleClicked(articles.get(getAdapterPosition()));
        }
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
