package com.lehaine.ldtktest;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.lwjgl.opengl.GL20;

public class GdxTest implements ApplicationListener {

    private SpriteBatch spriteBatch;
    private Texture tiles;
    private Camera camera;
    private PixelPerfectViewport viewport;
    private final World world = new World();
    private final World.WorldLevel testLevel = world.getAllLevels().get(0);

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        tiles = new Texture(Gdx.files.internal("Cavernas_by_Adam_Saltsman.png"));
        camera = new OrthographicCamera();
        viewport = new PixelPerfectViewport(480, 270, camera);
        camera.translate(testLevel.getPxWidth() / 2f, testLevel.getPxHeight() / -2f, 0f);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        testLevel.getLayerBackground().render(spriteBatch, tiles);
        testLevel.getLayerCollisions().render(spriteBatch, tiles);
        testLevel.getLayerCustom_tiles().render(spriteBatch, tiles);
        spriteBatch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(960, 540);
        new Lwjgl3Application(new GdxTest(), config);
    }
}
