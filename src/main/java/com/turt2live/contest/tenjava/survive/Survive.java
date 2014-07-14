package com.turt2live.contest.tenjava.survive;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class. Does nothing but delegate.
 *
 * @author turt2live
 */
public class Survive extends JavaPlugin implements Listener {

    private static Survive instance;

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(new GameListener(this), this);

        getServer().addRecipe(new ShapelessRecipe(new ItemStack(Material.FLINT_AND_STEEL)).addIngredient(4, Material.WOOD).addIngredient(1, Material.IRON_INGOT));
        getServer().addRecipe(new ShapelessRecipe(new ItemStack(Material.FLINT_AND_STEEL)).addIngredient(1, Material.COBBLESTONE).addIngredient(1, Material.IRON_INGOT));
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);

        instance = null;
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new WorldGenerator();
    }

    /**
     * Gets the current instance of the plugin. May return null
     * if not yet initialized.
     *
     * @return the current instance of the plugin.
     */
    public static Survive getInstance() {
        return instance;
    }

}
