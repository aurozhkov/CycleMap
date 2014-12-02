package com.aurozhkov.cyclemap;

import android.graphics.Point;

/**
 * Created by anton on 30.11.14.
 */
public class MapProjection {

    public static final int TILE_SIZE = 128;
    public static final int TILE_COUNT = 100;

    private int screenWidth;
    private int screenHeight;

    private Point center;

    public MapProjection(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        center = new Point(TILE_COUNT / 2 * TILE_SIZE, TILE_COUNT / 2 * TILE_SIZE);
    }

    public int getTileFromX() {
        final int xLeftOffset = (center.x - screenWidth / 2) / TILE_SIZE;
        return ((center.x - screenWidth / 2) % TILE_SIZE == 0 ? xLeftOffset : xLeftOffset - 1) - TILE_COUNT / 2;
    }

    public int getTileToX() {
        return getTileFromX() + screenWidth / TILE_SIZE + 1;
    }

    public int getTileFromY() {
        final int yLeftOffset = (center.y - screenHeight / 2) / TILE_SIZE;
        return ((center.y - screenHeight / 2) % TILE_SIZE == 0 ? yLeftOffset : yLeftOffset - 1) - TILE_COUNT / 2;
    }

    public int getTileToY() {
        return getTileFromY() + screenHeight / TILE_SIZE + 1;
    }

    public void updateCenter(int offsetX, int offsetY) {
        int newCenterX = center.x - offsetX > 0 ? center.x - offsetX : 0;
        newCenterX = newCenterX < TILE_SIZE * TILE_COUNT ? newCenterX : TILE_SIZE * TILE_COUNT;
        int newCenterY = center.y - offsetY > 0 ? center.y - offsetY : 0;
        newCenterY = newCenterY < TILE_SIZE * TILE_COUNT ? newCenterY : TILE_SIZE * TILE_COUNT;
        center = new Point(newCenterX, newCenterY);
    }

    public int getFirstPaddingX() {
        return Math.abs((center.x - screenWidth / 2) % TILE_SIZE);
    }

    public int getFirstPaddingY() {
        return Math.abs((center.y - screenHeight / 2) % TILE_SIZE);
    }
}
