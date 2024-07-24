package org.kw906plugin.catchTail.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.kw906plugin.catchTail.SendMessage;
import org.kw906plugin.catchTail.utils.ColorMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class PlayerData {

    private final List<Player> players = new ArrayList<>();
    private final List<TailPlayer> tailPlayers = new ArrayList<>();
    private final ColorMapper colorMapper = new ColorMapper();

    public void add(Player player) {
        players.add(player);
    }

    public void removePlayer(Player playerToRemove) {
        players.removeIf(player -> player.equals(playerToRemove));
    }

    public void cleanup() {
        players.clear();
    }

    public List<TailPlayer> getTailPlayers() {
        return tailPlayers;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public TailPlayer getNextPlayer(Player player) {
        TailPlayer tailPlayer = tailPlayers.stream().filter(p -> p.getPlayer().getName().equals(player.getName())).findFirst().orElse(null);
        assert tailPlayer != null;
        SendMessage.sendMessageOP(Component.text("현재 플레이어의 색깔 : " + tailPlayer.getColorName()).color(NamedTextColor.GRAY));
        String prevColor = tailPlayer.getColorName();
        while (true) {
            String nextColor = colorMapper.getNextColor(prevColor);
            TailPlayer nextPlayer = tailPlayers.stream().filter(p -> p.getColorName().equals(nextColor)).findFirst().orElse(null);
            assert nextPlayer != null;
            SendMessage.sendMessageOP(Component.text("타켓 플레이어 : " + nextPlayer.getPlayer().getName()).color(NamedTextColor.GRAY));
            SendMessage.sendMessageOP(Component.text("타켓 색깔 : " + nextPlayer.getColorName()).color(NamedTextColor.GRAY));
            if (nextPlayer.isNotOut()) {
                return nextPlayer;
            }
            prevColor = nextColor;
        }
    }

    public void shuffleColor() {
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
            Bukkit.getLogger().info("플레이어 등록 진행 중 - 플레이어 이름: " + player.getName());

            NamedTextColor color = colorMapper.getColor(randomIndex);
            boolean success = tailPlayers.add(new TailPlayer(player, colorName, color));
            if (success) {
                Bukkit.getLogger().info("플레이어 등록 성공");
                Bukkit.getLogger().info("플레이어: " + player.getName());
                Bukkit.getLogger().info("색깔: " + colorName);
            }

            SendMessage.sendMessagePlayer(player, Component.text("당신의 색깔은 ")
                    .append(Component.text(String.format("%s색", colorName)).color(color))
                    .append(Component.text(" 입니다.")));
            int nextColorId = randomIndex - 1 < 0 ? playerCount - 1 : randomIndex - 1;
            SendMessage.sendMessagePlayer(player, Component.text("당신이 죽여야 할 색깔은 ")
                    .append(Component.text(String.format("%s색", colorMapper.getColorName(nextColorId)))
                            .color(colorMapper.getColor(nextColorId)))
                    .append(Component.text(" 입니다.")));
        }
    }
}
