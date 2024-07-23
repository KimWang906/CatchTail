package org.kw906plugin.catchTail.player;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class TailPlayer {

    private Player player;
    private String colorName;
    private NamedTextColor color;
    private Boolean isOut;

    public TailPlayer(Player player, String colorName, NamedTextColor color) {
        this.player = player;
        this.colorName = colorName;
        this.color = color;
        isOut = false;
    }

    public Boolean isNotOut() {
        return !isOut;
    }

    public void setOut(Boolean out) {
        isOut = out;
    }

    public NamedTextColor getColor() {
        return color;
    }

    public void setColor(NamedTextColor color) {
        this.color = color;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
}
