package com.example.newsapp.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;

public class NewsItemViewHolder extends RecyclerView.ViewHolder {

    private TextView mTitleTile;
    private TextView mDescriptionTile;
    private TextView mAuthorTile;
    private TextView mPublishedAtTile;

    public NewsItemViewHolder(@NonNull View itemView) {
        super(itemView);

        mTitleTile = itemView.findViewById(R.id.title_tile);
        mDescriptionTile = itemView.findViewById(R.id.description_tile);
        mAuthorTile = itemView.findViewById(R.id.author_tile);
        mPublishedAtTile = itemView.findViewById(R.id.published_at_tile);
    }

    public TextView getTitleTile() {
        return mTitleTile;
    }

    public void setTitleTile(TextView mTitleTile) {
        this.mTitleTile = mTitleTile;
    }

    public TextView getDescriptionTile() {
        return mDescriptionTile;
    }

    public void setDescriptionTile(TextView mDescriptionTile) {
        this.mDescriptionTile = mDescriptionTile;
    }

    public TextView getAuthorTile() {
        return mAuthorTile;
    }

    public void setAuthorTile(TextView mAuthorTile) {
        this.mAuthorTile = mAuthorTile;
    }

    public TextView getmTitleTile() {
        return mTitleTile;
    }

    public TextView getmDescriptionTile() {
        return mDescriptionTile;
    }

    public TextView getmAuthorTile() {
        return mAuthorTile;
    }

    public TextView getmPublishedAtTile() {
        return mPublishedAtTile;
    }
}
