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

package com.turt2live.survive;

import com.turt2live.survive.structure.FilledSphere;
import com.turt2live.survive.structure.RawMaterialSphere;
import com.turt2live.survive.structure.Structure;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a repository of many structures
 *
 * @author turt2live
 */
public final class StructureRepository {

    private static List<Structure> STRUCTURES = new ArrayList<Structure>();

    static {
        // Regular resource spheres
        STRUCTURES.add(new RawMaterialSphere(Material.DIRT, 6, 6, 0.15, true));
        STRUCTURES.add(new RawMaterialSphere(Material.GRASS, 6, 6, 0.15, true));
        STRUCTURES.add(new RawMaterialSphere(Material.WOOD, 6, 6, 0.05, true));
        STRUCTURES.add(new RawMaterialSphere(Material.LOG, 6, 6, 0.05, true));
        STRUCTURES.add(new RawMaterialSphere(Material.IRON_BLOCK, 0.05, true));
        STRUCTURES.add(new RawMaterialSphere(Material.DIAMOND_BLOCK, 0.02, true));
        STRUCTURES.add(new RawMaterialSphere(Material.STONE, 6, 6, 0.10, true));

        // Filled spheres
        STRUCTURES.add(new FilledSphere(Material.STONE, Material.STATIONARY_LAVA, 6, 6, 0.10, true));
        STRUCTURES.add(new FilledSphere(Material.STONE, Material.STATIONARY_WATER, 6, 6, 0.10, true));
        STRUCTURES.add(new FilledSphere(Material.DIRT, Material.STATIONARY_LAVA, 6, 6, 0.10, true));
        STRUCTURES.add(new FilledSphere(Material.DIRT, Material.STATIONARY_WATER, 6, 6, 0.10, true));
        STRUCTURES.add(new FilledSphere(Material.DIAMOND_BLOCK, Material.STATIONARY_LAVA, 6, 6, 0.10, true));
        STRUCTURES.add(new FilledSphere(Material.DIAMOND_BLOCK, Material.STATIONARY_WATER, 6, 6, 0.10, true));
    }

    /**
     * Gets a random structure from the repository. This uses a simple method of
     * choosing a random value and creating a list of structures which fit the
     * generated number. From there it chooses a random value and returns that.
     * <p/>This may return null if no structure was generated.
     *
     * @param random the random to use, cannot be null
     * @param types  the types to generate from. If null/empty, all are assumed.
     *
     * @return the structure chosen, or null if none
     */
    public static Structure getRandomStructure(Random random, Class<? extends Structure>... types) {
        if (random == null) throw new IllegalArgumentException();

        boolean all = types == null || types.length == 0;

        double choice = random.nextDouble();
        List<Structure> choices = new ArrayList<Structure>();

        for (Structure struct : STRUCTURES) {
            if (struct.getPercentChance() >= 0
                    && struct.getPercentChance() <= 1
                    && choice <= struct.getPercentChance()
                    && (all || is(struct, types))) {
                choices.add(struct);
            }
        }

        if (choices.size() > 0) return choices.get(random.nextInt(choices.size()));
        return null;
    }

    private static boolean is(Structure structure, Class[] types) {
        for (Class<?> c : types) {
            if (c.isInstance(structure)) return true;
        }
        return false;
    }
}
