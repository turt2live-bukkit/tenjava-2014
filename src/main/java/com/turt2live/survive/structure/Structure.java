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

import com.turt2live.survive.util.Point3D;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.Random;

/**
 * Represents a single large structure in the world
 *
 * @author turt2live
 */
public interface Structure {

    /**
     * Gets the percentage chance that this structure can be created. The returned
     * value should be within 0 and 1. Any other values are assumed to be "never
     * generate".
     *
     * @return the percentage chance this structure can be created
     */
    public double getPercentChance();

    /**
     * Generates this structure at the specified location. All arguments are assumed
     * to be non-null.
     *
     * @param world  the world to generate this structure in
     * @param chunk  the applicable chunk
     * @param random the applicable random
     * @param center the center location to build this structure in
     *
     * @return the size of the generated object (where axis = dimension size)
     */
    public abstract Vector generate(World world, Chunk chunk, Random random, Point3D center);
}
