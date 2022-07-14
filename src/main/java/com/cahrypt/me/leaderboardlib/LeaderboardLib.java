package com.cahrypt.me.leaderboardlib;

import com.cahrypt.me.leaderboardlib.leaderboard.LeaderboardManager;
import com.cahrypt.me.leaderboardlib.listeners.PlayerJoinListener;
import com.cahrypt.me.leaderboardlib.listeners.PlayerLeaveListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LeaderboardLib extends JavaPlugin {
    private boolean hologramsEnabled;
    private static LeaderboardManager leaderboardManager;

    @Override
    public void onEnable() {

        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
            this.hologramsEnabled = false;
            return;
        }

        this.hologramsEnabled = true;

        leaderboardManager = new LeaderboardManager();
        leaderboardManager.startUpdateTask();

        new PlayerJoinListener();
        new PlayerLeaveListener();
    }

    @Override
    public void onDisable() {
        if (hologramsEnabled) {
            leaderboardManager.stopUpdateTask();
        }
    }

    /**
     * Obtains the Leaderboard library manager that allows interaction with core
     * leaderboard functionality
     * @return registered leaderboard manager class
     */

    public static LeaderboardManager getLeaderboardManager() {
        return leaderboardManager;
    }
}
