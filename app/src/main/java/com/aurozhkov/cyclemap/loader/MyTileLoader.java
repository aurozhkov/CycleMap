package com.aurozhkov.cyclemap.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.aurozhkov.cyclemap.cache.Cache;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by anton on 01.12.14.
 */
public class MyTileLoader implements TileLoader {

    private static final Executor executor = Executors.newFixedThreadPool(6);
    private static final Map<String, ReentrantLock> locks = new WeakHashMap<String, ReentrantLock>();

    private Cache memoryCache;
    private Cache diskCache;

    MyTileLoader(Cache memoryCache, Cache diskCache) {
        this.memoryCache = memoryCache;
        this.diskCache = diskCache;
    }

    @Override
    public void loadTile(ImageView imageView, int x, int y) {
        final StringBuilder stringBuilder = new StringBuilder(BASE_URL);
        stringBuilder.append(centerX + x).append("/").append(centerY + y).append(".png");
        final String url = stringBuilder.toString();
        if (url.equals(imageView.getTag())) {
            return;
        }
        Bitmap bitmap = null;
        if (memoryCache != null && (bitmap = memoryCache.get(url)) != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        if (diskCache != null && (bitmap = diskCache.get(url)) != null) {
            memoryCache.put(url, bitmap);
            imageView.setImageBitmap(bitmap);
            return;
        }
        final LoadTaskOptions options = new LoadTaskOptions(
                getLock(url),
                diskCache,
                memoryCache,
                new WeakReference<ImageView>(imageView),
                url);
        executor.execute(new LoadTask(options));
    }

    private ReentrantLock getLock(String url) {
        ReentrantLock lock = locks.get(url);
        if (lock == null) {
            lock = new ReentrantLock();
            locks.put(url, lock);
        }
        return lock;
    }

    public static class Builder {

        private Cache memoryCache = null;
        private Cache diskCache = null;

        public Builder setMemoryCache(Cache memoryCache) {
            this.memoryCache = memoryCache;
            return this;
        }

        public Builder setDiskCache(Cache diskCache) {
            this.diskCache = diskCache;
            return this;
        }

        public MyTileLoader build() {
            return new MyTileLoader(memoryCache, diskCache);
        }
    }
}
