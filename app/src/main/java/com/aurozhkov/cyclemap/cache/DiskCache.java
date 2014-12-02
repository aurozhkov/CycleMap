package com.aurozhkov.cyclemap.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by anton on 01.12.14.
 */
public class DiskCache implements Cache {

    private static final String CACHE_FOLDER_NAME = "cache";

    private File directory;
    private FileNamingStrategy namingStrategy = new HashFileNamingStrategy();

    public DiskCache(File root) {
        directory = new File(root, CACHE_FOLDER_NAME);
        directory.mkdirs();
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        final File file = new File(directory, namingStrategy.getNameForUrl(url));
        if (!saveToFile(bitmap, file)) {
            file.delete();
        }
    }

    private boolean saveToFile(Bitmap bitmap, File file) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Bitmap get(String url) {
        final File file = new File(directory, namingStrategy.getNameForUrl(url));
        if (file.exists()) {
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        return null;
    }

    @Override
    public void clear() {
        for (File file : directory.listFiles()) {
            file.delete();
        }
    }

    public void setFileNamingStrategy(FileNamingStrategy namingStrategy) {
        if (namingStrategy == null) {
            throw new IllegalStateException("namingStrategy can't be null");
        }
        this.namingStrategy = namingStrategy;
    }
}
