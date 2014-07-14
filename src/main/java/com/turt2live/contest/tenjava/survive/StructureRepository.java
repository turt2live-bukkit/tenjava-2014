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

package com.turt2live.contest.tenjava.survive;

import com.turt2live.contest.tenjava.survive.structure.RawMaterialSphere;
import com.turt2live.contest.tenjava.survive.structure.Structure;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a repository of many structures
 *
 * @author turt2live
 */
public class StructureRepository {

    private static List<Structure> STRUCTURES = new ArrayList<Structure>();

    static {
        STRUCTURES.add(new RawMaterialSphere(Material.DIRT, 0.25));
        STRUCTURES.add(new RawMaterialSphere(Material.WOOD, 0.05));
        STRUCTURES.add(new RawMaterialSphere(Material.LOG, 0.05));
        STRUCTURES.add(new RawMaterialSphere(Material.IRON_BLOCK, 0.05));
        STRUCTURES.add(new RawMaterialSphere(Material.DIAMOND_BLOCK, 0.02));
        STRUCTURES.add(new RawMaterialSphere(Material.STONE, 0.10));
    }

    /**
     * Gets a random structure from the repository. This uses a simple method of
     * choosing a random value and creating a list of structures which fit the
     * generated number. From there it chooses a random value and returns that.
     * <p/>This may return null if no structure was generated.
     *
     * @param random the random to use, cannot be null
     *
     * @return the structure chosen, or null if none
     */
    public static Structure getRandomStructure(Random random) {
        if (random == null) throw new IllegalArgumentException("Must supply a random");

        double choice = random.nextDouble();
        List<Structure> choices = new ArrayList<Structure>();

        for (Structure struct : STRUCTURES) {
            if (struct.getPercentChance() >= 0 && struct.getPercentChance() <= 1 && choice <= struct.getPercentChance()) {
                choices.add(struct);
            }
        }

        if (choices.size() > 0) return choices.get(random.nextInt(choices.size()));
        return null;
    }
}
