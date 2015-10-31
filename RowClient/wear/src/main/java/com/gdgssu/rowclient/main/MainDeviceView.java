package com.gdgssu.rowclient.main;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdgssu.rowclient.R;

public class MainDeviceView extends FrameLayout implements WearableListView.OnCenterProximityListener {

    private ColorMatrix matrix;
    private ColorMatrixColorFilter filter;

    final ImageView image;
    final TextView text;

    public MainDeviceView(Context context) {
        super(context);
        View.inflate(context, R.layout.list_item, this);
        image = (ImageView) findViewById(R.id.image);
        text = (TextView) findViewById(R.id.text);

        setColorFilter();

    }

    private void setColorFilter() {
        matrix = new ColorMatrix();
        matrix.setSaturation(0);
        filter = new ColorMatrixColorFilter(matrix);
    }


    @Override
    public void onCenterPosition(boolean b) {

        //Animation example to be ran when the view becomes the centered one
        image.animate().scaleX(1.7f).scaleY(1.7f).alpha(1);
        image.getDrawable().setColorFilter(null);
        text.animate().scaleX(1f).scaleY(1f).alpha(1);

    }

    @Override
    public void onNonCenterPosition(boolean b) {

        //Animation example to be ran when the view is not the centered one anymore
        image.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
        image.getDrawable().setColorFilter(filter);
        text.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);

    }
}
