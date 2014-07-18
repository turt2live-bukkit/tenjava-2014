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

/**
     * Represents a schematic
     */
    public interface Schematic {

        /**
         * Gets the block data array
         *
         * @return the block data array
         */
        public byte[] getBlockData();

        /**
         * Gets the block ID array
         *
         * @return the block ID array
         */
        public byte[] getBlockIds();

        /**
         * Gets the width of this schematic
         *
         * @return the width (X)
         */
        public int getWidth();

        /**
         * Gets the height of this schematic
         *
         * @return the height (Y)
         */
        public int getHeight();

        /**
         * Gets the length of this schematic
         *
         * @return the length (Z)
         */
        public int getLength();
    }