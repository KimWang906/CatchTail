package org.kw906plugin.catchTail.player;

import org.bukkit.entity.Player;

public class TailPlayer {

    private Player player;
    private Integer color;
    private Boolean isOut;
    private Long stunnedAt;

    public TailPlayer(Player player, int color) {
        this.player = player;
        this.color = color;
        isOut = false;
        stunnedAt = 0L;
    }

    public Boolean getOut() {
        return isOut;
    }

    public Long getStunnedAt() {
        return stunnedAt;
    }

    public void stunPlayer() {
        stunnedAt = System.currentTimeMillis() / 1000;
    }

    public Boolean isNotOut() {
        return !isOut;
    }

    public Boolean isOut() {
        return isOut;
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

    public void releaseStun() {
        stunnedAt = 0L;
    }
}
