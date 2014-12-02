package com.aurozhkov.cyclemap;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aurozhkov.cyclemap.loader.TileLoader;

/**
 * Created by anton on 30.11.14.
 */
public class MapView extends ViewGroup {

    private int touchX;
    private int touchY;

    private MapProjection projection;

    private TileLoader tileLoader = null;

    public MapView(Context context) {
        super(context);
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        projection = new MapProjection(w, h);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = (int) event.getX();
                touchY = (int) event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                projection.updateCenter((int) (event.getX() - touchX), (int) (event.getY() - touchY));
                touchX = (int) event.getX();
                touchY = (int) event.getY();
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                projection.updateCenter((int) (event.getX() - touchX), (int) (event.getY() - touchY));
                invalidate();
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int index = 0;
        int tileFromX = projection.getTileFromX();
        int tileFromY = projection.getTileFromY();
        for (int tileX = tileFromX; tileX < projection.getTileToX() + 1; tileX++) {
            for (int tileY = tileFromY; tileY < projection.getTileToY() + 1; tileY++) {
                ImageView imageView = null;
                while (index < getChildCount() && imageView == null) {
                    imageView = (ImageView) getChildAt(index++);
                }
                if (imageView == null) {
                    imageView = new ImageView(getContext());
                    imageView.setLayoutParams(new LayoutParams(MapProjection.TILE_SIZE, MapProjection.TILE_SIZE));
                    addView(imageView);
                    index++;
                }
                imageView.layout(
                        -projection.getFirstPaddingX() + (tileX - tileFromX) * MapProjection.TILE_SIZE,
                        -projection.getFirstPaddingY() + (tileY - tileFromY) * MapProjection.TILE_SIZE,
                        -projection.getFirstPaddingX() + (tileX - tileFromX + 1) * MapProjection.TILE_SIZE,
                        -projection.getFirstPaddingY() + (tileY - tileFromY + 1) * MapProjection.TILE_SIZE);
                loadTile(tileX, tileY, imageView);
            }
        }
        for (int i = index; i < getChildCount(); i++) {
            removeView(getChildAt(i));
        }
        super.dispatchDraw(canvas);
    }

    private void loadTile(int tileX, int tileY, ImageView imageView) {
        if (tileLoader != null) {
            tileLoader.loadTile(imageView, tileX, tileY);
        }
    }

    public void setTileLoader(TileLoader tileLoader) {
        this.tileLoader = tileLoader;
        invalidate();
    }
}
