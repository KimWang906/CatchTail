package org.kw906plugin.catchTail.utils;

import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getOnlinePlayers;

public class PlayerImpl {
    public static Player getPlayerByName(String name) {
        for (Player onlinePlayer : getOnlinePlayers()) {
            if (onlinePlayer.getName().equalsIgnoreCase(name)) {
                return onlinePlayer;
            }
        }
        return null;
    }
}
