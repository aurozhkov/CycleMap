package com.aurozhkov.cyclemap.cache;

import android.graphics.Bitmap;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by anton on 01.12.14.
 */
public class MemoryCache implements Cache {

    private static int MAX_CACHE_SIZE = 8 * 1024 * 1024;

    private static final Map<String, Bitmap> cache = Collections.synchronizedMap(new LinkedHashMap<String,Bitmap>());

    private int cacheSize = 0;

    @Override
    public void put(String url, Bitmap bitmap) {
        synchronized (this) {
            final int size = bitmap.getRowBytes() * bitmap.getHeight();
            if (cacheSize + size > MAX_CACHE_SIZE) {
                Iterator<Map.Entry<String, Bitmap>> iter = cache.entrySet().iterator();
                while (iter.hasNext() && cacheSize + size > MAX_CACHE_SIZE) {
                    Map.Entry<String, Bitmap> entry = iter.next();
                    cacheSize -= entry.getValue().getRowBytes() * entry.getValue().getHeight();
                    iter.remove();
                }
            }
            final Bitmap previous = cache.put(url, bitmap);
            cacheSize += previous == null ? size : size - previous.getHeight()*previous.getRowBytes();
        }
    }

    @Override
    public Bitmap get(String url) {
        return cache.get(url);
    }

    @Override
    public void clear() {
        cacheSize = 0;
        cache.clear();
    }
}
