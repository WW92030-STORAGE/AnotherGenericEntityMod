package com.notasmr.darkness.util;

import java.awt.*;
import java.net.URI;

public class Reference {
    public static final String MODID = "darkness";
    public static final String VERSION = "1.0";

    public static final double DEG = 180.0 / Math.PI;
    public static final double TAU = 2.0 * Math.PI;
    public static final double EPSILON = 0.000000001;

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
}
