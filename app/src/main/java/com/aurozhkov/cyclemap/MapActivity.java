package com.aurozhkov.cyclemap;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.aurozhkov.cyclemap.loader.TileLoader;

import javax.inject.Inject;


public class MapActivity extends ActionBarActivity {

    @Inject
    TileLoader tileLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MapApplication) getApplication()).inject(this);
        final MapView mapView = new MapView(this);
        mapView.setTileLoader(tileLoader);
        setContentView(mapView);
    }
}
