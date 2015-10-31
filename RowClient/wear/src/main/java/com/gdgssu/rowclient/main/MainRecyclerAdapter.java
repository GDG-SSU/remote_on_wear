package com.gdgssu.rowclient.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gdgssu.rowclient.R;

import java.util.List;

/**
 * Created by everyone on 15. 10. 31.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerHolder> {

    private List<String> mItems;
    private Context mContext;

    public MainRecyclerAdapter(List<String> items, Context context) {
        this.mItems = items;
        this.mContext = context;
    }

    @Override
    public MainRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_listrow, parent, false);

        return new MainRecyclerHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MainRecyclerHolder holder, final int position) {
        setItem(holder, position);
        holder.mRecycleHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "test"+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setItem(MainRecyclerHolder holder, int position) {
        String itemString = mItems.get(position);

        holder.mRecycleHolder.textView.setText(itemString);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
