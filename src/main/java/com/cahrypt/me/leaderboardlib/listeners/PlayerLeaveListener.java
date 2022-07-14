package com.cahrypt.me.leaderboardlib.listeners;

import com.cahrypt.me.leaderboardlib.LeaderboardLib;
import com.cahrypt.me.leaderboardlib.leaderboard.LeaderboardManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener extends AListener {
    private final LeaderboardManager leaderboardManager;

    public PlayerLeaveListener() {
        super();
        this.leaderboardManager = LeaderboardLib.getLeaderboardManager();
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        leaderboardManager.removeGlobalLeaderboardViewer(event.getPlayer());
    }
}
