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

import com.turt2live.contest.tenjava.survive.StructureRepository;
import com.turt2live.contest.tenjava.survive.structure.Sphere;
import com.turt2live.contest.tenjava.survive.structure.Structure;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the populator for generating spheres. This also contains
 * references to other sphere populators to make the spheres pretty.
 *
 * @author turt2live
 */
public class SphereIncludePopulator extends BlockPopulator {

    private List<SpherePopulator> populatorList = new ArrayList<SpherePopulator>();

    public SphereIncludePopulator() {
        populatorList.add(new SphereSandPopulator(0.05));
    }

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        if (chunk.getX() == 0 && chunk.getZ() == 0) return; // Spawn chunk, skip

        // Sphere generation
        Structure structure = StructureRepository.getRandomStructure(random, Sphere.class);

        if (structure != null) {
            // We need to choose a suitable Y location
            int minY = 32;
            int cy = random.nextInt(world.getMaxHeight() - minY - minY) + minY; // Keep within world bounds

            Material[][][] data = structure.generate();

            int radius = data.length / 2;
            int cx = random.nextInt(16);
            int cz = random.nextInt(16);

            Location center = new Location(world, chunk.getX() * 16 + cx, cy, chunk.getZ() * 16 + cz);

            for (int sy = 0; sy < data.length; sy++) {
                Material[][] zData = data[sy];
                for (int sz = 0; sz < zData.length; sz++) {
                    Material[] xData = zData[sz];
                    for (int sx = 0; sx < xData.length; sx++) {
                        if (xData[sx] == null) continue;

                        int x = sx - radius;
                        int y = sy - radius;
                        int z = sz - radius;

                        Location l = center.clone().add(x, y, z);
                        Chunk c = l.getChunk();
                        if (!c.isLoaded()) c.load();

                        l.getBlock().setType(xData[sx]);
                    }
                }
            }

            for (SpherePopulator populator : populatorList) {
                populator.populate(world, random, chunk, center, radius);
            }
        }
    }
}
