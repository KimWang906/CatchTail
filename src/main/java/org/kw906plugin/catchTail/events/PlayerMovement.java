package org.kw906plugin.catchTail.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.kw906plugin.catchTail.commands.Sequence;

public class PlayerMovement implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        switch (Sequence.checkPlayerStun(player)) {
            case 0:
                event.setCancelled(true);
                player.teleport(event.getFrom());
                break;
            case 1:
                Sequence.releaseStun(player);
                break;
            default:
                break;
        }
    }
}
