package org.kw906plugin.catchTail.commands;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.kw906plugin.catchTail.CatchTail;
import org.bukkit.plugin.java.JavaPlugin;

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
    public int netherWorldBorderSize = 6250;
    public int endWorldBorderSize = 3000;
    private final FileConfiguration config = getPlugin(CatchTail.class).getConfig();

    public int timer = 0;

    public Configure() {
        getPlugin(CatchTail.class).saveDefaultConfig();

        checkStringValue("world_name", worldName);
        overworld = getWorld(Objects.requireNonNull(config.getString("world_name")));

        checkStringValue("nether_name", netherName);
        nether = getWorld(Objects.requireNonNull(config.getString("nether_name")));

        checkStringValue("end_name", endName);
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
        }
    }

    public void checkStringValue(String path, String value) {
        if (!config.contains(path)) {
            config.set(path, value);
        }
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
}
