package bot;

import java.util.ArrayList;

import gui.GameMode;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import logic.Game;
import logic.State;

public class ParinyaBot extends Bot {
    public ParinyaBot() {
        super("ParinyaBot", Color.MOCCASIN, 13, true);
    }

    @Override
    public Record calculateRecord(State player, char[] boardState) {
        ArrayList<Pair<Integer, ParinyaData>> moves = new ArrayList<Pair<Integer, ParinyaData>>();
		ArrayList<Integer> validMoves = Game.getInstance().getValidMoves(boardState);

        for(int i=0; i<validMoves.size(); i++) {
            int move = validMoves.get(i);
            char[] newBoardState = boardState.clone();
            newBoardState[move] = (player == State.X) ? 'X' : 'O';
            ParinyaData result = this.calculateScore(player, newBoardState);
            moves.add(new Pair<Integer, ParinyaData>(move, result));
        }

        int maxScore = -1000;
        for(int i=0; i<moves.size(); i++) {
            if(moves.get(i).getValue().getStateScore() > maxScore) {
                maxScore = moves.get(i).getValue().getStateScore();
            }
        }
        
        ArrayList<Integer> moveToPlay = new ArrayList<Integer>();
		for(int i = 0; i < moves.size(); i++) {
			if(moves.get(i).getValue().getCriticalMove().size() > 0 && maxScore < 1000) {
				moveToPlay = moves.get(i).getValue().getCriticalMove();
				break;
			}else if (moves.get(i).getValue().getStateScore() == maxScore) {
				moveToPlay.add(moves.get(i).getKey());
			}
		}

		return new Record(String.valueOf(boardState), moveToPlay);
    }
	
	private ParinyaData calculateScore(State player, char[] boardState) {
		int playerScore = 0, opponentScore = 0;
		char playerChar = (player == State.X) ? 'X' : 'O',  opponentChar = (player == State.X) ? 'O' : 'X';
		ArrayList<Integer> criticalMove = new ArrayList<Integer>();
			
		int boardSize = (Game.getInstance().getGameMode() == GameMode.Speed) ? 5 : Game.getInstance().getBoardSize();
		
		for(ArrayList<Integer> win : Game.getInstance().getWinState()) {
			int playerCount = 0, opponentCount = 0;

			for(int i : win) {
				if(boardState[i] == playerChar) {
					playerCount++;
				}
				else if(boardState[i] == opponentChar) {
					opponentCount++;
				}
			}

			if(opponentCount == boardSize - 1 && playerCount == 0) {
				for(int i : win) {
					if(boardState[i] == '-' && !criticalMove.contains(i)) {
						criticalMove.add(i);
					}
				}
			}
			
			if(Game.getInstance().getGameMode() == GameMode.Speed && opponentCount == 3 && playerCount == 0) {
				if(boardState[win.get(0)] == '-' && boardState[win.get(4)] == '-') {
					if(!criticalMove.contains(win.get(0))) criticalMove.add(win.get(0));
					if(!criticalMove.contains(win.get(4))) criticalMove.add(win.get(4));
				}
			}
			
			if(playerCount == boardSize) {
				playerScore += 5000;
			}
			
			if(opponentCount == 0) {
				playerScore += playerCount;
			}else if(playerCount == 0) {
				opponentScore += opponentCount;
			}
		}

		return new ParinyaData(playerScore, opponentScore, criticalMove);
	}
}
