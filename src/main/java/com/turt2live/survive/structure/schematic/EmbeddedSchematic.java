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

import org.jnbt.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Represents a schematic loaded from a stream
 *
 * @author turt2live
 */
public class EmbeddedSchematic implements Schematic {

    private byte[] blockIds;
    private byte[] data;
    private int w, l, h;

    /**
     * Creates a new embedded schematic from a stream of a schematic file
     *
     * @param stream the stream to read from, cannot be null
     */
    public EmbeddedSchematic(InputStream stream) throws IOException {
        if (stream == null) throw new IllegalArgumentException();

        NBTInputStream is = new NBTInputStream(stream);

        CompoundTag root = (CompoundTag) is.readTag();
        Map<String, Tag> tags = root.getValue();

        w = ((ShortTag) tags.get("Width")).getValue();
        h = ((ShortTag) tags.get("Height")).getValue();
        l = ((ShortTag) tags.get("Length")).getValue();

        data = ((ByteArrayTag) tags.get("Data")).getValue();
        blockIds = ((ByteArrayTag) tags.get("Blocks")).getValue();

        is.close();
    }

    @Override
    public byte[] getBlockData() {
        return data;
    }

    @Override
    public byte[] getBlockIds() {
        return blockIds;
    }

    @Override
    public int getWidth() {
        return w;
    }

    @Override
    public int getHeight() {
        return h;
    }

    @Override
    public int getLength() {
        return l;
    }
}
