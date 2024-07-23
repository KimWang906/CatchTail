package org.kw906plugin.catchTail;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.kw906plugin.catchTail.commands.Configure;
import org.kw906plugin.catchTail.commands.GameController;

public final class CatchTail extends JavaPlugin {
    public final static String version = "1.0.0";
    public final static String name = "CatchTail";
    public static Configure config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        config = new Configure();
        getCommand("catch-tail").setExecutor(new GameController());

        getLogger().info("CatchTail 플러그인이 활성화 되었습니다.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("CatchTail 플러그인이 비활성화 되었습니다.");
    }
}
