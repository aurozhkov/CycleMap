package com.aurozhkov.cyclemap;

import android.content.Context;

import com.aurozhkov.cyclemap.cache.DiskCache;
import com.aurozhkov.cyclemap.cache.MemoryCache;
import com.aurozhkov.cyclemap.loader.MyTileLoader;
import com.aurozhkov.cyclemap.loader.TileLoader;

import java.io.File;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anton on 01.12.14.
 */
@Module(
        injects = {MapActivity.class}
)
public class MapModule {

    private final Context context;

    public MapModule(Context context) {
        this.context = context;
    }

    @Provides
    File provideCacheRoot() {
        return context.getExternalCacheDir();
    }

    @Provides
    TileLoader provideTileLoader(File cacheRoot) {
        return new MyTileLoader.Builder()
                .setDiskCache(new DiskCache(cacheRoot))
                .setMemoryCache(new MemoryCache())
                .build();
    }
}
