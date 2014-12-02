package com.aurozhkov.cyclemap;

import android.app.Application;

import dagger.ObjectGraph;

/**
 * Created by anton on 01.12.14.
 */
public class MapApplication extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(new MapModule(this));
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }
}
