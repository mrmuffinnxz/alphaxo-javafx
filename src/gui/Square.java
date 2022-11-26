package gui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import logic.Game;
import logic.State;

public class Square extends Pane {
	private final String oURL = "o.png";
	private final String xURL = "x.png";
	private final Color baseColor = Color.WHITE;
	private int squareLenght;
	private int position;
	private State state;
	
	public Square(int position, int length) {
		this.setPosition(position);
		
		this.setSquareLenght(length);
		this.setMinHeight(length);
		this.setMinWidth(length);
		
		this.reset();
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> this.onClickHandler());
	}
	
	public void onClickHandler() {
		if(Game.getInstance().isGameEnd() || this.getState() != State.EMPTY) {
			return;
		}
		this.setState(Game.getInstance().getCurrentTurn());
		Game.getInstance().updateGameState(Game.getInstance().getCurrentTurn(), this.getPosition());
	}
	
	private void draw(Image image, Color backgroundColor) {
		BackgroundFill bgFill = new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY);
		BackgroundFill[] bgFillA = {bgFill};
		BackgroundSize bgSize = new BackgroundSize(this.getSquareLenght(),this.getSquareLenght(),false,false,false,false);
		BackgroundImage bgImg = new BackgroundImage(image, null, null, null, bgSize);
		BackgroundImage[] bgImgA = {bgImg};
		this.setBackground(new Background(bgFillA,bgImgA));
	}
	
	public void drawCurrentState(Color color) {
		if(this.getState() == State.O) {
			this.draw(new Image(this.getoURL(), true), color);
		}else if(this.getState() == State.X){
			this.draw(new Image(this.getxURL(), true), color);
		}else {
			this.reset();
		}
	}
	
	public void reset() {
		this.setState(State.EMPTY);
		this.fillBackground(this.getBaseColor());
	}
	
	public void fillBackground(Color color) {
		this.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	public int getSquareLenght() {
		return squareLenght;
	}

	public void setSquareLenght(int squareLenght) {
		this.squareLenght = squareLenght;
	}

	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public Color getBaseColor() {
		return baseColor;
	}
	
	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public String getoURL() {
		return oURL;
	}
	
	public String getxURL() {
		return xURL;
	}
}