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

import com.turt2live.contest.tenjava.survive.util.Point3D;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.Random;

/**
 * Represents a sphere
 *
 * @author turt2live
 */
public abstract class Sphere implements Structure {

    protected final int maxRadius;
    protected final int minRadius;
    protected double percentChance = 0.0;

    public Sphere(int minRadius, int maxRadius) {
        if (minRadius <= 0 || maxRadius < minRadius) throw new IllegalArgumentException();

        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
    }

    /**
     * Sets the percent chance of this sphere being generated
     *
     * @param chance the percent chance. Must be between 0 and 1
     */
    protected void setPercentChance(double chance) {
        if (chance < 0 || chance > 1) throw new IllegalArgumentException();

        this.percentChance = chance;
    }

    @Override
    public double getPercentChance() {
        return percentChance;
    }

    /**
     * Determines if a point is within a circle's radius.
     * <p/>
     * This returns false if either point is null.
     *
     * @param center the sphere center, cannot be null
     * @param target the target point, cannot be null
     * @param radius the radius of the sphere, must be >0
     *
     * @return true if the point is within the radius, false otherwise
     */
    protected boolean inRadius(Point3D center, Point3D target, int radius) {
        if (radius <= 0) throw new IllegalArgumentException();
        return !(center == null || target == null) && center.distanceSquared(target) < radius * radius;
    }

    @Override
    public final Vector generate(World world, Chunk chunk, Random random, Point3D center) {
        int radius = maxRadius == minRadius ? maxRadius : random.nextInt(maxRadius - minRadius) + minRadius;

        doGeneration(radius, center, world);

        return new Vector(radius * 2, radius * 2, radius * 2);
    }

    /**
     * Runs the generation of the sphere, calling {@link #setBlock(com.turt2live.contest.tenjava.survive.util.Point3D, com.turt2live.contest.tenjava.survive.util.Point3D, int, boolean, org.bukkit.World)}
     * where possible.
     *
     * @param radius the radius of the sphere. must be >0
     * @param center the sphere center, cannot be null
     * @param world  the applicable world
     */
    protected void doGeneration(int radius, Point3D center, World world) {
        if (radius <= 0) throw new IllegalArgumentException();

        for (int y = 0; y < radius; y++) {
            for (int x = 0; x < radius; x++) {
                for (int z = 0; z < radius; z++) {
                    Point3D block1 = center.add(x, y, z);

                    if (inRadius(center, block1, radius)) {
                        setBlock(block1, center, radius, center.equals(block1), world);

                        // If the first block is in the radius, so are all the others
                        for (int i = 1; i < 8; i++) { // i = 1 because i = 0 is already handled above
                            int mx = (i & 0x04) != 0 ? -x : x; // 0100
                            int my = (i & 0x02) != 0 ? -y : y; // 0010
                            int mz = (i & 0x01) != 0 ? -z : z; // 0001

                            Point3D block = center.add(mx, my, mz);
                            setBlock(block, center, radius, center.equals(block), world);
                        }
                    }
                }
            }
        }
    }

    /**
     * Sets a block in the world. This should only be called by {@link #doGeneration(int, com.turt2live.contest.tenjava.survive.util.Point3D, org.bukkit.World)}
     * and therefore should have no null arguments.
     *
     * @param block    the block location to set
     * @param center   the center of the sphere
     * @param radius   the radius of the sphere
     * @param isCenter true if the test block is the center
     * @param world    the applicable world
     */
    protected abstract void setBlock(Point3D block, Point3D center, int radius, boolean isCenter, World world);
}
