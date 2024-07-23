package org.kw906plugin.catchTail.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerTracking implements Listener {

    @EventHandler
    public void rightClickDiamond(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if ((action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) &&
                player.getItemInHand().getType() == Material.DIAMOND) {

        }
    }
}
