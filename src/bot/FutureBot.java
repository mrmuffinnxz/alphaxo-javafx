package bot;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import logic.Game;
import logic.State;

public class FutureBot extends Bot{
    public FutureBot() {
        super("FutureBot", Color.LIGHTPINK, 3, false);
    }

    public int checkWin(State player, char[] boardState) {
        ArrayList<Integer> playerSquare = Game.getInstance().getPlayerSquareFromBoardState(player, boardState);
        ArrayList<Integer> opponentSquare = Game.getInstance().getPlayerSquareFromBoardState((player == State.X) ? State.O : State.X, boardState);
        for(ArrayList<Integer> win : Game.getInstance().getWinState()) {
            if(playerSquare.containsAll(win)) {
                return 1;
            } else if(opponentSquare.containsAll(win)) {
                return -1;
            }
        }
        return 0;
    }

    @Override
    public Record calculateRecord(State player, char[] boardState) {
        String boardStateStr = String.valueOf(boardState);
    	char playerChar = (player == State.X) ? 'X' : 'O';

        if(this.getRecords().containsKey(boardStateStr)) {
            return this.getRecords().get(boardStateStr);
        }

        ArrayList<Integer> moves = Game.getInstance().getValidMoves(boardState);
		
        int bestResult = -100;
		StateSpaceRecord stateRecord = new StateSpaceRecord(boardStateStr, new ArrayList<Integer>(), 0, 0, 0);
		
		for(Integer move : moves) {
			char[] newboardState = boardState.clone();
			newboardState[move] = playerChar;
			
			int finalResult = this.checkWin(player, newboardState);

			if(finalResult == 0 && moves.size() != 1) {
				StateSpaceRecord result = (StateSpaceRecord) this.calculateRecord((player == State.X) ? State.O : State.X, newboardState);
				finalResult = -result.getFinalResult();
				if(finalResult == 1) {
					stateRecord.setWin(stateRecord.getWin() + result.getLose());
				}else if(finalResult == -1) {
					stateRecord.setLose(stateRecord.getLose() + result.getWin());
				}else {
					stateRecord.setDraw(stateRecord.getDraw() + result.getDraw());
				}
			}else {
				if(finalResult == 1) {
					stateRecord.setWin(stateRecord.getWin() + 1);
				}else if(finalResult == -1) {
					stateRecord.setLose(stateRecord.getLose() + 1);
				}else {
					stateRecord.setDraw(stateRecord.getDraw() + 1);
				}
			}
			
			if(finalResult > bestResult) {
				bestResult = finalResult;
				stateRecord.setMoveToPlay(new ArrayList<Integer>());
				stateRecord.getMoveToPlay().add(move);
				stateRecord.setFinalResult(finalResult);
			}else if(finalResult == bestResult) {
				stateRecord.getMoveToPlay().add(move);
			}
		}
        
		this.getRecords().put(boardStateStr, stateRecord);
		return stateRecord;
    }
}
