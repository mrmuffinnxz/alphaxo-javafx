package logic;

import gui.Square;
import javafx.scene.paint.Color;

public class UpdateBoardState implements Runnable {
    private Square square;

    public UpdateBoardState(Square square) {
        super();
        this.square = square;
    }

    @Override
    public void run() {
    	if(!Game.getInstance().isGameEnd() || Game.getInstance().getWinner() == State.EMPTY) {
    		square.drawCurrentState(Color.WHITE);
    	}
    }
}
