package org.kw906plugin.catchTail.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.kw906plugin.catchTail.GameStatus;
import org.kw906plugin.catchTail.SendMessage;
import org.kw906plugin.catchTail.commands.Sequence;

public class EntityDamage implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (GameStatus.getStatus() == GameStatus.COUNT_DOWN) {
                event.setCancelled(true);
                SendMessage.sendMessagePlayer(player, Component.text("게임 시작전에는 공격하실 수 없습니다")
                        .color(NamedTextColor.RED));
                return;
            }

            if (GameStatus.getStatus() != GameStatus.RUNNING)
                return;

            if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK)
                return;

            if (event.getDamage() >= player.getHealth()) {
                event.setCancelled(true);

                Player nextPlayer = Sequence.getNextPlayer(player).getPlayer();

//                Sequence.out(player);
            }
        }
    }


}
