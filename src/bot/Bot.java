package bot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javafx.scene.paint.Color;
import logic.Game;
import logic.State;

public abstract class Bot {
    private HashMap<String, Record> records;
	private String name;
	private Color color;
	private int boardSizeLimit;
	private boolean isSpeedModeEnable;

	public Bot(String name, Color color, int boardSizeLimit, boolean isSpeedModeEnable) {
		this.setRecords(new HashMap<String, Record>());
		this.setName(name);
		this.setColor(color);
		this.setBoardSizeLimit(boardSizeLimit);
	}

    public Record getRecord(char[] boardState) {
		String boardStateStr = String.valueOf(boardState);

		if(!this.getRecords().containsKey(boardStateStr)) {
			this.getRecords().put(boardStateStr, this.calculateRecord(Game.getInstance().getCurrentTurn(), boardState));
		}
		
		return this.getRecords().get(boardStateStr);
	}
	
	public int getBestMove(State player, char[] boardState) {
        Record record = this.getRecord(boardState);
        ArrayList<Integer> moves = record.getMoveToPlay();
        Random random = new Random();
        return moves.get(random.nextInt(moves.size()));
    }

	public abstract Record calculateRecord(State player, char[] boardState);
	
	public HashMap<String, Record> getRecords() {
		return records;
	}

	public void setRecords(HashMap<String, Record> records) {
		this.records = records;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getBoardSizeLimit() {
		return boardSizeLimit;
	}

	public void setBoardSizeLimit(int boardSizeLimit) {
		this.boardSizeLimit = boardSizeLimit;
	}

	public boolean isSpeedModeEnable() {
		return isSpeedModeEnable;
	}

	public void setSpeedModeEnable(boolean isSpeedModeEnable) {
		this.isSpeedModeEnable = isSpeedModeEnable;
	}
	
}
