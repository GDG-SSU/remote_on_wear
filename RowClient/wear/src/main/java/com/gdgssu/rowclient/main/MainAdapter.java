package com.gdgssu.rowclient.main;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdgssu.rowclient.R;
import com.gdgssu.rowclient.model.Device;

import java.util.List;

/**
 * Created by lk on 15. 10. 31..
 */
public class MainAdapter extends WearableListView.Adapter {

    private Context mContext;
    private List<Device> deviceItems;

    public MainAdapter(Context mContext, List<Device> deviceItems) {
        this.mContext = mContext;
        this.deviceItems = deviceItems;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WearableListView.ViewHolder(new MainDeviceView(mContext));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        MainDeviceView SettingsItemView = (MainDeviceView) holder.itemView;
        final Device item = deviceItems.get(position);

        TextView textView = (TextView) SettingsItemView.findViewById(R.id.text);
        textView.setText(item.deviceName);

        final ImageView imageView = (ImageView) SettingsItemView.findViewById(R.id.image);
        imageView.setImageDrawable(item.deviceImg);
    }

    @Override
    public int getItemCount() {
        return deviceItems.size();
    }
}
