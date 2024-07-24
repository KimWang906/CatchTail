package org.kw906plugin.catchTail.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.kw906plugin.catchTail.SendMessage;
import org.kw906plugin.catchTail.utils.ColorMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerData {

    private final List<Player> players = new ArrayList<>();
    private final List<TailPlayer> tailPlayers = new ArrayList<>();
    private final ColorMapper colorMapper = new ColorMapper();
    int playerCount = 0;

    public void add(Player player) {
        players.add(player);
    }

    public void removePlayer(Player playerToRemove) {
        players.removeIf(player -> player.equals(playerToRemove));
    }

    public void cleanup() {
        players.clear();
        tailPlayers.clear();
    }

    public List<TailPlayer> getTailPlayers() {
        return tailPlayers;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean isNotOutPlayer(Player player) {
        TailPlayer tailPlayer = getTailPlayer(player);
        assert tailPlayer != null;
        return tailPlayer.isNotOut();
    }

    public void stunPlayer(Player player) {
        TailPlayer tailPlayer = getTailPlayer(player);
        assert tailPlayer != null;
        tailPlayer.stunPlayer();
    }

    public void releaseStun(Player player) {
        TailPlayer tailPlayer = getTailPlayer(player);
        assert tailPlayer != null;
        tailPlayer.releaseStun();
    }

    public Long getStunnedAt(Player player) {
        TailPlayer tailPlayer = getTailPlayer(player);
        assert tailPlayer != null;
        return tailPlayer.getStunnedAt();
    }

    public TailPlayer getNextPlayer(Player player) {
        TailPlayer tailPlayer = getTailPlayer(player);
        assert tailPlayer != null;
        Integer prevColor = tailPlayer.getColor();
        SendMessage.sendMessageOP(Component.text("현재 플레이어의 색깔 : " + colorMapper.getColorName(prevColor))
                .color(NamedTextColor.GRAY));
        while (true) {
            int nextColor = prevColor - 1 < 0 ? playerCount - 1 : prevColor - 1;
            TailPlayer nextPlayer = tailPlayers.stream().filter(p -> Objects.equals(p.getColor(), nextColor)).findFirst().orElse(null);
            assert nextPlayer != null;
            SendMessage.sendMessageOP(Component.text("타켓 플레이어 : " + nextPlayer.getPlayer().getName()).color(NamedTextColor.GRAY));
            SendMessage.sendMessageOP(Component.text("타켓 색깔 : " + colorMapper.getColorName(nextPlayer.getColor())).color(NamedTextColor.GRAY));
            if (nextPlayer.isNotOut()) {
                return nextPlayer;
            }
            prevColor = nextColor;
        }
    }

    public void shuffleColor() {
        List<Integer> existingColors = new ArrayList<>();
        playerCount = players.size();
        while (!players.isEmpty()) {
            int randomIndex = (int) (Math.random() * playerCount);
            if (existingColors.contains(randomIndex)) {
                continue;
            }

            existingColors.add(randomIndex);
            Player player = players.removeFirst();
            String colorName = colorMapper.getColorName(randomIndex);
            NamedTextColor color = colorMapper.getColor(randomIndex);
            Bukkit.getLogger().info("플레이어 등록 진행 중 - 플레이어 이름: " + player.getName());

            boolean success = tailPlayers.add(new TailPlayer(player, randomIndex));
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

    public TailPlayer getTailPlayer(Player player) {
        return tailPlayers.stream().filter(p -> p.getPlayer().getName().equals(player.getName())).findFirst().orElse(null);
    }
}
