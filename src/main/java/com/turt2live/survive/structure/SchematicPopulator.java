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

package com.turt2live.survive.structure;

import com.turt2live.survive.structure.schematic.Schematic;
import com.turt2live.survive.structure.schematic.SchematicRepository;
import com.turt2live.survive.util.Point3D;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

/**
 * Represents a structure that parses schematics
 *
 * @author turt2live
 */
public class SchematicPopulator extends BlockPopulator {

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        Schematic schematic = SchematicRepository.getRandomSchematic(random);

        if (schematic != null) {
            Point3D center = new Point3D(random.nextInt(16), random.nextInt(world.getMaxHeight() - schematic.getHeight()), random.nextInt(16));
            center = center.add(chunk.getX() * 16, 0, chunk.getZ() * 16);

            System.out.println(center);

            byte[] blocks = schematic.getBlockIds();
            byte[] data = schematic.getBlockData();

            for (int y = 0; y < schematic.getHeight(); y++) {
                for (int z = 0; z < schematic.getLength(); z++) {
                    for (int x = 0; x < schematic.getWidth(); x++) {
                        int ox = x - (schematic.getWidth() / 2);
                        int oy = y - (schematic.getHeight() / 2);
                        int oz = z - (schematic.getLength() / 2);

                        Point3D blockPoint = center.add(ox, oy, oz);
                        Block choice = world.getBlockAt(blockPoint.getX(), blockPoint.getY(), blockPoint.getZ());

                        int index = (schematic.getHeight() * y) + (schematic.getLength() * z) + (schematic.getWidth() * x);
                        byte bId = blocks[index];
                        byte bData = data[index];

                        choice.setTypeIdAndData(bId, bData, true);
                    }
                }
            }
        }
    }
}
