package gui;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import logic.Game;
import main.Main;

public class Board extends GridPane {
	private ArrayList<Square> squares;

	public Board() {
		this.setHgap(8);
		this.setVgap(8);
		this.setPadding(new Insets(8));
		this.setPrefWidth(Main.getScreenHeight());
		this.setAlignment(Pos.CENTER);
		this.setBorder(new Border(
				new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		this.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));

		this.setSquares(new ArrayList<Square>());
		for (int i = 0; i < Game.getInstance().getBoardSize(); i++) {
			for (int j = 0; j < Game.getInstance().getBoardSize(); j++) {
				Square square = new Square(Game.getInstance().indexTo1D(i, j),
						Main.getScreenHeight() / Game.getInstance().getBoardSize());
				this.add(square, i, j);
				this.getSquares().add(square);
			}
		}
	}

	public void drawWinSquare(ArrayList<Integer> win) {
		for (Square s : Game.getInstance().getBoard().getSquares()) {
			if(win.contains(s.getPosition())) {
				s.drawCurrentState(Color.LIGHTGREEN);
			}else {
				s.drawCurrentState(Color.WHITE);
			}
			
		}
	}

	public ArrayList<Square> getSquares() {
		return squares;
	}

	public void setSquares(ArrayList<Square> squares) {
		this.squares = squares;
	}
}