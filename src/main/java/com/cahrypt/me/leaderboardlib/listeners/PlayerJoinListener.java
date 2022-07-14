package com.cahrypt.me.leaderboardlib.listeners;

import com.cahrypt.me.leaderboardlib.LeaderboardLib;
import com.cahrypt.me.leaderboardlib.leaderboard.LeaderboardManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener extends AListener {
    private final LeaderboardManager leaderboardManager;

    public PlayerJoinListener() {
        super();
        this.leaderboardManager = LeaderboardLib.getLeaderboardManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        leaderboardManager.addGlobalLeaderboardViewer(event.getPlayer());
    }
}
