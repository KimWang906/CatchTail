package org.kw906plugin.catchTail.player;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class TailPlayer {

    private Player player;
    private Integer color;
    private Boolean isOut;

    public TailPlayer(Player player, int color) {
        this.player = player;
        this.color = color;
        isOut = false;
    }

    public Boolean isNotOut() {
        return !isOut;
    }

    public void setOut(Boolean out) {
        isOut = out;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }
}
