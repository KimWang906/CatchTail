package org.kw906plugin.catchTail;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

import static org.bukkit.Bukkit.broadcast;
import static org.bukkit.Bukkit.getOnlinePlayers;

public class SendMessage extends JavaPlugin {
    private static final String name = CatchTail.name;
    private static final String version = CatchTail.version;

    public static void logConsole(String msg) {
        Logger logger = Bukkit.getServer().getLogger();
        logger.info("[%plugin%] ".replace("%plugin%", name) + msg);
    }

    public static void broadcastMessage(Component msg) {
//        Bukkit.broadcastMessage(ChatColor.GOLD + "[%plugin%] ".replace("%plugin%", name) + ChatColor.RESET + msg);
        broadcast(Component.text("[%plugin%] ".replace("%plugin%", name))
                           .color(NamedTextColor.GOLD)
                           .append(msg)
        );
    }

    public static void sendMessagePlayer(Player player, Component msg) {
//        player.sendMessage(
//                ChatColor.GOLD + "[%plugin% > %name%] ".replace("%plugin%", name).replace("%name%", player.getName())
//                        + ChatColor.RESET + msg);
        player.sendMessage(Component.text("[%plugin% → %name%] "
                                                  .replace("%plugin%", name)
                                                  .replace("%name%", player.getName()))
                                   .color(NamedTextColor.GOLD)
                                   .append(msg)
        );

        logConsole(player.getName() + "  -  " + msg);
    }

    public static void sendMessageOP(Component msg) {
        for (Player player : getOnlinePlayers())
            if (player.isOp())
                sendMessagePlayer(player, msg);
    }

    public static void sendCreditInfo() {
        broadcastMessage(Component.text(""));
        broadcastMessage(Component.text("====  플러그인 정보  ====  ").color(NamedTextColor.BLUE));
        broadcastMessage(Component.text("게임 이름: " + name));
        broadcastMessage(Component.text("게임 버전: " + version));
        broadcastMessage(Component.text("원작자: gidonyou"));
        broadcastMessage(Component.text("개발자: KimWang906"));
        broadcastMessage(Component.text("GitHub: https://github.com/KimWang906"));
        broadcastMessage(Component.text(""));
    }
}
