package org.kw906plugin.catchTail.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.kw906plugin.catchTail.SendMessage;
import org.kw906plugin.catchTail.commands.Sequence;
import org.kw906plugin.catchTail.events.TickListener;
import static org.kw906plugin.catchTail.CatchTail.tickListener;

public class DetectEnemy {
    public static boolean isRunning = false;

    public static void initTickListener() {
        tickListener = new TickListener(() -> {
            if (Sequence.getSurvivePlayerCount() <= 1) {
                disableDetectEnemy();
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                TailPlayer tailPlayer = Sequence.getNextPlayer(player);

                if (tailPlayer.isNotOut()) {
                    Location playerLocation = player.getLocation();
                    Location enemyLocation = tailPlayer.getPlayer().getLocation();

                    // Player & Enemy offset
                    int offsetX = Math.abs(playerLocation.getBlockX() - enemyLocation.getBlockX());
                    int offsetY = Math.abs(playerLocation.getBlockY() - enemyLocation.getBlockY());
                    int offsetZ = Math.abs(playerLocation.getBlockZ() - enemyLocation.getBlockZ());
                    if (offsetX <= 30 && offsetY <= 30 && offsetZ <= 30) {
//                        new BukkitRunnable() {
//                            @Override
//                            public void run() {
//                                playHeartBeat(player);
//                            }
//                        }.runTaskLater(getPlugin(CatchTail.class), 120L);
                        playHeartBeat(player);
                    }
                }
            }
        });
    }

    public static void enableDetectEnemy() {
        if (!isRunning) {
            isRunning = true;
            initTickListener();
            tickListener.start();
        }
    }

    public static void disableDetectEnemy() {
        if (isRunning) {
            isRunning = false;
            tickListener.stop();
            tickListener = null;
        }
    }

    public static void playHeartBeat(Player player) {
//        String heartbeatSound = "minecraft:entity.warden.heartbeat";
//        player.playSound(player.getLocation(), heartbeatSound, 1.0F, 1.0F);
        SendMessage.sendActionBar(player, Component.text("[ ♥ ]")
                                                   .decoration(TextDecoration.BOLD, true)
                                                   .color(NamedTextColor.RED));

    }

//    static void stopHeartBeat(Player player) {
//        String heartbeatSound = "minecraft:entity.warden.heartbeat";
//        player.sendActionBar(Component.text(""));
//
//    }
}
