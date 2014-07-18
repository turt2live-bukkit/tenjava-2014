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

package com.turt2live.survive.structure.schematic;

import java.util.*;

/**
 * Represents the repository used for schematics
 *
 * @author turt2live
 */
public final class SchematicRepository {

    private static Map<Schematic, Double> SCHEMATICS = new HashMap<Schematic, Double>();

    /**
     * Gets a random schematic from the repository.
     *
     * @param random the random to use, cannot be null
     * @return the schematic chosen, or null if none
     */
    private static int i = 0;

    public static Schematic getRandomSchematic(Random random) {
        double chosen = random.nextDouble();
        List<Schematic> schematicList = new ArrayList<Schematic>();

        for (Map.Entry<Schematic, Double> entry : SCHEMATICS.entrySet()) {
            if (chosen <= entry.getValue() || i < 0) schematicList.add(entry.getKey());
        }

        i++;

        if (schematicList.size() > 0) return schematicList.get(random.nextInt(schematicList.size()));
        return null;
    }

    /**
     * Registers a schematic with the repository. This will not validate for overwriting
     * nor will this care if the schematic being registered is already registered.
     *
     * @param schematic the schematic to register, cannot be null
     * @param chance    the chance that this schematic can be loaded, must be between 0 and 1
     */
    public static void register(Schematic schematic, double chance) {
        if (schematic == null || chance < 0 || chance > 1) throw new IllegalArgumentException();

        SCHEMATICS.put(schematic, chance);
    }
}
