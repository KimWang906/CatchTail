package org.kw906plugin.catchTail.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.kw906plugin.catchTail.CatchTail;
import org.bukkit.plugin.java.JavaPlugin;
import org.kw906plugin.catchTail.SendMessage;

import java.util.Objects;

import static org.bukkit.Bukkit.getWorld;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class Configure {
    public String worldName = "world";
    public String netherName = "world_nether";
    public String endName = "world_the_end";

    private World overworld = null;
    private World nether = null;
    private World theEnd = null;

    public int overworldWorldBorderSize = 50000;
    public int netherWorldBorderSize = 12500;
    public int endWorldBorderSize = 3000;
    private FileConfiguration config = getPlugin(CatchTail.class).getConfig();

    public int countDown = 5;

    public Configure() {
        getPlugin(CatchTail.class).saveDefaultConfig();

        checkIntValue("time.countdown", countDown);

        checkStringValue("worlds.world_name", worldName);
        overworld = getWorld(Objects.requireNonNull(config.getString("world_name")));

        checkStringValue("worlds.nether_name", netherName);
        nether = getWorld(Objects.requireNonNull(config.getString("nether_name")));

        checkStringValue("worlds.end_name", endName);
        theEnd = getWorld(Objects.requireNonNull(config.getString("end_name")));

        // WorldBorder Configs
        String overworldWorldBorderSizeKey = worldBorderConfig(MinecraftWorld.OVERWORLD, "worldborder_size");
        checkIntValue(overworldWorldBorderSizeKey, this.overworldWorldBorderSize);
        this.overworldWorldBorderSize = config.getInt(overworldWorldBorderSizeKey);

        String netherWorldBorderSizeKey = worldBorderConfig(MinecraftWorld.NETHER, "worldborder_size");
        checkIntValue(netherWorldBorderSizeKey, this.netherWorldBorderSize);
        this.netherWorldBorderSize = config.getInt(netherWorldBorderSizeKey);

        String theEndWorldBorderSizeKey = worldBorderConfig(MinecraftWorld.THE_END, "worldborder_size");
        checkIntValue(theEndWorldBorderSizeKey, this.endWorldBorderSize);
        this.endWorldBorderSize = config.getInt(theEndWorldBorderSizeKey);

        getPlugin(CatchTail.class).saveConfig();
    }

    public enum MinecraftWorld {
        OVERWORLD,
        NETHER,
        THE_END
    }

    public void checkIntValue(String path, int value) {
        if (!config.contains(path)) {
            config.set(path, value);
            getPlugin(CatchTail.class).saveConfig();
        }
    }

    public void checkStringValue(String path, String value) {
        if (!config.contains(path)) {
            config.set(path, value);
            getPlugin(CatchTail.class).getLogger().info("Updated config value for path: " + path);
            getPlugin(CatchTail.class).saveConfig();
            getPlugin(CatchTail.class).getLogger().info("Saved config after update.");
        } else {
            getPlugin(CatchTail.class).getLogger().info("No change needed for path: " + path);
        }
    }

    public void applyConfig() {
        config.set("worlds.world_name", worldName);
        config.set("worlds.nether_name", netherName);
        config.set("worlds.end_name", endName);
        String overworldWorldBorderSizeKey = worldBorderConfig(MinecraftWorld.OVERWORLD, "worldborder_size");
        config.set(overworldWorldBorderSizeKey, overworldWorldBorderSize);
        String netherWorldBorderSizeKey = worldBorderConfig(MinecraftWorld.NETHER, "worldborder_size");
        config.set(netherWorldBorderSizeKey, netherWorldBorderSize);
        String theEndWorldBorderSizeKey = worldBorderConfig(MinecraftWorld.THE_END, "worldborder_size");
        config.set(theEndWorldBorderSizeKey, theEndWorldBorderSizeKey);

        getPlugin(CatchTail.class).saveConfig();
    }

    public String worldBorderConfig(MinecraftWorld type, String var) {
        String result = "";
        switch (type) {
            case OVERWORLD:
                result = "worldborder.overworld." + var;
                break;
            case NETHER:
                result = "worldborder.nether." + var;
                break;
            case THE_END:
                result = "worldborder.the-end." + var;
                break;
            default:
                break;
        }
        return result;
    }

    public World getOverworld() {
        return overworld;
    }

    public World getNether() {
        return nether;
    }

    public World getTheEnd() {
        return theEnd;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void setConfig(FileConfiguration config) {
        this.config = config;
    }
}
