package bot;

import java.util.ArrayList;

public class ParinyaData {
    private int playerScore;
    private int opponentScore;
    private ArrayList<Integer> criticalMove;

    public ParinyaData(int playerScore, int opponentScore, ArrayList<Integer> criticalMove) {
        this.setPlayerScore(playerScore);
        this.setOpponentScore(opponentScore);
        this.setCriticalMove(criticalMove);
    }

    public int getStateScore() {
        return 1 + this.getPlayerScore() - this.getOpponentScore();
    }

	public int getPlayerScore() {
		return playerScore;
	}

	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}

	public int getOpponentScore() {
		return opponentScore;
	}

	public void setOpponentScore(int opponentScore) {
		this.opponentScore = opponentScore;
	}

	public ArrayList<Integer> getCriticalMove() {
		return criticalMove;
	}

	public void setCriticalMove(ArrayList<Integer> criticalMove) {
		this.criticalMove = criticalMove;
	}
    
    
}
