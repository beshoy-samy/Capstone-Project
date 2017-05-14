package com.capstone.beshoy.newnews.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.capstone.beshoy.newnews.R;
import com.capstone.beshoy.newnews.classes.Article;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

public class ArticleDetailsActivity extends AppCompatActivity {

    private ImageView article_image;
    private ImageButton share_article;
    private TextView article_date;
    private TextView article_title;
    private TextView article_description;
    private TextView article_author;
    private TextView article_source;
    private TextView full_article;
    private Article article;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        MobileAds.initialize(this, getString(R.string.banner_ad_unit_id));

        article_image = (ImageView) findViewById(R.id.article_image);
        share_article = (ImageButton) findViewById(R.id.share_article);
        article_date  = (TextView) findViewById(R.id.article_date);
        article_title = (TextView) findViewById(R.id.article_title);
        article_description = (TextView) findViewById(R.id.article_description);
        article_author= (TextView) findViewById(R.id.article_author);
        article_source= (TextView) findViewById(R.id.article_source);
        full_article  = (TextView) findViewById(R.id.full_article);
        mAdView = (AdView) findViewById(R.id.adView);

        article = getIntent().getExtras().getParcelable("article");

        Picasso.with(this)
                .load(article.getImageURL())
                .error(R.drawable.image_error)
                .into(article_image);
        share_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, article.getArticleURL());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        if(!article.getPublishedAt().equals("null"))
            article_date.setText(article.getPublishedAt());
        article_title.setText(article.getTitle());
        article_description.setText(article.getDescription());
        full_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent("android.intent.action.VIEW",
                        Uri.parse(article.getArticleURL()));
                startActivity(viewIntent);
            }
        });
        String author = "Author: "+article.getAuthor();
        article_author.setText(author);
        String source = "Source: "+article.getSource();
        article_source.setText(source);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


    }
}
