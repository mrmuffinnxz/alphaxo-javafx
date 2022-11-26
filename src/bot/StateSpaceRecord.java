package bot;

import java.util.ArrayList;

public class StateSpaceRecord extends Record {
    private float win;
    private float lose;
    private float draw;
    private int finalResult;

    public StateSpaceRecord(String boardState, ArrayList<Integer> moveToPlay, float win, float lose, float draw) {
        super(boardState, moveToPlay);
        this.setWin(win);
        this.setLose(lose);
        this.setDraw(draw);
    }
    
    public String getResultText() {
    	String txt = "";
    	txt += "Win : " + String.format("%.2f", this.getWinRate());
    	txt += "\nDraw : " + String.format("%.2f", this.getDrawRate());
    	txt += "\nLose : " + String.format("%.2f", this.getLoseRate());
    	return txt;
    }
    
    public float getTotal() {
		return this.getWin() + this.getLose() + this.getDraw();
	}

	public float getWinRate() {
		return this.getWin() / this.getTotal();
	}

	public float getLoseRate() {
		return this.getLose() / this.getTotal();
	}

	public float getDrawRate() {
		return this.getDraw() / this.getTotal();
	}

    public float getWin() {
		return this.win;
	}

	public void setWin(float win) {
		this.win = win;
	}

	public float getLose() {
		return lose;
	}

	public void setLose(float lose) {
		this.lose = lose;
	}

	public float getDraw() {
		return draw;
	}

	public void setDraw(float draw) {
		this.draw = draw;
	}

	public int getFinalResult() {
		return finalResult;
	}

	public void setFinalResult(int finalResult) {
		this.finalResult = finalResult;
	}
	
}
