package org.kw906plugin.catchTail;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import static org.bukkit.Bukkit.getServer;

public enum GameStatus {
    STANDBY(NamedTextColor.YELLOW),
    INITIALIZED(NamedTextColor.GREEN),
    ENGINE_SETUP(NamedTextColor.GOLD),
    GAME_SETUP(NamedTextColor.GOLD),
    COUNT_DOWN(NamedTextColor.BLUE),
    RUNNING(NamedTextColor.GREEN),
    FINISHED(NamedTextColor.AQUA),
    STOPPED(NamedTextColor.RED),
    BREAK_PRD(NamedTextColor.LIGHT_PURPLE);

    private NamedTextColor color;

    GameStatus(NamedTextColor color) {
        this.color = color;
    }

    public NamedTextColor getColor() {
        return color;
    }

    private static GameStatus currentStatus = GameStatus.STANDBY;

    public static void setStatus(GameStatus newStatus) {
        SendMessage.sendMessageOP(Component.text("게임상태가 변경되었습니다. %old% → %new%"
                                                         .replace("%old%", currentStatus.toString())
                                                         .replace("%new%", newStatus.toString()))
                                           .color(NamedTextColor.GRAY));
        currentStatus = newStatus;
    }

    public static GameStatus getStatus() {
        return currentStatus;
    }

    public static void broadcastStatus() {
        getServer().broadcast(getMessage());
    }

    public static Component getMessage() {
        return Component.text("게임상태 : " + currentStatus.color + currentStatus.toString())
                .color(NamedTextColor.GOLD);
    }
}
