package com.aurozhkov.cyclemap.loader;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by anton on 30.11.14.
 */
public class PicassoTileLoader implements TileLoader {

    @Override
    public void loadTile(ImageView imageView, int x, int y) {
        final StringBuilder stringBuilder = new StringBuilder(BASE_URL);
        stringBuilder.append(centerX + x).append("/").append(centerY + y).append(".png");
        final Picasso picasso = Picasso.with(imageView.getContext());
        picasso.setLoggingEnabled(true);
        picasso.load(stringBuilder.toString()).into(imageView);
    }
}
