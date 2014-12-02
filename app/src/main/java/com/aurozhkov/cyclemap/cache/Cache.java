package com.aurozhkov.cyclemap.cache;

import android.graphics.Bitmap;

/**
 * Created by anton on 01.12.14.
 */
public interface Cache {

    public void put(String url, Bitmap bitmap);

    public Bitmap get(String url);

    public void clear();
}
