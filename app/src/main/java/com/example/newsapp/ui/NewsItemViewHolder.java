package com.example.newsapp.ui;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;

public class NewsItemViewHolder extends RecyclerView.ViewHolder {

    private TextView mTitleTile;
    private TextView mDescriptionTile;
    private TextView mAuthorTile;
    private TextView mPublishedAtTile;
    private View mRootView;
    private CheckBox mCheckBox;

    public NewsItemViewHolder(@NonNull View itemView) {
        super(itemView);
        mRootView = itemView.getRootView();
        mTitleTile = itemView.findViewById(R.id.title_tile);
        mDescriptionTile = itemView.findViewById(R.id.description_tile);
        mAuthorTile = itemView.findViewById(R.id.author_tile);
        mPublishedAtTile = itemView.findViewById(R.id.published_at_tile);
        mCheckBox = itemView.findViewById(R.id.offline_checkbox);
    }

    public View getRootView() {
        return mRootView;
    }

    public TextView getTitleTile() {
        return mTitleTile;
    }

    public TextView getDescriptionTile() {
        return mDescriptionTile;
    }

    public TextView getAuthorTile() {
        return mAuthorTile;
    }

    public TextView getPublishedAtTile() {
        return mPublishedAtTile;
    }

    public CheckBox getCheckBox() {
        return mCheckBox;
    }
}
