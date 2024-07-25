package org.kw906plugin.catchTail.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.kw906plugin.catchTail.GameStatus;
import org.kw906plugin.catchTail.SendMessage;

public class EntityDamage implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (GameStatus.getStatus() == GameStatus.COUNT_DOWN) {
                event.setCancelled(true);
                SendMessage.sendMessagePlayer(player, Component.text("게임 시작전에는 공격하실 수 없습니다")
                        .color(NamedTextColor.RED));
            }

            if (event.getDamage() >= player.getHealth()) {
                event.setCancelled(true);
                player.getWorld().playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1.0f, 1.0f);
                player.getWorld().spawnParticle(Particle.valueOf("totem_of_undying"), player.getLocation(), 250);
            }
        }
    }
}
