package org.kw906plugin.catchTail.utils;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
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

    public static void removeAllItemsAndArmor(Player player) {
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }

    public static void setFullCondition() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            removeAllItemsAndArmor(player);
            AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            assert attribute != null;
            attribute.setBaseValue(20);
            player.heal(20);
            player.setFoodLevel(20);
        }
    }
}
