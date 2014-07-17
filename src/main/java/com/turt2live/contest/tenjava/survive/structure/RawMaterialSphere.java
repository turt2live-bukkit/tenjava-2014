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
import org.bukkit.Material;
import org.bukkit.World;

/**
 * Represents a sphere which is of a raw material
 *
 * @author turt2live
 */
public class RawMaterialSphere extends Sphere {

    protected Material materialId;
    protected boolean withDiamond = false;

    /**
     * Creates a new raw material sphere
     *
     * @param material      the material to use, cannot be null
     * @param minRadius     the minimum radius to use, cannot be 0 or negative
     * @param maxRadius     the maximum radius to use, cannot be smaller than the minimum
     * @param percentChance the percentage chance to spawn this sphere, must be between 0 and 1 inclusive
     * @param withDiamond   if true, a diamond block will be spawned in the middle of the sphere
     */
    public RawMaterialSphere(Material material, int minRadius, int maxRadius, double percentChance, boolean withDiamond) {
        super(minRadius, maxRadius);
        setPercentChance(percentChance);

        if (material == null)
            throw new IllegalArgumentException("A material must be supplied");

        this.materialId = material;
        this.withDiamond = withDiamond;
    }

    /**
     * Uses withDiamond=false
     *
     * @see #RawMaterialSphere(org.bukkit.Material, int, int, double, boolean)
     */
    public RawMaterialSphere(Material material, int minRadius, int maxRadius, double percentChance) {
        this(material, minRadius, maxRadius, percentChance, false);
    }

    /**
     * Uses a radius of 3 -> 7
     *
     * @see #RawMaterialSphere(org.bukkit.Material, int, int, double)
     */
    public RawMaterialSphere(Material material, double percentChance) {
        this(material, 3, 7, percentChance);
    }

    /**
     * Uses a radius of 3 -> 7
     *
     * @see #RawMaterialSphere(org.bukkit.Material, int, int, double, boolean)
     */
    public RawMaterialSphere(Material material, double percentChance, boolean withDiamond) {
        this(material, 3, 7, percentChance, withDiamond);
    }

    @Override
    protected void setBlock(Point3D block, Point3D center, int radius, boolean isCenter, World world) {
        Material material = materialId;
        if (isCenter && withDiamond) material = Material.DIAMOND_BLOCK;

        world.getBlockAt(block.getX(), block.getY(), block.getZ()).setType(material);
    }
}
