package com.lehaine.ldtktest;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

// https://gist.github.com/mgsx-dev/ff693b8d83e6d07f88b0aaf653407e5a
public class PixelPerfectViewport extends FitViewport {

    public PixelPerfectViewport(float worldWidth, float worldHeight, Camera camera) {
        super(worldWidth, worldHeight, camera);
    }

    @Override
    public void update(int screenWidth, int screenHeight, boolean centerCamera) {

        // get the min screen/world rate from width and height
        float wRate = screenWidth / getWorldWidth();
        float hRate = screenHeight / getWorldHeight();
        float rate = Math.min(wRate, hRate);

        // round it down and limit to one
        int iRate = Math.max(1, MathUtils.floor(rate));

        // compute rounded viewport dimension
        int viewportWidth = (int) getWorldWidth() * iRate;
        int viewportHeight = (int) getWorldHeight() * iRate;

        // Center.
        setScreenBounds((screenWidth - viewportWidth) / 2, (screenHeight - viewportHeight) / 2, viewportWidth, viewportHeight);

        apply(centerCamera);
    }
}