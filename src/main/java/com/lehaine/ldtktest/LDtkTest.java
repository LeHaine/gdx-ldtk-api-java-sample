package com.lehaine.ldtktest;

import com.lehaine.ldtk.LDtkProject;
import com.lehaine.ldtk.LayerAutoLayer;
import com.lehaine.ldtk.Point;

import java.util.List;

@LDtkProject(ldtkFileLocation = "sample.ldtk", name = "World")
public class LDtkTest {

    public static void main(String[] args) {
        // create new LDtk world
        World world = new World();
        world.load();

        // get a level
        World.WorldLevel level = world.getAllLevels().get(0);

        // iterate over a layers tiles
        for (LayerAutoLayer.AutoTile tile : level.getLayerCavern_background().getAutoTiles()) {
            // logic for handling the tile
            int x = tile.getRenderX();
        }

        // iterate over entities
        for (World.EntityMob mob : level.getLayerEntities().getAllMob()) {
            World.MobType type = mob.type;
            List<Point> patrolPoint = mob.getPatrol();
            int health = mob.getHealth();
            System.out.println(health);
        }

        for (World.EntityItem item : level.getLayerEntities().getAllItem()) {
            World.Items type = item.getType();
            System.out.println(type.name());
            if (type == World.Items.Pickaxe) {
                // spawn pickaxe
            }

        }
    }
}
