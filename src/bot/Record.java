package bot;

import java.util.ArrayList;

public class Record {
	private String boardState;
	private ArrayList<Integer> moveToPlay;
	
	public Record(String boardState, ArrayList<Integer> moveToPlay) {
		this.setBoardState(boardState);
		this.setMoveToPlay(moveToPlay);
	}
	
	public String getBoardState() {
		return boardState;
	}
	public void setBoardState(String boardState) {
		this.boardState = boardState;
	}
	public ArrayList<Integer> getMoveToPlay() {
		return moveToPlay;
	}
	public void setMoveToPlay(ArrayList<Integer> moveToPlay) {
		this.moveToPlay = moveToPlay;
	}
	
}
