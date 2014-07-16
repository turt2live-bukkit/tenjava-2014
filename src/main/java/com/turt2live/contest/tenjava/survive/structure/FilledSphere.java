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

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.Random;

/**
 * Represents a sphere that is filled with another material
 *
 * @author turt2live
 */
public class FilledSphere extends Sphere {

    protected Material shell, fill;
    protected int minRadius, maxRadius;
    protected double percentChance;
    protected boolean withDiamond = false;

    /**
     * Creates a new filled sphere
     *
     * @param shell         the shell material, cannot be null
     * @param filler        the filling material, cannot be null
     * @param minRadius     the minimum radius, must be greater than 1
     * @param maxRadius     the maximum radius, must be greater than or equal to the minimum radius
     * @param percentChance the percentage chance this sphere can spawn, must be between 0 and 1 inclusively
     * @param withDiamond   if true, a diamond will be placed in the middle of the sphere, false otherwise
     */
    public FilledSphere(Material shell, Material filler, int minRadius, int maxRadius, double percentChance, boolean withDiamond) {
        if (shell == null)
            throw new IllegalArgumentException("A shell material must be supplied");
        if (filler == null)
            throw new IllegalArgumentException("A filler material must be supplied");
        if (minRadius <= 0)
            throw new IllegalArgumentException("Minimum radius must be >=1");
        if (maxRadius < minRadius)
            throw new IllegalArgumentException("Maximum radius must be larger than or equal to the minimum radius");
        if (percentChance < 0 || percentChance > 1)
            throw new IllegalArgumentException("The percentage chance must be within 0 and 1 inclusive");

        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
        this.percentChance = percentChance;
        this.shell = shell;
        this.fill = filler;
        this.withDiamond = withDiamond;
    }

    /**
     * Uses a radius of 3 -> 7
     *
     * @see #FilledSphere(org.bukkit.Material, org.bukkit.Material, int, int, double, boolean)
     */
    public FilledSphere(Material shell, Material filler, double percentChance, boolean withDiamond) {
        this(shell, filler, 3, 7, percentChance, withDiamond);
    }

    /**
     * Uses a radius of 3 -> 7 and no diamond block
     *
     * @see #FilledSphere(org.bukkit.Material, org.bukkit.Material, int, int, double, boolean)
     */
    public FilledSphere(Material shell, Material filler, double percentChance) {
        this(shell, filler, 3, 7, percentChance, false);
    }

    /**
     * Uses no diamond block
     *
     * @see #FilledSphere(org.bukkit.Material, org.bukkit.Material, int, int, double, boolean)
     */
    public FilledSphere(Material shell, Material filler, int minRadius, int maxRadius, double percentChance) {
        this(shell, filler, minRadius, maxRadius, percentChance, false);
    }

    @Override
    public double getPercentChance() {
        return percentChance;
    }

    @Override
    public Vector generate(World world, Chunk chunk, Random random, Location center) {
        int radius = maxRadius == minRadius ? maxRadius : random.nextInt(maxRadius - minRadius) + minRadius;

        int cx = center.getBlockX();
        int cy = center.getBlockY();
        int cz = center.getBlockZ();

        for (int y = -radius; y < radius; y++) {
            for (int z = -radius; z < radius; z++) {
                for (int x = -radius; x < radius; x++) {
                    int bx = x + cx;
                    int by = y + cy;
                    int bz = z + cz;

                    if (inRadius(cx, cy, cz, bx, by, bz, radius)) {
                        Material m = inRadius(cx, cy, cz, bx, by, bz, radius - 1) ? fill : shell;
                        if (withDiamond && y == 0 && z == 0 && x == 0)
                            m = Material.DIAMOND_BLOCK;

                        world.getBlockAt(bx, by, bz).setType(m);
                    }
                }
            }
        }

        return new Vector(radius * 2, radius * 2, radius * 2);
    }
}
