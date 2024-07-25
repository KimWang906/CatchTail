package org.kw906plugin.catchTail;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.kw906plugin.catchTail.commands.Configure;
import org.kw906plugin.catchTail.commands.GameController;
import org.kw906plugin.catchTail.events.*;

import java.time.Duration;
import java.util.Objects;

public final class CatchTail extends JavaPlugin {
    public final static String version = "1.0.0";
    public final static String name = "CatchTail";
    public static Configure config;
    public static TickListener tickListener;

    @Override
    public void onEnable() {
        // Plugin startup logic
        config = new Configure();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new EntityDamage(), this);
        pm.registerEvents(new EntityDamageByEntity(), this);
        pm.registerEvents(new PlayerTracking(), this);
        pm.registerEvents(new PlayerMovement(), this);
        Objects.requireNonNull(getCommand("catch-tail")).setExecutor(new GameController());
        getLogger().info("CatchTail 플러그인이 활성화 되었습니다.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("CatchTail 플러그인이 비활성화 되었습니다.");
        saveConfig();
    }
}
