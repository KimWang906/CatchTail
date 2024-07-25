package org.kw906plugin.catchTail.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.kw906plugin.catchTail.GameStatus;
import org.kw906plugin.catchTail.SendMessage;
import org.kw906plugin.catchTail.commands.Sequence;

public class PlayerTracking implements Listener {

    @EventHandler
    public void rightClickDiamond(PlayerInteractEvent event) {

        if (GameStatus.getStatus() != GameStatus.RUNNING)
            return;

        Player player = event.getPlayer();
        Action action = event.getAction();
        World world = player.getWorld();
        ItemStack clickedItem = player.getInventory().getItemInMainHand();

        if ((action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) &&
            clickedItem.getType() == Material.DIAMOND) {
            Player nextPlayer = Sequence.getNextPlayer(player).getPlayer();

            World targetWorld = nextPlayer.getWorld();
            if (targetWorld != world) {
                SendMessage.sendActionBar(player, Component.text("추적 오류: 대상과 같은 월드에 있지 않습니다.").color(NamedTextColor.RED));
                return;
            }

            Particle particle = Particle.valueOf("soul_fire_flame");

            double nowX = player.getLocation().getX();
            double nowY = player.getLocation().getY() + 1.0;
            double nowZ = player.getLocation().getZ();

            double dirX = nextPlayer.getLocation().getX() - nowX;
            double dirY = nextPlayer.getLocation().getY() - nowY + 1.0;
            double dirZ = nextPlayer.getLocation().getZ() - nowZ;

            double dir = Math.sqrt(Math.pow(dirX, 2) + Math.pow(dirY, 2) + Math.pow(dirZ, 2));

            double unitX = dirX / dir;
            double unitY = dirY / dir;
            double unitZ = dirZ / dir;

            for (int i = 0; i < 20; i++) {
                nowX = nowX + 0.25 * unitX;
                nowY = nowY + 0.25 * unitY;
                nowZ = nowZ + 0.25 * unitZ;
                world.spawnParticle(particle, nowX, nowY, nowZ, 1, 0, 0, 0, 0.005);
            }
            clickedItem.subtract();
        }
    }
}
