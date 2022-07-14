package com.cahrypt.me.leaderboardlib.leaderboard;

import com.cahrypt.me.leaderboardlib.LeaderboardLib;
import com.cahrypt.me.leaderboardlib.leaderboard.lines.LeaderboardLine;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class Leaderboard <T> {
    private final LeaderboardManager leaderboardManager;

    private final String id;
    private final String statFormat;

    private final int leaderboardSize;

    private final List<LeaderboardLine<?>> upperLines;
    private final List<LeaderboardLine<?>> lowerLines;

    private final Supplier<Map<String, T>> statsSupplier;

    private final Hologram hologram;
    private final VisibilityManager hologramVisibilityManager;

    /**
     *
     * @param id the unique identification of this leaderboard. Note that a leaderboard with the same ID as another will override it completely and delete it
     * @param leaderboardSize the size of the leaderboard, exclusively referring to the stats
     * @param statFormat the format which stats will be displayed. Placeholders %player_name%, %leaderboard_position%, and %stat% are acceptable
     * @param location the location where this leaderboard will be spawned
     * @param upperLines the hologram lines above the displayed stats
     * @param lowerLines the hologram lines below the displayed stats
     * @param statsSupplier the supplier for the providing of leaderboard stats. Note that sorting must be done manually. This is to allow freedom of organization
     */

    public Leaderboard(String id,
            int leaderboardSize,
            @NotNull String statFormat,
            @NotNull Location location,
            @Nullable LeaderboardLine<?>[] upperLines,
            @Nullable LeaderboardLine<?>[] lowerLines,
            @NotNull Supplier<Map<String, T>> statsSupplier) {

        LeaderboardLib main = JavaPlugin.getPlugin(LeaderboardLib.class);
        this.leaderboardManager = LeaderboardLib.getLeaderboardManager();
        this.id = id;
        this.statFormat = statFormat;
        this.leaderboardSize = leaderboardSize;
        this.upperLines = (upperLines != null ? Arrays.stream(upperLines).toList() : null);
        this.lowerLines = (lowerLines != null ? Arrays.stream(lowerLines).toList() : null);
        this.statsSupplier = statsSupplier;
        this.hologram = HologramsAPI.createHologram(main, location);
        this.hologramVisibilityManager = hologram.getVisibilityManager();

        hologramVisibilityManager.setVisibleByDefault(false);
        setupHologramLines();
        leaderboardManager.addLeaderboard(id, this);
    }

    private String buildStatLine(String playerName, int position, T stat) {
        return statFormat
                .replace("%player_name%", playerName)
                .replace("%leaderboard_position%", String.valueOf(position)
                .replace("%stat%", stat.toString())
            );
    }

    private void setupHologramLines() {
        if (upperLines != null) {
            upperLines.forEach(leaderboardLine -> leaderboardLine.apply(hologram));
        }

        AtomicInteger position = new AtomicInteger(1);

        statsSupplier.get().forEach((playerName, stat) -> {
            int posInt = position.get();

            if (posInt > leaderboardSize) {
                return;
            }

            hologram.appendTextLine(buildStatLine(playerName, posInt, stat));
            position.getAndIncrement();
        });

        if (lowerLines != null) {
            lowerLines.forEach(leaderboardLine -> leaderboardLine.apply(hologram));
        }
    }

    protected boolean isID(@NotNull String id) {
        return this.id.equals(id);
    }

    protected void updateStats() {
        int startingLine = (upperLines == null ? 1 : upperLines.size());
        int endingLine = startingLine + leaderboardSize;

        AtomicInteger currentLine = new AtomicInteger(startingLine);
        AtomicInteger position = new AtomicInteger(1);

        statsSupplier.get().forEach((playerName, stat) -> {
            if (position.get() > endingLine) {
                return;
            }

            hologram.removeLine(currentLine.get());
            hologram.insertTextLine(currentLine.get(), buildStatLine(playerName, position.get(), stat));
            currentLine.getAndIncrement();
            position.getAndIncrement();
        });
    }

    /**
     * Sets the new leaderboard update delay (in seconds) for the active BukkitRunnable task timer
     * Note that this value is by default set to 10 seconds
     * @throws IllegalArgumentException if the specified update time is 0 or below
     */

    public void remove() {
        hologram.delete();
        leaderboardManager.removeLeaderboard(id);
    }

    /**
     * Makes the leaderboard visible to the specified player
     */

    public void show(@NotNull Player player) {
        hologramVisibilityManager.showTo(player);
    }

    /**
     * Hides the leaderboard from the specified player
     */

    public void hide(@NotNull Player player) {
        hologramVisibilityManager.hideTo(player);
    }
}
