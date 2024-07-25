package org.kw906plugin.catchTail.events;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.kw906plugin.catchTail.SendMessage;
import org.kw906plugin.catchTail.commands.Sequence;

public class PlayerMovement implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (Sequence.checkPlayerStun(player)) {
            SendMessage.sendActionBar(player, Component.text("기절!"));
            event.setCancelled(true);
            player.teleport(event.getFrom());
        } else {
            Sequence.releaseStun(player);
        }
    }
}
