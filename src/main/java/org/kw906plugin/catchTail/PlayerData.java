package org.kw906plugin.catchTail;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {
    public List<Player> players = new ArrayList<>();

    public void add(Player player) {
        players.add(player);
    }

    public void removePlayer(Player playerToRemove) {
        players.removeIf(player -> player.equals(playerToRemove));
    }

    public void cleanup() {
        players.clear();
    }
}
