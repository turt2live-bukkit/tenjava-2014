/*******************************************************************************
 * Copyright (C) 2014 Travis Ralston (turt2live)
 *
 * This software is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package com.turt2live.contest.tenjava.survive.structure;

import org.bukkit.Material;

import java.util.Random;

/**
 * Represents a sphere
 *
 * @author turt2live
 */
public abstract class Sphere implements Structure {

    /**
     * A random object to be used
     */
    protected static Random random = new Random();

    /**
     * Generates a template sphere to use. The array will contain a AIR for "use this" and
     * a null for "should be nothing".
     *
     * @param radius the radius of the sphere
     *
     * @return the generated array
     */
    protected Material[][][] generateTemplate(int radius) {
        Material[][][] template = new Material[radius * 2][radius * 2][radius * 2];

        // For sanity
        int cx = radius;
        int cy = radius;
        int cz = radius;

        for (int y = 0; y < template.length; y++) {
            for (int z = 0; z < template[y].length; z++) {
                for (int x = 0; x < template[y][z].length; x++) {
                    Material[] xDim = template[y][z];

                    // Test for circle
                    if (inRadius(cx, cy, cz, x, y, z, radius)) {
                        xDim[x] = Material.AIR;
                    }
                }
            }
        }

        return template;
    }

    /**
     * Determines whether or not a coordinate is in a specified radius
     *
     * @param sx the center x
     * @param sy the center y
     * @param sz the center z
     * @param dx the testing x
     * @param dy the testing y
     * @param dz the testing z
     * @param r  the radius
     *
     * @return true if the test point is within the radius, false otherwise
     */
    protected boolean inRadius(int sx, int sy, int sz, int dx, int dy, int dz, int r) {
        // s = center
        // d = desired
        int rsq = r * r;

        int a = Math.abs(sx - dx);
        int b = Math.abs(sy - dy);
        int c = Math.abs(sz - dz);

        int asq = a * a;
        int bsq = b * b;
        int csq = c * c;

        return asq + bsq + csq < rsq;
    }

}
