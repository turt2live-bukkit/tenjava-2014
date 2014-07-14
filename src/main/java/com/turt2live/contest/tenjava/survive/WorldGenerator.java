package com.turt2live.contest.tenjava.survive;

import com.google.common.collect.ImmutableList;
import com.turt2live.contest.tenjava.survive.structure.Structure;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.List;
import java.util.Random;

/**
 * The world generator used to create a skyblock-ish world with more
 * challenges and benefits. Challenges include the spacing between
 * blocks is not even as well as the types of blocks spawned. Benefits
 * include large spheres of materials with diamonds included in the
 * center.
 *
 * @author turt2live
 */
public class WorldGenerator extends ChunkGenerator {

    private int spawnY = 128;
    private int[] randomIds = new int[] {
            Material.DIRT.getId(),
            Material.STONE.getId(),
            Material.WOOD.getId(),
            Material.SPONGE.getId(),
            Material.GLASS.getId(),
            Material.LOG.getId(),
            Material.WOOL.getId(),
            Material.ICE.getId()
    };
    private int[] semiRareIds = new int[] {
            Material.IRON_BLOCK.getId(),
            Material.GOLD_BLOCK.getId(),
            Material.COAL_ORE.getId(),
            Material.IRON_ORE.getId(),
            Material.LAPIS_ORE.getId(),
            Material.REDSTONE_ORE.getId(),
            Material.GLOWSTONE.getId(),
            Material.NETHERRACK.getId(),
            Material.OBSIDIAN.getId()
    };
    private int[] rareIds = new int[] {
            Material.DIAMOND_BLOCK.getId(),
            Material.COAL_BLOCK.getId(),
            Material.LAPIS_BLOCK.getId(),
            Material.REDSTONE_BLOCK.getId(),
            Material.LAPIS_ORE.getId(),
            Material.BOOKSHELF.getId()
    };

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return ImmutableList.<BlockPopulator>of(new WorldPopulator(spawnY));
    }

    @Override
    public byte[][] generateBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
        // generates a chunk
        byte[][] blocks = new byte[world.getMaxHeight() / 16][];
        boolean spawnChunk = x == 0 && z == 0;

        if (spawnChunk) {
            // We'll generate an island for them
            setRange(8, spawnY - 4, 8, 11, spawnY - 3, 11, Material.STONE.getId(), blocks);
            setBlock(9, spawnY - 4, 9, Material.BEDROCK.getId(), blocks); // So they don't fall..

            /*
             7  8  9  10 11
            [x][ ][ ][ ][x] 7
            [ ]         [ ] 8
            [ ]         [ ] 9
            [ ]         [ ] 10
            [x][ ][ ][ ][x] 11
             */

            // Pillars
            setRange(7, spawnY - 4, 7, 8, spawnY, 8, Material.LOG.getId(), blocks);
            setRange(7, spawnY - 4, 11, 8, spawnY, 12, Material.LOG.getId(), blocks);
            setRange(11, spawnY - 4, 7, 12, spawnY, 8, Material.LOG.getId(), blocks);
            setRange(11, spawnY - 4, 11, 12, spawnY, 12, Material.LOG.getId(), blocks);

            // Set walls
            setRange(8, spawnY - 4, 7, 11, spawnY - 1, 8, Material.DIRT.getId(), blocks);
            setRange(7, spawnY - 4, 8, 8, spawnY - 1, 11, Material.DIRT.getId(), blocks);
            setRange(11, spawnY - 4, 8, 12, spawnY - 1, 11, Material.DIRT.getId(), blocks);
            setRange(8, spawnY - 4, 11, 11, spawnY - 1, 12, Material.DIRT.getId(), blocks);
        } else {
            Structure structure = StructureRepository.getRandomStructure(random);

            // TODO: Handle structures which are larger than a chunk
            if (structure != null) {
                // We need to choose a suitable Y location
                int minY = 32;
                int cy = random.nextInt(world.getMaxHeight() - minY - minY) + minY; // Keep within world bounds

                // For now we'll take the first 16 blocks in each row, if possible
                int[][] data = structure.generate();
                for (int level = 0; level < data.length; level++) {
                    int[] zDim = data[level];

                    for (int xIndex = 0; xIndex < (zDim.length > 16 ? 16 : zDim.length); xIndex++) {
                        setBlock(xIndex, cy + level, level, zDim[xIndex], blocks);
                    }
                }
            }
        }

        return blocks;
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 8.5, spawnY, 8.5);
    }

    private void setRange(int sx, int sy, int sz, int dx, int dy, int dz, int id, byte[][] chunk) {
        for (int x = sx; x < dx; x++) {
            for (int y = sy; y < dy; y++) {
                for (int z = sz; z < dz; z++) {
                    setBlock(x, y, z, id, chunk);
                }
            }
        }
    }

    // This is a slightly modified version of the method found in the javadocs for ChunkGenerator.
    // jkcclemens permitted use
    private void setBlock(int x, int y, int z, int id, byte[][] chunk) {
        if (chunk[y >> 4] == null) {
            chunk[y >> 4] = new byte[4096];
        }
        chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (byte) id;
    }

    // This is a slightly modified version of the method found in the javadocs for ChunkGenerator.
    // jkcclemens permitted use
    private byte getBlock(int x, int y, int z, byte[][] chunk) {
        if (chunk[y >> 4] == null) {
            return (byte) 0;
        }
        return chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x];
    }
}
