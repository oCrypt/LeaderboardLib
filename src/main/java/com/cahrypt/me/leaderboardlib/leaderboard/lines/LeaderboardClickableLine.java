package com.cahrypt.me.leaderboardlib.leaderboard.lines;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class LeaderboardClickableLine extends LeaderboardLine<String> {
    private final Consumer<Player> clickAction;

    /**
     * @param text the text of the clickable line that should be displayed
     * @param clickAction the actions that should be performed on the player who clicks the line
     */

    public LeaderboardClickableLine(String text, Consumer<Player> clickAction) {
        super(text);
        this.clickAction = clickAction;
    }

    @Override
    public void apply(Hologram hologram) {
        hologram.appendTextLine(value).setTouchHandler(hologramLineClickEvent -> clickAction.accept(hologramLineClickEvent.getPlayer()));
    }
}
