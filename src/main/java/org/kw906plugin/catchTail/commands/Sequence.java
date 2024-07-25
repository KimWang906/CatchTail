package org.kw906plugin.catchTail.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.kw906plugin.catchTail.CatchTail;
import org.kw906plugin.catchTail.GameStatus;
import org.kw906plugin.catchTail.SendMessage;
import org.kw906plugin.catchTail.player.DetectEnemy;
import org.kw906plugin.catchTail.player.PlayerData;
import org.kw906plugin.catchTail.player.TailPlayer;

import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Level;

import static org.bukkit.Bukkit.*;
import static org.kw906plugin.catchTail.utils.PlayerImpl.*;
import static org.kw906plugin.catchTail.utils.UpdateWorldBorder.setWorldBorder;

public class Sequence {
    private static final PlayerData playerData = new PlayerData();
    private static final int gameTime = 0;

    public static void init() {
        if (GameStatus.getStatus().equals(GameStatus.INITIALIZED) || GameStatus.getStatus().equals(GameStatus.RUNNING)) {
            SendMessage.sendMessageOP(Component.text("이미 게임이 초기화 되어있습니다!")
                    .color(NamedTextColor.RED));
            return;
        }

        SendMessage.broadcastMessage(Component.text("시퀀스 - 게임 초기화 진행")
                .color(NamedTextColor.GRAY)
        );

        try {
            SendMessage.broadcastMessage(Component.text("시퀀스 - 플레이어 초기화 중.."));
            setFullCondition();

            SendMessage.broadcastMessage(Component.text("시퀀스 - 오버월드 월드보더 설정 중..")
                    .color(NamedTextColor.GRAY));
            setWorldBorder(CatchTail.config.getOverworld(), CatchTail.config.overworldWorldBorderSize);

            SendMessage.broadcastMessage(Component.text("시퀀스 - 네더 월드보더 설정 중..")
                    .color(NamedTextColor.GRAY));
            setWorldBorder(CatchTail.config.getNether(), CatchTail.config.netherWorldBorderSize);

            SendMessage.broadcastMessage(Component.text("시퀀스 - 엔드 월드보더 설정 중..")
                    .color(NamedTextColor.GRAY));
            setWorldBorder(CatchTail.config.getTheEnd(), CatchTail.config.endWorldBorderSize);

            SendMessage.broadcastMessage(Component.text("시퀀스 - 플레이어 추가 중..")
                    .color(NamedTextColor.GRAY));
            for (Player player : getOnlinePlayers()) {
                playerData.add(player);
                SendMessage.broadcastMessage(Component.text(
                        "시퀀스 - " + player.getName() + "을 대기 목록에 추가하였습니다."));
            }
        } catch (NullPointerException e) {
            SendMessage.broadcastMessage(Component.text("게임 초기화 도중 오류가 발생하였습니다.")
                    .color(NamedTextColor.RED));
            SendMessage.broadcastMessage(Component.text("오류 내용: " + e.getMessage())
                    .color(NamedTextColor.RED));
        }

        GameStatus.setStatus(GameStatus.INITIALIZED);
        SendMessage.broadcastMessage(Component.text("시퀀스 - 게임 초기화가 완료되었습니다.")
                .color(NamedTextColor.GRAY));
    }

    public static void start() {
        SendMessage.broadcastMessage(Component.text("시퀀스 - 게임이 시작됩니다!")
                .color(NamedTextColor.BLUE));
        setup();
        preparingTeleport();
    }

    public static void stop() {
        SendMessage.broadcastMessage(Component.text("시퀀스 - 게임이 중지됩니다!")
                .color(NamedTextColor.RED));
        GameStatus.setStatus(GameStatus.STOPPED);
        cleanup();
        DetectEnemy.disableDetectEnemy();
    }

    public static void unregister(String[] args) {
        for (String playerName : args) {
            Player playerToRemove = getPlayerByName(playerName);
            if (playerToRemove != null) {
                playerData.removePlayer(playerToRemove);
                SendMessage.broadcastMessage(Component.text(playerName + " - 플레이어가 대기 목록에서 제외되었습니다.")
                        .color(NamedTextColor.RED));
            } else {
                SendMessage.broadcastMessage(Component.text(playerName + " - 플레이어를 찾을 수 없습니다.")
                        .color(NamedTextColor.RED));
            }
        }
    }

    public static void config(String[] args) {
        if (args.length == 0) {
            SendMessage.sendMessageOP(Component.text("설정 목록:"));
            for (Field field : CatchTail.config.getClass().getFields()) {
                try {
                    Object value = field.get(CatchTail.config);

                    SendMessage.sendMessageOP(
                            Component.text(field.getName() + ": ")
                                    .color(NamedTextColor.AQUA)
                                    .append(Component.text(value.toString())
                                            .color(NamedTextColor.GRAY)));
                } catch (IllegalAccessException e) {
                    getLogger().log(Level.WARNING, e.getMessage());
                }
            }
            return;
        }

        Optional<Field> configOptional = Arrays.stream(CatchTail.config.getClass().getFields())
                .filter(f -> f.getName().equals(args[0])).findAny();
        if (configOptional.isPresent()) {
            try {
                Field targetConfig = configOptional.get();
                String configType = targetConfig.getType().getSimpleName();
                String beforeValue = targetConfig.get(CatchTail.config).toString();
                boolean success = false;
                switch (configType) {
                    case "String":
                        targetConfig.set(CatchTail.config, args[1]);
                        success = true;
                        break;
                    case "int":
                        try {
                            targetConfig.set(CatchTail.config, Integer.parseInt(args[1]));
                            success = true;
                        } catch (NumberFormatException e) {
                            SendMessage.sendMessageOP(Component.text("잘못된 입력 값으로 인해 변경에 실패하였습니다.\n" +
                                            "요청된 값: " + args[1])
                                    .color(NamedTextColor.RED));
                        }
                        break;
                    default:
                        SendMessage.sendMessageOP(Component.text("구현되지 않은 요청입니다. (요청된 타입: " + configType + ")")
                                .color(NamedTextColor.DARK_GRAY));
                        break;
                }

                if (success) {
                    SendMessage.sendMessageOP(Component.text("설정이 성공적으로 변경되었습니다!"));
                    SendMessage.sendMessageOP(Component.text("변경 내용: (" + beforeValue + "→" + args[1] + ")"));
                    CatchTail.config.applyConfig();
                }
            } catch (IllegalAccessException e) {
                SendMessage.sendMessageOP(Component.text("설정을 변경하던 중 오류가 발생하였습니다.\n" +
                                "자세한 내용은 서버 로그를 참고하세요.")
                        .color(NamedTextColor.RED));
                getLogger().log(Level.WARNING, e.getMessage());
            }
        }
    }

    public static void preparingTeleport() {
        GameStatus.setStatus(GameStatus.COUNT_DOWN);
        SendMessage.broadcastMessage(Component.text(CatchTail.config.countDown + "초 후 랜덤한 위치로 텔레포트합니다.")
                .color(NamedTextColor.BLUE));

        if (CatchTail.config.countDown > 0) {
            for (int i = CatchTail.config.countDown; i > 0; i--) {
                int countdown = i;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        SendMessage.broadcastMessage(Component.text(countdown + "초")
                                .color(NamedTextColor.AQUA));
                    }
                }.runTaskLater(JavaPlugin.getProvidingPlugin(CatchTail.class), (CatchTail.config.countDown - i) * 20L);  // 1초(20 틱) 간격으로 실행
            }
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                World world = getWorlds().getFirst();

                for (Player player : getOnlinePlayers()) {
                    Location randomLocation = getRandomLocation(world);
                    player.teleport(randomLocation);
                }

                GameStatus.setStatus(GameStatus.RUNNING);
                SendMessage.broadcastMessage(Component.text("모든 인원이 텔레포트되었습니다.")
                        .color(NamedTextColor.BLUE));
                playerData.shuffleColor();
                DetectEnemy.enableDetectEnemy();
            }
        }.runTaskLater(JavaPlugin.getProvidingPlugin(CatchTail.class), CatchTail.config.countDown * 20L);  // 10초 후 실행 (200 틱)
    }


    private static Location getRandomLocation(World world) {
        Random random = new Random();
        double x = random.nextInt(3000);
        double z = random.nextInt(3000);

        Location location = new Location(world, x, 0, z);
        location.setY(world.getHighestBlockAt(location).getY() + 1);

        return location;
    }

    public static void setup() {
        SendMessage.sendMessageOP(Component.text("시퀀스 - 셋업 진행중")
                .color(NamedTextColor.GRAY)
        );
        GameStatus.setStatus(GameStatus.ENGINE_SETUP);

        // Remove Dropped Item
        for (Entity current : CatchTail.config.getOverworld().getEntities()) {
            if (current instanceof Item) {
                current.remove();
            }
            if (current instanceof Creature) {
                current.remove();
            }
        }
    }

    public static void cleanup() {
        playerData.cleanup();
        setFullCondition();
    }

    public static void out(Player outPlayer, Player killedPlayer) {
        AttributeInstance attribute = outPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (attribute != null) {
            attribute.setBaseValue(10.0);
            outPlayer.setHealth(10.0);
        }

        playerData.setPlayerOut(outPlayer);
        int color = playerData.getPlayerColor(killedPlayer);

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

        applyArmors(playerData, color, helmet, chest, leggings, boots);

        outPlayer.getInventory().setHelmet(helmet);
        outPlayer.getInventory().setChestplate(chest);
        outPlayer.getInventory().setLeggings(leggings);
        outPlayer.getInventory().setBoots(boots);
    }

    public static void stun(Player player) {
        if (playerData.isNotOutPlayer(player)) {
            int duration = playerData.isNotOutPlayer(player) ? 120 : 30;
            SendMessage.sendMessagePlayer(player, Component.text(duration + "초간 기절하셨습니다."));
            PotionEffect fireResistance = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 2 * duration * 20, 0);
            player.addPotionEffect(fireResistance);
            playerData.stunPlayer(player);
        }
    }

    public static TailPlayer getNextPlayer(Player player) {
        return playerData.getNextPlayer(player);
    }

    public static Player getPrevPlayer(Player player) {
        return playerData.getPrevPlayer(player).getPlayer();
    }

    public static void printPlayerData() {
        List<Player> registeredPlayers = playerData.getPlayers();
        List<TailPlayer> tailPlayers = playerData.getTailPlayers();

        SendMessage.sendMessageOP(Component.text("현재 게임중인 플레이어 목록"));
        for (TailPlayer player : tailPlayers) {
            SendMessage.sendMessageOP(Component.text(player.getPlayer().getName()));
            SendMessage.sendMessageOP(Component.text(player.getColor())); // Debugging
        }
        SendMessage.sendMessageOP(Component.text("현재 등록된 플레이어 목록"));
        for (Player player : registeredPlayers) {
            SendMessage.sendMessageOP(Component.text(player.getName()));
        }
    }

    public static Integer checkPlayerStun(Player player) {
        TailPlayer tailPlayer = playerData.getTailPlayer(player);
        if (tailPlayer == null) {
            return 2;
        }
        if (tailPlayer.isOut()) {
            Long now = System.currentTimeMillis() / 1000;
            long duration = tailPlayer.isNotOut() ? 120L : 30L;
            return now - tailPlayer.getStunnedAt() >= duration ? 1 : 0;
        }
        return 2;
    }

    public static void releaseStun(Player player) {
        if (playerData.getStunnedAt(player) != 0) {
            playerData.releaseStun(player);
        }
    }

    public static boolean isPlayerOut(Player player) {
        return playerData.isPlayerOut(player);
    }

    public static int getSurvivePlayerCount() {
        return playerData.getSurvivePlayer();
    }

    public static List<Player> getPlayers() {
        return playerData.getPlayers();
    }
}
