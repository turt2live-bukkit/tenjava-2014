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
     * Generates an array representation of block IDs for this structure. The array is
     * structured to be [y][z][x]. Therefore the following array layout could be used
     * to generate a simple arrow which points north (2 layers). The example block ID
     * is 1 for visual purposes only.
     * <pre>
     * {
     *   {
     *    {00100},
     *    {01010},
     *    {10001}
     *   },
     *   // Second layer is a mirror of the first layer
     *   {
     *    {00100},
     *    {01010},
     *    {10001}
     *   }
     * }
     * </pre>
     * <p/>
     * Note that as the Z increases, you approach closer to south. As X increases you
     * approach closer to east. As Y increases you approach closer to the sky.
     *
     * @return the generated structure
     */
    public int[][][] generate();
}
