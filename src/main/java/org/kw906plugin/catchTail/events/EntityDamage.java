package org.kw906plugin.catchTail.events;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.kw906plugin.catchTail.commands.Sequence;

public class EntityDamage implements Listener {
    @EventHandler
    public void onEntityDamage(PlayerDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            player.heal(10.0f);
            event.setCancelled(true);
            player.getWorld().playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1.0f, 1.0f);
            player.getWorld().spawnParticle(Particle.valueOf("totem_of_undying"), player.getLocation(), 250);
            Sequence.stun(player);
        }
    }
}
