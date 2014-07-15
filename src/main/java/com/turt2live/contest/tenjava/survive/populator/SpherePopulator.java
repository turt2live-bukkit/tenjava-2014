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

package com.turt2live.contest.tenjava.survive.populator;

import com.turt2live.contest.tenjava.survive.Survive;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Random;

/**
 * Represents a populator which generates things for spheres
 *
 * @author turt2live
 */
public abstract class SpherePopulator extends BlockPopulator {

    private static final String SPHERE_POPULATED_META = "SURVIVE.sphere.populated";

    private double chance;

    public SpherePopulator(double chance) {
        if (chance < 0 || chance > 1) throw new IllegalArgumentException();

        this.chance = chance;
    }

    @Override
    public final void populate(World world, Random random, Chunk chunk) {
        Location center = getSphereCenter(chunk);
        if (center != null && random.nextDouble() < chance) {
            Block bcenter = center.getBlock();

            if (!bcenter.hasMetadata(SPHERE_POPULATED_META)) {
                if (populate(world, chunk, random, center, getSphereRadius(center)))
                    bcenter.setMetadata(SPHERE_POPULATED_META, new FixedMetadataValue(Survive.getInstance(), true));
            }
        }
    }

    /**
     * Called when a sphere should be populated
     *
     * @param world  the applicable world
     * @param chunk  the applicable chunk
     * @param random the applicable random
     * @param center the sphere's center
     * @param radius the sphere's radius
     *
     * @return true if the sphere was populated, false otherwise
     */
    protected abstract boolean populate(World world, Chunk chunk, Random random, Location center, int radius);

    /**
     * Gets the center of the sphere
     *
     * @param chunk the chunk to look inside, assumed to be not null
     *
     * @return the location of a sphere in this chunk, or null if not found
     */
    protected Location getSphereCenter(Chunk chunk) {
        int cx = 8;
        int cz = 8;
        int streak = 0;

        for (int y = 0; y < chunk.getWorld().getMaxHeight(); y++) {
            Block block = chunk.getBlock(cx, y, cz);
            if (block.getType() == Material.AIR) {
                if (streak >= 3) {
                    if (getSphereRadius(block.getLocation().subtract(0, (streak / 2) + 1, 0)) > 0) {
                        return block.getLocation().subtract(0, (streak / 2) + 1, 0);
                    }
                }
                streak = 0;
            } else streak++;
        }

        return null;
    }

    /**
     * Gets the radius of the sphere
     *
     * @param location the center
     *
     * @return the radius of the sphere, or 0 if no sphere or null location
     */
    protected int getSphereRadius(Location location) {
        if (location == null) return 0;

        for (int i = 0; i < 8; i++) {
            Block b1 = location.clone().add(i, 0, 0).getBlock();
            Block b2 = location.clone().add(-i, 0, 0).getBlock();
            Block b3 = location.clone().add(0, i, 0).getBlock();
            Block b4 = location.clone().add(0, -i, 0).getBlock();
            Block b5 = location.clone().add(0, 0, i).getBlock();
            Block b6 = location.clone().add(0, 0, -i).getBlock();

            if (!allNot(Material.AIR, b1, b2, b3, b4, b5, b6)) {
                if (all(Material.AIR, b1, b2, b3, b4, b5, b6)) {
                    return i - 1;
                } else return 0; // Not a sphere
            }
        }

        return 0;
    }

    /**
     * Caps a sphere with a material, squaring the top off. This will always add at least
     * 1 layer on top of the sphere of the defined material. The 'extraLayers' can be used
     * to further increase that.
     *
     * @param capMaterial  the material to cap the sphere with, cannot be null
     * @param extraLayers  the number of extra layers to add to the sphere. Must be >= 0
     * @param sphereCenter the center of the sphere, cannot be null
     * @param radius       the radius of the sphere, must be >= 1
     */
    protected void capSphere(Material capMaterial, int extraLayers, Location sphereCenter, int radius) {
        if (capMaterial == null || extraLayers < 0 || sphereCenter == null || radius < 1)
            throw new IllegalArgumentException();

        // First we need to map how the sphere looks. This is so we can just add slices to it as needed.
        int[][] map = new int[16][16];
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                Block b = sphereCenter.clone().add(x - 8, 0, z - 8).getBlock();
                if (b.getType() != Material.AIR) {
                    map[x][z] = 1;
                }
            }
        }

        // Start capping!
        boolean onTop = false;
        int layersLeft = extraLayers;
        int y = 1; // We already used y=0 to determine the mapping

        while (!onTop || layersLeft > 0) {
            boolean wasAllCapped = true;

            for (int z = 0; z < 16; z++) {
                for (int x = 0; x < 16; x++) {
                    if (map[x][z] == 1) {
                        Block b = sphereCenter.clone().add(x - 8, y, z - 8).getBlock();
                        if (b.getType() == Material.AIR) b.setType(capMaterial);
                        else wasAllCapped = false;
                    }
                }
            }

            if (onTop) layersLeft--;
            if (wasAllCapped) onTop = true;
            y++;
        }
    }

    protected boolean allNot(Material material, Block... blocks) {
        for (Block block : blocks) {
            if (block.getType() == material) return false;
        }
        return true;
    }

    protected boolean all(Material material, Block... blocks) {
        for (Block block : blocks) {
            if (block.getType() != material) return false;
        }
        return true;
    }

}
