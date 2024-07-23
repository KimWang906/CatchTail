package org.kw906plugin.catchTail.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.kw906plugin.catchTail.SendMessage;
import org.kw906plugin.catchTail.utils.ColorMapper;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {

    public List<Player> players = new ArrayList<>();
    private final List<TailPlayer> tailPlayers = new ArrayList<>();

    public void add(Player player) {
        players.add(player);
    }

    public void removePlayer(Player playerToRemove) {
        players.removeIf(player -> player.equals(playerToRemove));
    }

    public void cleanup() {
        players.clear();
    }

    public void shuffleColor() {
        ColorMapper colorMapper = new ColorMapper();
        List<Integer> existingColors = new ArrayList<>();
        int playerCount = players.size();
        while (!players.isEmpty()) {
            int randomIndex = (int) (Math.random() * playerCount);
            if (existingColors.contains(randomIndex)) {
                continue;
            }

            existingColors.add(randomIndex);
            Player player = players.removeFirst();
            String colorName = colorMapper.getColorName(randomIndex);
            NamedTextColor color = colorMapper.getColor(randomIndex);
            tailPlayers.add(new TailPlayer(player, colorName, color));

            SendMessage.sendMessagePlayer(player, Component.text("당신의 색깔은 ")
                    .append(Component.text(String.format("%s색", colorName)).color(color))
                    .append(Component.text(" 입니다.")));
            int nextColorId = randomIndex-1 < 0 ? playerCount-1 : randomIndex;
            SendMessage.sendMessagePlayer(player, Component.text("당신이 죽여야 할 색깔은 ")
                    .append(Component.text(String.format("%s색", colorMapper.getColorName(nextColorId)))
                            .color(colorMapper.getColor(nextColorId)))
                    .append(Component.text(" 입니다.")));
        }
    }
}
