package com.cahrypt.me.leaderboardlib.listeners;

import com.cahrypt.me.leaderboardlib.LeaderboardLib;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AListener implements Listener {

    public AListener() {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(LeaderboardLib.class));
    }
}
