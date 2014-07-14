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

import org.bukkit.Material;

/**
 * Represents a sphere which is of a raw material
 *
 * @author turt2live
 */
public class RawMaterialSphere extends Sphere {

    protected int materialId;
    protected int minRadius, maxRadius;
    protected double percentChance;

    /**
     * Creates a new raw material sphere
     *
     * @param material      the material to use, cannot be null
     * @param minRadius     the minimum radius to use, cannot be 0 or negative
     * @param maxRadius     the maximum radius to use, cannot be smaller than the minimum
     * @param percentChance the percentage chance to spawn this sphere, must be between 0 and 1 inclusive
     */
    public RawMaterialSphere(Material material, int minRadius, int maxRadius, double percentChance) {
        if (material == null)
            throw new IllegalArgumentException("A material must be supplied");
        if (minRadius <= 0)
            throw new IllegalArgumentException("Minimum radius must be >=1");
        if (maxRadius < minRadius)
            throw new IllegalArgumentException("Maximum radius must be larger than or equal to the minimum radius");
        if (percentChance < 0 || percentChance > 1)
            throw new IllegalArgumentException("The percentage chance must be within 0 and 1 inclusive");

        this.materialId = material.getId();
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
        this.percentChance = percentChance;
    }

    /**
     * Uses a radius of 3 -> 7
     *
     * @see #RawMaterialSphere(org.bukkit.Material, int, int, double)
     */
    public RawMaterialSphere(Material material, double percentChance) {
        this(material, 3, 7, percentChance);
    }

    @Override
    public double getPercentChance() {
        return percentChance;
    }

    @Override
    public int[][] generate() {
        int radius = random.nextInt(maxRadius - minRadius) + minRadius;

        int[][] template = generateTemplate(radius);
        if (materialId == 1) return template; // Save SOME time

        for (int[] z : template) {
            for (int x = 0; x < z.length; x++) {
                if (z[x] == 1) z[x] = materialId;
            }
        }

        return template;
    }
}
