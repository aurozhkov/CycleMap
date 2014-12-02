package com.aurozhkov.cyclemap.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by anton on 01.12.14.
 */
public class LoadTask implements Runnable {

    private final LoadTaskOptions options;

    public LoadTask(LoadTaskOptions options) {
        this.options = options;
    }

    @Override
    public void run() {
        options.lock.lock();
        if (needLoad()) {
            clearImage();
            Bitmap bitmap = null;
            if (options.memoryCache != null) {
                bitmap = options.memoryCache.get(options.url);
            }
            if (bitmap == null && options.diskCache != null) {
                bitmap = options.diskCache.get(options.url);
            }
            if (bitmap == null) {
                bitmap = loadFromNetwork();
            }
            if (bitmap != null) {
                if (options.diskCache != null) {
                    options.diskCache.put(options.url, bitmap);
                }
                if (options.memoryCache != null) {
                    options.memoryCache.put(options.url, bitmap);
                }
                display(bitmap);
            }
        }
        options.lock.unlock();
    }

    private void clearImage() {
        final ImageView imageView = options.imageView.get();
        if (imageView != null) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageDrawable(null);
                    imageView.setTag("");
                }
            });
        }
    }

    private boolean needLoad() {
        final ImageView imageView = options.imageView.get();
        return imageView != null && !options.url.equals(imageView.getTag());
    }

    private void display(final Bitmap bitmap) {
        final ImageView imageView = options.imageView.get();
        final String url = options.url;
        if (imageView != null) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                    imageView.setTag(url);
                }
            });
        }
    }

    private Bitmap loadFromNetwork() {
        final URL url;
        try {
            url = new URL(options.url);
            return BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return null;
    }
}
