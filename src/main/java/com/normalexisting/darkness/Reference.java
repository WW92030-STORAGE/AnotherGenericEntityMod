package com.normalexisting.darkness;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;

public class Reference {
    public static final String MODID = "darkness";
    public static final float SPEED = 0.375f;
    public static final int RANGE = 128;
    public static final double DEG = 180.0 / Math.PI;
    public static final double TAU = 2.0 * Math.PI;
    public static final double EPSILON = 0.000000001;
    public static final int MAX_PER_PLAYER = 4;

    public static double rem(double a, double m) {
        double res = a % m;
        while (a < 0) a += m;
        return a % m;
    }

    public static double atan(double dx, double dy) {
        double res = Math.atan2(dx, dy);
        //	if (dx < 0) res += Math.PI;
        res = (res % TAU) + 10 * TAU;
        return res % TAU;
    }

    public static void open(String u) {
        try {
            Desktop d = Desktop.getDesktop();
            d.browse(new URI(u));
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static ArrayList<Entity> aabb(Entity e, int x1, int y1, int z1, int x2, int y2, int z2) {
        BlockPos bp1 = new BlockPos(x1, y1, z1);
        BlockPos bp2 = new BlockPos(x2, y2, z2);
        AABB aabb = new AABB(bp1, bp2);
        ArrayList<Entity> things = (ArrayList<Entity>) e.level().getEntities(e, aabb);
        for (int i = 0; i < things.size(); i++) {
            if (things.get(i).equals(e)) {
                things.remove(i);
                break;
            }
        }
        return things;
    }
    public static ArrayList<Entity> aabb(Entity e, double x1, double y1, double z1, double x2, double y2, double z2) {
        return aabb(e, (int)Math.floor(x1), (int)Math.floor(y1), (int)Math.floor(z1), (int)Math.ceil(x2), (int)Math.ceil(y2), (int)Math.ceil(z2));
    }
}
