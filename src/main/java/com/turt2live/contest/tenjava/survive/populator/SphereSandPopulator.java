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

import java.util.Random;

/**
 * Adds a small sand landing (with cacti) to the top of a sphere.
 *
 * @author turt2live
 */
public class SphereSandPopulator extends SpherePopulator {

    /**
     * Creates a new sand sphere populator
     *
     * @param chance the chance that this will occur on any given sphere between 0 and 1
     */
    public SphereSandPopulator(double chance) {
        super(chance);
    }

    @Override
    protected boolean populate(World world, Chunk chunk, Random random, Location center, int radius) {
        // We're going to create a level platform first (of sand...)
        capSphere(Material.SAND, 0, center, radius);

        Location platformStart = center.add(0, radius + 2, 0);

        for (int z = -radius; z < radius; z++) {
            for (int x = -radius; x < radius; x++) {
                if (random.nextDouble() < 0.05) {
                    Location cactiLocation = platformStart.clone().add(x, 0, z);

                    // We'll build a cacti here
                    int height = random.nextInt(6) + 2; // 2 - 8 high

                    for (int h = 0; h < height; h++) {
                        // Check safety of position
                        Block b1 = cactiLocation.clone().add(1, 0, 0).getBlock();
                        Block b2 = cactiLocation.clone().add(-1, 0, 0).getBlock();
                        Block b3 = cactiLocation.clone().add(0, 0, 1).getBlock();
                        Block b4 = cactiLocation.clone().add(0, 0, -1).getBlock();

                        if (!all(Material.AIR, b1, b2, b3, b4)) {
                            break;
                        }

                        cactiLocation.getBlock().setType(Material.CACTUS);
                        cactiLocation = cactiLocation.add(0, 1, 0);
                    }
                }
            }
        }

        return true;
    }
}
