package org.kw906plugin.catchTail.utils;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.kw906plugin.catchTail.player.PlayerData;

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

    public static void applyArmors(PlayerData playerData, int color, ItemStack helmet, ItemStack chest, ItemStack leggings, ItemStack boots) {
        LeatherArmorMeta helmetsMeta = setArmorSetting(playerData, color, helmet);
        LeatherArmorMeta chestMeta = setArmorSetting(playerData, color, chest);
        LeatherArmorMeta leggingsMeta = setArmorSetting(playerData, color, leggings);
        LeatherArmorMeta bootsMeta = setArmorSetting(playerData, color, boots);

        helmet.setItemMeta(helmetsMeta);
        chest.setItemMeta(chestMeta);
        leggings.setItemMeta(leggingsMeta);
        boots.setItemMeta(bootsMeta);
    }

    public static LeatherArmorMeta setArmorSetting(PlayerData playerData, int color, ItemStack armor) {
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) armor.getItemMeta();
        armorMeta.setColor(playerData.getColorCode(color));
        armorMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        armorMeta.setUnbreakable(true);

        return armorMeta;
    }
}
