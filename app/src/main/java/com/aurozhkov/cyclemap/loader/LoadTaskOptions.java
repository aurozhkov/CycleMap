package com.aurozhkov.cyclemap.loader;

import android.widget.ImageView;

import com.aurozhkov.cyclemap.cache.Cache;

import java.lang.ref.WeakReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by anton on 01.12.14.
 */
public class LoadTaskOptions {

    public final ReentrantLock lock;
    public final Cache diskCache;
    public final Cache memoryCache;
    public final WeakReference<ImageView> imageView;
    public final String url;

    public LoadTaskOptions(ReentrantLock lock, Cache diskCache, Cache memoryCache, WeakReference<ImageView> imageView, String url) {
        this.lock = lock;
        this.diskCache = diskCache;
        this.memoryCache = memoryCache;
        this.imageView = imageView;
        this.url = url;
    }
}
