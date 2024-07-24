package org.kw906plugin.catchTail.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
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

import java.time.Duration;
import java.util.NoSuchElementException;

public class PlayerTracking implements Listener {

    @EventHandler
    public void rightClickDiamond(PlayerInteractEvent event) {

        if (GameStatus.getStatus() != GameStatus.RUNNING)
            return;

        Player player = event.getPlayer();
        Action action = event.getAction();
        World world = player.getWorld();
        ItemStack clickedItem = player.getInventory().getItemInMainHand();

        if ((action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR) &&
            clickedItem.getType() == Material.DIAMOND) {
            Player nextPlayer = Sequence.getNextPlayer(player).getPlayer();

            World targetWorld = nextPlayer.getWorld();
            SendMessage.sendMessageOP(Component.text("월드 고정").color(NamedTextColor.AQUA));
            if (targetWorld != world) {
                Title.Times times = Title.Times.times(Duration.ZERO, Duration.ofSeconds(3), Duration.ZERO);
                SendMessage.sendTitle(player, Component.text("추적 오류: 대상과 같은 월드에 있지 않습니다."), times);
//                player.sendTitlePart(TitlePart.SUBTITLE, Component.text("추적 오류: 대상과 같은 월드에 있지 않습니다.")
//                        .color(NamedTextColor.RED));
                return;
            }

            SendMessage.sendMessageOP(Component.text("파티클 생성").color(NamedTextColor.AQUA));
            Particle particle = Particle.valueOf("soul_fire_flame");

            double nowX = player.getLocation().getX();
            double nowY = player.getLocation().getY();
            double nowZ = player.getLocation().getZ();
            SendMessage.sendMessageOP(Component.text("현재 좌표 고정").color(NamedTextColor.AQUA));

            double dirX = nextPlayer.getLocation().getX() - nowX;
            double dirY = nextPlayer.getLocation().getY() - nowY;
            double dirZ = nextPlayer.getLocation().getZ() - nowZ;
            SendMessage.sendMessageOP(Component.text("방향 백터 계산").color(NamedTextColor.AQUA));

            double dir = Math.sqrt(Math.pow(dirX, 2) + Math.pow(dirY, 2) + Math.pow(dirZ, 2));
            SendMessage.sendMessageOP(Component.text("방향 백터 d 계산").color(NamedTextColor.AQUA));

            double unitX = dirX / dir;
            double unitY = dirY / dir;
            double unitZ = dirZ / dir;
            SendMessage.sendMessageOP(Component.text("단위 백터 계산").color(NamedTextColor.AQUA));

            for (int i = 0; i < 10; i++) {
                nowX = nowX + 0.25 * unitX;
                nowY = nowY + 0.25 * unitY;
                nowZ = nowZ + 0.25 * unitZ;
                world.spawnParticle(particle, nowX, nowY, nowZ, 1, 0.1, 0.1, 0.1, 0.01);
                SendMessage.sendMessageOP(Component.text(i + "번째 파티클 생성").color(NamedTextColor.AQUA));
            }
            clickedItem.subtract();
        }
    }
}
