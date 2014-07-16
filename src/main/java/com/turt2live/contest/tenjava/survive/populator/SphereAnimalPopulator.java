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

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;

import java.util.Random;

/**
 * Represents a populator that spawns various animals on grass spheres
 *
 * @author turt2live
 */
public class SphereAnimalPopulator extends SpherePopulator {

    private static EntityType[] types = {
            EntityType.PIG,
            EntityType.WOLF,
            EntityType.COW,
            EntityType.SHEEP,
            EntityType.CHICKEN,
            EntityType.HORSE
    };

    private double chance;

    /**
     * Creates a new sphere animal populator. The percent chance for this class is
     * used only if the sphere is a grass sphere. A 100% chance is actually passed
     * to the underlying handler to unsure all grass spheres are parsed.
     *
     * @param chance the percentage chance this can take effect between 0 and 1
     */
    public SphereAnimalPopulator(double chance) {
        super(1.0);

        if (chance < 0 || chance > 1) throw new IllegalArgumentException("Chance must be between 0 and 1");

        this.chance = chance;
    }

    @Override
    protected boolean populate(World world, Chunk chunk, Random random, Location center, int radius) {
        Location top = center.add(0, radius - 1, 0);
        Block bTop = top.getBlock();

        if (bTop.getType() == Material.GRASS && random.nextDouble() < chance) {
            int minMobs = 6;
            int mobs = random.nextInt(minMobs) + minMobs;

            Location l = top.add(0, 1, 0);
            for (int i = 0; i < mobs; i++) {
                world.spawnEntity(l, types[random.nextInt(types.length)]);
            }

            return true;
        }

        return false;
    }
}
