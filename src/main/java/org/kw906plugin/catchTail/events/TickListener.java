package org.kw906plugin.catchTail.events;

import org.bukkit.scheduler.BukkitRunnable;
import org.kw906plugin.catchTail.CatchTail;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class TickListener extends BukkitRunnable {
    private final Runnable task;

    public TickListener(Runnable task) {
        this.task = task;
    }

    @Override
    public void run() {
        task.run();
    }

    public void start() {
        this.runTaskTimer(getPlugin(CatchTail.class), 0L, 1L); // 매 틱마다 실행
    }

    public void stop() {
        cancel(); // 스케줄링된 작업 취소
    }
}
