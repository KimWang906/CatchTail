package org.kw906plugin.catchTail.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.kw906plugin.catchTail.GameStatus;
import org.kw906plugin.catchTail.SendMessage;
import org.kw906plugin.catchTail.commands.Sequence;

public class EntityDamageByEntity implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player damagedPlayer && event.getDamager() instanceof Player damagerPlayer) {
            if (GameStatus.getStatus() == GameStatus.COUNT_DOWN) {
                event.setCancelled(true);
                SendMessage.sendMessagePlayer(damagerPlayer, Component.text("게임 시작전에는 공격하실 수 없습니다")
                        .color(NamedTextColor.RED));
                return;
            }
            if (event.getDamage() >= damagedPlayer.getHealth()) {
                event.setCancelled(true);
                damagedPlayer.getWorld().playSound(damagedPlayer.getLocation(), Sound.ITEM_TOTEM_USE, 1.0f, 1.0f);
                damagedPlayer.getWorld().spawnParticle(Particle.valueOf("totem_of_undying"), damagedPlayer.getLocation(), 150);

                if (Sequence.isPlayerOut(damagedPlayer)) {
                    Sequence.stun(damagedPlayer);
                }

                if (Sequence.isPlayerOut(damagerPlayer) &&
                        Sequence.getNextPlayer(damagerPlayer).getPlayer().equals(damagedPlayer)) {
                    Sequence.out(damagedPlayer, Sequence.getPrevPlayer(damagerPlayer));
                } else if (Sequence.isPlayerOut(damagerPlayer) &&
                        !Sequence.getNextPlayer(damagerPlayer).getPlayer().equals(damagedPlayer)) {
                    damagedPlayer.heal(20.0);
                    Sequence.stun(damagerPlayer);
                }

                if (Sequence.getNextPlayer(damagerPlayer).getPlayer().equals(damagedPlayer)) {
                    Sequence.out(damagedPlayer, damagerPlayer);
                } else {
                    Sequence.out(damagerPlayer, damagedPlayer);
                }

            }
        } else if (event.getEntity() instanceof Player player) {
            if (event.getDamage() >= player.getHealth()) {
                event.setDamage(0);
                Sequence.stun(player);
            }
        }

    }
}
