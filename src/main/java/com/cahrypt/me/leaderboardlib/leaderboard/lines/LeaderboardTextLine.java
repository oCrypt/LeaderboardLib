package com.cahrypt.me.leaderboardlib.leaderboard.lines;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

public class LeaderboardTextLine extends LeaderboardLine<String> {

    /**
     * @param text the text that should be displayed
     */

    public LeaderboardTextLine(String text) {
        super(text);
    }

    @Override
    public void apply(Hologram hologram) {
        hologram.appendTextLine(value);
    }
}
