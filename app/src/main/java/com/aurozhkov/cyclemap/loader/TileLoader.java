package com.aurozhkov.cyclemap.loader;

import android.widget.ImageView;

/**
 * Created by anton on 30.11.14.
 */
public interface TileLoader {

    static final String BASE_URL = "https://b.tile.thunderforest.com/cycle/16/";

    static final int centerX = 46131;
    static final int centerY = 20733;

    public void loadTile(ImageView imageView, int x, int y);
}
