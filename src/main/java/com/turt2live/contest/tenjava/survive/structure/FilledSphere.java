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
 * Represents a sphere that is filled with another material
 *
 * @author turt2live
 */
public class FilledSphere extends Sphere {

    protected int shell, fill;
    protected int minRadius, maxRadius;
    protected double percentChance;
    protected boolean withDiamond = false;

    /**
     * Creates a new filled sphere
     *
     * @param shell         the shell material, cannot be null
     * @param filler        the filling material, cannot be null
     * @param minRadius     the minimum radius, must be greater than 1
     * @param maxRadius     the maximum radius, must be greater than or equal to the minimum radius
     * @param percentChance the percentage chance this sphere can spawn, must be between 0 and 1 inclusively
     * @param withDiamond   if true, a diamond will be placed in the middle of the sphere, false otherwise
     */
    public FilledSphere(Material shell, Material filler, int minRadius, int maxRadius, double percentChance, boolean withDiamond) {
        if (shell == null)
            throw new IllegalArgumentException("A shell material must be supplied");
        if (filler == null)
            throw new IllegalArgumentException("A filler material must be supplied");
        if (minRadius <= 0)
            throw new IllegalArgumentException("Minimum radius must be >=1");
        if (maxRadius < minRadius)
            throw new IllegalArgumentException("Maximum radius must be larger than or equal to the minimum radius");
        if (percentChance < 0 || percentChance > 1)
            throw new IllegalArgumentException("The percentage chance must be within 0 and 1 inclusive");

        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
        this.percentChance = percentChance;
        this.shell = shell.getId();
        this.fill = filler.getId();
        this.withDiamond = withDiamond;
    }

    /**
     * Uses a radius of 3 -> 7
     *
     * @see #FilledSphere(org.bukkit.Material, org.bukkit.Material, int, int, double, boolean)
     */
    public FilledSphere(Material shell, Material filler, double percentChance, boolean withDiamond) {
        this(shell, filler, 3, 7, percentChance, withDiamond);
    }

    /**
     * Uses a radius of 3 -> 7 and no diamond block
     *
     * @see #FilledSphere(org.bukkit.Material, org.bukkit.Material, int, int, double, boolean)
     */
    public FilledSphere(Material shell, Material filler, double percentChance) {
        this(shell, filler, 3, 7, percentChance, false);
    }

    /**
     * Uses no diamond block
     *
     * @see #FilledSphere(org.bukkit.Material, org.bukkit.Material, int, int, double, boolean)
     */
    public FilledSphere(Material shell, Material filler, int minRadius, int maxRadius, double percentChance) {
        this(shell, filler, minRadius, maxRadius, percentChance, false);
    }

    @Override
    public double getPercentChance() {
        return percentChance;
    }

    @Override
    public int[][][] generate() {
        int radius = maxRadius == minRadius ? maxRadius : random.nextInt(maxRadius - minRadius) + minRadius;

        int[][][] template = generateTemplate(radius);
        int[][][] innerTemplate = generateTemplate(radius - 1);

        for (int y = 0; y < template.length; y++) {
            for (int z = 0; z < template[y].length; z++) {
                for (int x = 0; x < template[y][z].length; x++) {
                    if (template[y][z][x] == 1) template[y][z][x] = innerTemplate[y][z][x] == 1 ? fill : shell;
                    if (withDiamond && y == 8 && z == 8 && x == 8)
                        template[y][z][x] = Material.DIAMOND_BLOCK.getId();
                }
            }
        }

        return template;
    }
}
