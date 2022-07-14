package com.cahrypt.me.leaderboardlib.leaderboard.lines;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import org.jetbrains.annotations.NotNull;

public abstract class LeaderboardLine <T> {
    protected final T value;

    public LeaderboardLine(@NotNull T value) {
        this.value = value;
    }

    public abstract void apply(Hologram hologram);
}
