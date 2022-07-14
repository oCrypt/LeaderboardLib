package com.cahrypt.me.leaderboardlib.leaderboard;

import com.cahrypt.me.leaderboardlib.LeaderboardLib;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class LeaderboardManager {
    private final Map<String, Leaderboard<?>> leaderboardMap;

    private int updateTime;
    private BukkitTask updateTask;

    public LeaderboardManager() {
        this.leaderboardMap = new HashMap<>();
        this.updateTime = 10;
    }

    protected void addLeaderboard(String id, Leaderboard<?> leaderboard) {
        if (leaderboardMap.containsKey(id)) {
            leaderboardMap.get(id).remove();
        }

        this.leaderboardMap.put(id, leaderboard);
    }

    protected void removeLeaderboard(String id) {
        leaderboardMap.remove(id);
    }

    /**
     * Obtains the correct leaderboard from the corresponding ID value
     * @return the required leaderboard, unless none corresponds to the ID. Which case null will be returned
     */

    public Leaderboard<?> getLeaderboard(String id) {
        return leaderboardMap.get(id);
    }

    /**
     * Completely removes all existing leaderboards, regardless of visibility
     */

    public void removeLeaderboards() {
        leaderboardMap.forEach((id, leaderboard) -> leaderboard.remove());
        leaderboardMap.clear();
    }

    /**
     * Starts a BukkitRunnable task timer to continuously update all leaderboards live.
     * Note that is operation is already done upon SERVER START
     */

    public void startUpdateTask() {
        this.updateTask = new BukkitRunnable() {
            @Override
            public void run() {
                leaderboardMap.forEach((id, leaderboard) -> leaderboard.updateStats());
            }
        }.runTaskTimer(JavaPlugin.getPlugin(LeaderboardLib.class), 0, updateTime * 20L);
    }

    /**
     * Cancels the active BukkitRunnable task timer for continuous updating
     * @throws NullPointerException if the active task timer is null
     */

    public void stopUpdateTask() {
        updateTask.cancel();
        this.updateTask = null;
    }

    /**
     * Sets the new leaderboard update delay (in seconds) for the active BukkitRunnable task timer
     * Note that this value is by default set to 10 seconds
     * @throws IllegalArgumentException if the specified update time is 0 or below
     */

    public void setUpdateTime(int updateTimeSeconds) {
        if (updateTimeSeconds <= 0) {
            throw new IllegalArgumentException("Update task time cannot be equal to or below 0");
        }

        this.updateTime = updateTimeSeconds;
    }

    /**
     * Enables the specified player to view all leaderboard holograms
     */

    public void addGlobalLeaderboardViewer(@NotNull Player player) {
        leaderboardMap.forEach((id, leaderboard) -> leaderboard.show(player));
    }

    /**
     * Disables the specified player from viewing leaderboard holograms
     */

    public void removeGlobalLeaderboardViewer(@NotNull Player player) {
        leaderboardMap.forEach((id, leaderboard) -> leaderboard.hide(player));
    }
}
