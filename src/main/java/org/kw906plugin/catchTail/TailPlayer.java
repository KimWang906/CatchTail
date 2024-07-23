package org.kw906plugin.catchTail;

import org.bukkit.entity.Player;

public class TailPlayer {

    private Player player;
    private String color;

    public TailPlayer(Player player, String color) {
        this.player = player;
        this.color = color;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
