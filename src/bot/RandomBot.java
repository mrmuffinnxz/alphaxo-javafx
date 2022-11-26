package bot;

import javafx.scene.paint.Color;
import logic.Game;
import logic.State;

public class RandomBot extends Bot {
    public RandomBot() {
        super("RandomBot", Color.LIGHTCYAN, 999, true);
    }

    @Override
    public Record calculateRecord(State player, char[] boardState) {
        String boardStateStr = String.valueOf(boardState);
        Record record = new Record(boardStateStr, Game.getInstance().getValidMoves(boardState));
        return record;
    }
}
