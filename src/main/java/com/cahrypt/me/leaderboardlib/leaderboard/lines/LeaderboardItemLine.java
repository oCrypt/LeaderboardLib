package com.cahrypt.me.leaderboardlib.leaderboard.lines;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import org.bukkit.inventory.ItemStack;

public class LeaderboardItemLine extends LeaderboardLine<ItemStack> {

    /**
     * @param item the item that should be displayed
     */

    public LeaderboardItemLine(ItemStack item) {
        super(item);
    }

    @Override
    public void apply(Hologram hologram) {
        hologram.appendItemLine(value);
    }
}
