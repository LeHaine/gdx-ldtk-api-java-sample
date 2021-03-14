package com.lehaine.ldtktest;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.lehaine.gdx.LDtkUtil;
import com.lehaine.ldtk.Entity;
import com.lehaine.ldtk.Level;
import org.lwjgl.opengl.GL20;

import java.util.Objects;

public class GdxTest implements ApplicationListener {

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private Texture tiles;
    private OrthographicCamera camera;
    private PixelPerfectViewport viewport;
    private final World world = new World();
    private World.WorldLevel testLevel;
    private TextureRegion bgImage;
    private final Vector2 temp = new Vector2();
    private final Vector2 velocity = new Vector2();
    private final int gridSize = 8;

    @Override
    public void create() {
        world.load();
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        tiles = new Texture(Gdx.files.internal("Cavernas_by_Adam_Saltsman.png"));
        camera = new OrthographicCamera();
        viewport = new PixelPerfectViewport(480, 270, camera);
        loadLevel(0);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            loadLevel(0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            loadLevel(1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            loadLevel(2);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            loadLevel(3);
        }

        velocity.setZero();

        int speed = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocity.y = speed;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocity.y = -speed;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.x = speed;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocity.x = -speed;
        }
        camera.translate(velocity);

        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        renderBgImage(spriteBatch, testLevel);
        testLevel.getLayerCavern_background().render(spriteBatch, tiles, testLevel.getPxHeight());
        testLevel.getLayerCollisions().render(spriteBatch, tiles, testLevel.getPxHeight());
        testLevel.getLayerCustom_tiles().render(spriteBatch, tiles, testLevel.getPxHeight());
        spriteBatch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        testLevel.getLayerEntities().getAllPlayer()
                .forEach(entityPlayer -> renderEntity(shapeRenderer, entityPlayer, Color.GREEN, gridSize, testLevel.getPxHeight()));
        testLevel.getLayerEntities().getAllMob()
                .forEach(entityMob -> renderEntity(shapeRenderer, entityMob, Color.RED, gridSize, testLevel.getPxHeight()));
        testLevel.getLayerEntities().getAllItem()
                .forEach(entityItem -> renderEntity(shapeRenderer, entityItem, Color.GOLD, gridSize, testLevel.getPxHeight()));
        shapeRenderer.end();
    }

    private void loadLevel(int levelIdx) {
        if (levelIdx < world.getAllLevels().size()) {
            testLevel = world.getAllLevels().get(levelIdx);
            if (testLevel.getHasBgImage()) {
                if (bgImage == null) {
                    bgImage = new TextureRegion(new Texture(Gdx.files.internal(Objects.requireNonNull(testLevel.getBgImageInfos()).getRelFilePath())));
                }
                Level.CropRect crop = Objects.requireNonNull(testLevel.getBgImageInfos()).getCropRect();
                bgImage.setRegion(crop.getX(), crop.getY(), crop.getW(), crop.getH());
            }
        }
        camera.position.set(testLevel.getPxWidth() / 2f, testLevel.getPxHeight() / 2f + 20f, camera.position.z);
    }

    private void renderEntity(ShapeRenderer shapeRenderer, Entity entity, Color color, int entitySize, int pxHeight) {
        LDtkUtil.toGdxPos(entity.getCx(), entity.getCy(), gridSize, pxHeight, temp);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(temp.x, temp.y, entitySize, entitySize);
    }


    private void renderBgImage(SpriteBatch spriteBatch, Level level) {
        if (level.getHasBgImage() && bgImage != null) {
            Level.LevelBgImage bgImageInfo = level.getBgImageInfos();
            spriteBatch.draw(
                    bgImage.getTexture(),
                    Objects.requireNonNull(bgImageInfo).getTopLeftX(),
                    bgImageInfo.getTopLeftY(),
                    0f,
                    0f,
                    bgImage.getRegionWidth(),
                    bgImage.getRegionHeight(),
                    bgImageInfo.getScaleX(),
                    bgImageInfo.getScaleY(),
                    0f,
                    bgImage.getRegionX(),
                    bgImage.getRegionY(),
                    bgImage.getRegionWidth(),
                    bgImage.getRegionHeight(),
                    false,
                    false);
        }
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
