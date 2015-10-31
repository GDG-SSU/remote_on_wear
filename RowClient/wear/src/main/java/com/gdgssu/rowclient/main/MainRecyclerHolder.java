package com.gdgssu.rowclient.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gdgssu.rowclient.R;

/**
 * Created by everyone on 15. 10. 31.
 */
public class MainRecyclerHolder extends RecyclerView.ViewHolder {

    public MainRecycleHolder mRecycleHolder;

    static class MainRecycleHolder{
        public TextView textView;
    }

    public MainRecyclerHolder(View itemView) {
        super(itemView);
        setHolder(itemView);
    }

    private void setHolder(View itemView) {
        mRecycleHolder = new MainRecycleHolder();
        mRecycleHolder.textView = (TextView)itemView.findViewById(R.id.item_main_textview);
    }
}
